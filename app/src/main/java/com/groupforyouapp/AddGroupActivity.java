package com.groupforyouapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.groupforyouapp.Models.Groups;
import com.groupforyouapp.Models.Users;
import com.groupforyouapp.db.FirebasedbHelper;
import com.groupforyouapp.db.MyCallback;

import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    private EditText edtNameGr, edtDesc, edtNumber;
    private Button btnDone;
    private TextView mName;
    private ImageButton imageGroup;
    private Spinner spinner;
    private ProgressBar progressBar;
    private final int REQUEST_CODE_GALLERY = 999;
    private Uri mImageUri;
    private StorageReference mSr;
    private StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        init();
        final Groups group = new Groups();
        imageGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddGroupActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddGroupActivity.this, "створення гуртка", Toast.LENGTH_SHORT).show();
                   }  uploadFile();

                        Toast.makeText(getApplicationContext(), "Гурток доданий успішно!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
                        startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something is wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            imageGroup.setBackground(null);
            Picasso.with(this).load(mImageUri).fit().centerInside().into(imageGroup);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile() {
        if (mImageUri!=null){
            final StorageReference fileRef = mSr.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            mUploadTask = fileRef.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Groups group = new Groups();
                                    String url = uri.toString();
                                    group.setImage(url);
                                    new FirebasedbHelper().getCurUser(new MyCallback() {
                                        @Override
                                        public void onSuccess(List<?> list) {
                                            Users curUser = (Users) list.get(0);
                                            group.setNameGroup(edtNameGr.getText().toString().trim());
                                            group.setAuthor(curUser.getName()+curUser.getS_name());
                                            group.setNumber(edtNumber.getText().toString().trim());
                                            group.setDescription(edtDesc.getText().toString().trim());
                                            group.setCategory(spinner.getSelectedItem().toString());
                                            group.setUId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            group.setUserEXP(curUser.getExp());
                                            new FirebasedbHelper().addGroup(group);
                                        }
                                    });
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddGroupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
        Toast.makeText(this, "Фото не було вибрано", Toast.LENGTH_SHORT).show();
            new FirebasedbHelper().getCurUser(new MyCallback() {
                @Override
                public void onSuccess(List<?> list) {
                    Groups group = new Groups();
                    Users curUser = (Users) list.get(0);
                    group.setNameGroup(edtNameGr.getText().toString().trim());
                    group.setAuthor(curUser.getName()+curUser.getS_name());
                    group.setNumber(edtNumber.getText().toString().trim());
                    group.setDescription(edtDesc.getText().toString().trim());
                    group.setCategory(spinner.getSelectedItem().toString());
                    group.setUId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    group.setUserEXP(curUser.getExp());
                    new FirebasedbHelper().addGroup(group);
                }
            });
    }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        edtNameGr = findViewById(R.id.edtNameGr);
        edtDesc = findViewById(R.id.edtDesc);
        edtNumber = findViewById(R.id.edtNumber);
        imageGroup = findViewById(R.id.imageGroup);
        btnDone = findViewById(R.id.btnDone);
        mName = findViewById(R.id.mName);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar);

        mSr = FirebaseStorage.getInstance().getReference("Groups");

        new FirebasedbHelper().getCurUser(new MyCallback() {
            @Override
            public void onSuccess(List<?> list) {
                Users curUser = (Users) list.get(0);
                mName.setText("Керівник: " + curUser.getName()+curUser.getS_name());
                edtNumber.setHint(curUser.getPhone());
            }
        });

    }


}