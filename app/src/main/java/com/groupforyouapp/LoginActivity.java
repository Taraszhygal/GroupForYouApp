package com.groupforyouapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shawnlin.numberpicker.NumberPicker;
import com.groupforyouapp.Models.Users;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    Button regU, regM, signIn;
   // List<Groups> allGroups = new ArrayList<>();
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        //check for sign in
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        regM = findViewById(R.id.btnRegistrationMentor);
        regU = findViewById(R.id.btnRegisrtrationUser);
        signIn = findViewById(R.id.btnSignIn);
        root = findViewById(R.id.root_element);
        users = db.getReference("Users");


        regU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRegistrationWindowForUser();
            }
        });
        regM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRegistrationWindowForMentor();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSignInWindow();
            }
        });

//        FirebasedbHelper obj = new FirebasedbHelper();
//        obj.getGroups(new MyCallback() {
//            @Override
//            public void onSuccess(List<?> list) {
//               allGroups = (List<Groups>) list;
//               Log.d("Tag", allGroups.toString());
//            }
//
//        });
    }

    private void ShowRegistrationWindowForUser() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зареєструватись");
        dialog.setMessage("Просимо заповнити всі поля для реєстрації аккаунта");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View reg_window = inflater.inflate(R.layout.registration_window, null);
        dialog.setView(reg_window);
        dialog.setNegativeButton("Відмінити", null);
        dialog.setPositiveButton("Добавити", null);

        final MaterialEditText email = reg_window.findViewById(R.id.mailField);
        final MaterialEditText pass = reg_window.findViewById(R.id.PassField);
        final MaterialEditText name = reg_window.findViewById(R.id.nameField);
        final MaterialEditText s_name = reg_window.findViewById(R.id.second_nameField);
        final MaterialEditText phone = reg_window.findViewById(R.id.numberField);

        final AlertDialog mdialog = dialog.create();
        mdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mdialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean check = true;
                        if (TextUtils.isEmpty(email.getText().toString().trim())) {
                            email.setError("Введіть вашу пошту ");
                            email.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(pass.getText().toString())) {
                            pass.setError("Введіть ваш пароль ");
                            pass.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            name.setError("Введіть ваше ім'я ");
                            name.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(s_name.getText().toString())) {
                            s_name.setError("Введіть ваше прізвище ");
                            s_name.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(phone.getText().toString())) {
                            phone.setError("Введыть номер телефону ");
                            phone.requestFocus();
                            check = false;
                        }
                        if (check) {
                            auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Users user = new Users();
                                            user.setEmail(email.getText().toString().trim());
                                            user.setPass(pass.getText().toString());
                                            user.setName(name.getText().toString());
                                            user.setS_name(s_name.getText().toString());
                                            user.setPhone(phone.getText().toString());
                                            user.setUserType(false);
                                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            mdialog.cancel();
                                                            Snackbar.make(root, "Користувач доданий ", Snackbar.LENGTH_LONG).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });
            }
        });
        mdialog.show();
    }

    private void ShowRegistrationWindowForMentor() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Для реєстрації");
        dialog.setMessage("Просимо заповнити всі поля для реєстрації аккаунта");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View reg_window = inflater.inflate(R.layout.register_window_for_mentor, null);
        dialog.setView(reg_window);
        dialog.setNegativeButton("Відмінити", null);
        dialog.setPositiveButton("Добавити", null);

        final MaterialEditText email = reg_window.findViewById(R.id.mailField);
        final MaterialEditText pass = reg_window.findViewById(R.id.PassField);
        final MaterialEditText name = reg_window.findViewById(R.id.nameField);
        final MaterialEditText s_name = reg_window.findViewById(R.id.second_nameField);
        final MaterialEditText phone = reg_window.findViewById(R.id.numberField);
        final NumberPicker numberPicker = reg_window.findViewById(R.id.number_picker);

        final AlertDialog mdialog = dialog.create();
        mdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mdialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean check = true;
                        if (TextUtils.isEmpty(email.getText().toString().trim())) {
                            email.setError("Введіть вашу пошту ");
                            email.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(pass.getText().toString())) {
                            pass.setError("Введіть ваш пароль ");
                            pass.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            name.setError("Введіть ваше ім'я ");
                            name.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(s_name.getText().toString())) {
                            s_name.setError("Введіть ваше прізвище ");
                            s_name.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(phone.getText().toString())) {
                            phone.setError("Введыть номер телефону ");
                            phone.requestFocus();
                            check = false;
                        }
                        if (check) {
                            auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Users user = new Users();
                                            user.setEmail(email.getText().toString().trim());
                                            user.setPass(pass.getText().toString());
                                            user.setName(name.getText().toString());
                                            user.setS_name(s_name.getText().toString());
                                            user.setPhone(phone.getText().toString());
                                            user.setUserType(true);
                                            user.setExp(numberPicker.getValue());
                                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            mdialog.cancel();
                                                            Snackbar.make(root, "Користувач доданий ", Snackbar.LENGTH_LONG).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });
            }
        });
        mdialog.show();
    }

    private void ShowSignInWindow() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        final View signIn = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signIn);
        dialog.setNegativeButton("Відмінити", null);
        dialog.setPositiveButton("Увійти", null);

        final MaterialEditText email = signIn.findViewById(R.id.mailField);
        final MaterialEditText pass = signIn.findViewById(R.id.PassField);
        final TextView err = signIn.findViewById(R.id.errorField);

        final AlertDialog mdialog = dialog.create();

        mdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mdialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean check = true;
                        if (TextUtils.isEmpty(email.getText().toString().trim())) {
                            email.setError("Введіть вашу пошту ");
                            email.requestFocus();
                            check = false;
                        }
                        if (TextUtils.isEmpty(pass.getText().toString())) {
                            pass.setError("Введіть ваш пароль ");
                            pass.requestFocus();
                            check = false;
                        }
                        if (check) {
                            auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    err.setText("Помилка входу: " + e.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
        mdialog.show();
    }

}