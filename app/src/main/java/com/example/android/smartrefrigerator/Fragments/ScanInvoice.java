package com.example.android.smartrefrigerator.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.smartrefrigerator.ApiHandler.ApiHandler;
import com.example.android.smartrefrigerator.ApiHandler.ImageResponse;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ScanInvoice extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private static ScanInvoice scanInvoiceFragment;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 123;
    private Button buttonToChoose, buttonForUpload;
    private ImageView imageView;
    private Uri mCropImageUri;
    private Uri imageUri;
    private Uri filePath;
    private View relativeView;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    public ScanInvoice() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_invoice, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();
        //Initialize Views
        buttonToChoose = view.findViewById(R.id.buttonToChoose);
        buttonForUpload = view.findViewById(R.id.buttonForUpload);
        imageView = view.findViewById(R.id.imgViewForInvoice);
        relativeView = view.findViewById(R.id.upload_layout);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        buttonToChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick();
            }
        });

        buttonForUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    public void onSelectImageClick() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        scanInvoiceFragment.startActivityForResult(CropImage.getPickImageChooserIntent(getActivity()), CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Inside onActivity","Inside onActivity");
        // handle result of pick image chooser
        if ((requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE || requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK)
        {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE)
                imageUri = CropImage.getPickImageResultUri(getContext(), data);
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
                imageUri = data.getData();

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                Log.e("Inside External","Inside External");
            } else {
                // no permissions required or already grunted, can start crop image activity
                Log.e("startCropImageActivity","startCropImageActivity");
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.e("Image Result","Image Result");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                imageView.setImageURI(filePath);
                Toast.makeText(getContext(), "Cropping successful", Toast.LENGTH_SHORT).show();
                Log.e("Before Visibility","Before Visibility");
                buttonToChoose.setVisibility(View.GONE);
                relativeView.setVisibility(View.VISIBLE);
                Log.e("After Visibility","After Visibility");
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            Log.d("Got Permission","Got Permission");
            startCropImageActivity(mCropImageUri);
            Log.d("After Permission","After Permission");

        } else {
//            Toast.makeText(getContext(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        Log.d("Inside CropImage","Inside CropImage");
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(), this);
        Log.d("Crop Complete","Crop Complete");
    }

    public void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            currentDateTimeString = currentDateTimeString.replace(" ", "-");
            Log.e("Time", currentDateTimeString);

            StorageReference ref = storageReference.child("scannedInvoice/"+ currentDateTimeString + ".jpg");

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

            //To POST API to server
            ImageResponse imageName = new ImageResponse(currentDateTimeString);
            Call<List<ImageResponse>> callToSendImage = jsonPlaceHolderApi.postImage(imageName);
            callToSendImage.enqueue(new Callback<List<ImageResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ImageResponse>> call, @NonNull Response<List<ImageResponse>> response) {

                    if (!response.isSuccessful()) {
                        Log.d("Code: ", String.valueOf(response.code()));
                        return;
                    }

                    Log.e("BeforeGET4", response.body().toString());
                    List<ImageResponse> imageResponses = response.body();
                    Log.e("GETResponse4", response.body().toString());

                    for (ImageResponse imageResponse : imageResponses) {
                        String result = imageResponse.getResult();
                        Log.e("Result",result);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ImageResponse>> call, @NonNull Throwable t) {
                    Log.d("Code: ",t.getMessage());
                }
            });

        }

    }

    public static ScanInvoice getScanInvoiceFragment() {
        if (scanInvoiceFragment == null)
            scanInvoiceFragment = new ScanInvoice();
        return scanInvoiceFragment;
    }

}
