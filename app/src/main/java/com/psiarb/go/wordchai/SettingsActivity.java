package com.psiarb.go.wordchai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;


public class SettingsActivity extends AppCompatActivity {

    private ProgressDialog mProgress;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private Button mStatusUpdateBtn;
    private Button ViewProfile;


    private static final int GALLERY_PIC = 1;

    //storage ref
    private StorageReference mProfileImageStorage;
    private String mLocation;
    private String mAge;
    private String mGender;
    private String mLang;
    private String mInterests;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String status_value = getIntent().getStringExtra("status_value");
        final String location_value = getIntent().getStringExtra("location_vaue");
        final String age_value = getIntent().getStringExtra("age_value");
        final String gender_value = getIntent().getStringExtra("gender_value");
        final String lang_value = getIntent().getStringExtra("lang_value");
        final String interests_value = getIntent().getStringExtra("interests_value");




        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);
        mName = (TextView) findViewById(R.id.profile_name);
        mStatus = (TextView) findViewById(R.id.profile_status);
        mStatusUpdateBtn = (Button) findViewById(R.id.settings_status_button);
        ViewProfile = (Button) findViewById(R.id.viewProfile_btn);


        mProfileImageStorage = FirebaseStorage.getInstance().getReference();


        //==============GO TO CHANGE PROFILE PICTURE ===============
        mDisplayImage.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PIC);

                /* start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);*/

//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(SettingsActivity.this);
            }



        });



        //==================GO TO PROFILE BUTTON=====================

        ViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = mCurrentUser.getUid();

                Intent profileIntent = new Intent(SettingsActivity.this, ProfileActivity.class);
                profileIntent.putExtra("user_id",user_id);
                startActivity(profileIntent);

            }
        });

        //================= GO TO STATUS BUTTON ====================
        mStatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status_value = mStatus.getText().toString();
                String location_value = mLocation;
                String age_value = mAge;
                String gender_value = mGender;
                String lang_value = mLang;
                String interests_value = mInterests;



                Intent statusIntent = new Intent(SettingsActivity.this, StatusActivity.class);
                statusIntent.putExtra("status_value", status_value);
                statusIntent.putExtra("location_value", location_value);
                statusIntent.putExtra("age_value", age_value);
                statusIntent.putExtra("gender_value", gender_value);
                statusIntent.putExtra("lang_value", lang_value);
                statusIntent.putExtra("interests_value", interests_value);


                startActivity(statusIntent);


            }
        });

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String location = dataSnapshot.child("location").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String lang = dataSnapshot.child("lang").getValue().toString();
                String interests = dataSnapshot.child("interests").getValue().toString();


                mName.setText(name);
                mStatus.setText(status);
                mLocation = location;
                mAge = age;
                mGender = gender;
                mInterests = interests;
                mLang = lang;


                if(!image.equals("default")){
                    Picasso.get().load(image).placeholder(R.drawable.defaultprofileuser).into(mDisplayImage);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {



            Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);


        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PIC && resultCode == RESULT_OK){




            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setAspectRatio(1,1).start(this);



        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgress = new ProgressDialog(SettingsActivity.this);
                mProgress.setTitle("Image");
                mProgress.setMessage("Please wait while we upload and process your image.");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();


                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();



                StorageReference filepath = mProfileImageStorage.child("profile_images").child(current_user_id + ".jpg");
                final StorageReference thumb_filepath =  mProfileImageStorage.child("profile_images").child("thumbs")
                       .child(current_user_id + ".jpg");

                Bitmap thumb_file = null;
                Bitmap thumb_bitmap = null;


                try {
                 thumb_bitmap = new Compressor(this)
                           .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                final byte[] thumb_byte = baos.toByteArray();


               filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            mProgress.dismiss();


                            @SuppressWarnings("VisibleForTests") final String download_url = task.getResult().getDownloadUrl().toString();


                            // ======================================================================================
                            UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();

                                    if(thumb_task.isSuccessful()){

                                        Map update_hashMap = new HashMap<>();
                                        update_hashMap.put("image",download_url);
                                        update_hashMap.put("thumb_image", thumb_downloadUrl);


                                        mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });


                                    } else { Toast.makeText(SettingsActivity.this, "Error in uploading thumbnail",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });


                        } else {

                            mProgress.dismiss();
                            Toast.makeText(SettingsActivity.this, "ERROR", Toast.LENGTH_LONG).show();}

                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }



    }
}
