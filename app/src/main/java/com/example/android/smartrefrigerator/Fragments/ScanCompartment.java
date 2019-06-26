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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ScanCompartment extends Fragment {

    private static ScanCompartment scanCompartmentFragment;

    public String resultOfRadio = "";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 123;
    private Button buttonToChooseRack, buttonForUploadComp;
    private ImageView imageView;
    private Uri mCropImageUri;
    private Uri imageUri;
    private Uri filePath;
    private View relativeView;
    private RadioGroup radioGroup;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    public ScanCompartment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_compartment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize Views
        buttonToChooseRack = view.findViewById(R.id.buttonToChooseRack);
        buttonForUploadComp = view.findViewById(R.id.buttonForUploadComp);
        imageView = view.findViewById(R.id.imgViewForRack);
        relativeView = view.findViewById(R.id.upload_comp_layout);
        radioGroup = view.findViewById(R.id.radioGroup);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        buttonToChooseRack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick();
            }
        });

        buttonForUploadComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    // Get the selected Radio Button
                    RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                    Log.e("Radio1",radioButton.toString());
                    Log.e("Radio2",radioButton.getText().toString());
                    resultOfRadio = radioButton.getText().toString().replace("Rack ","r");
                    if (!resultOfRadio.equals("")){
                        buttonForUploadComp.setVisibility(View.VISIBLE);
                    }
                }
            });

    }

    public void onSelectImageClick() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        scanCompartmentFragment.startActivityForResult(CropImage.getPickImageChooserIntent(getActivity()), CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                buttonToChooseRack.setVisibility(View.GONE);
                relativeView.setVisibility(View.VISIBLE);
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

            StorageReference ref = storageReference.child("rackImage/"+ resultOfRadio + ".jpg");

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
        }
    }

    public static ScanCompartment getScanCompartmentFragment() {
        if (scanCompartmentFragment == null)
            scanCompartmentFragment = new ScanCompartment();
        return scanCompartmentFragment;
    }

}
