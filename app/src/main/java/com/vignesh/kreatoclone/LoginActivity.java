package com.vignesh.kreatoclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtDomain, edtUserName, edtPassword;
    private Button btnLogIn, btnSignUp;

    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtDomain = findViewById(R.id.edtDomain_logIn);
        edtUserName = findViewById(R.id.edtUserName_logIn);
        edtPassword = findViewById(R.id.edtPassword_logIn);
        btnLogIn = findViewById(R.id.btnLogin_logIn);
        btnSignUp = findViewById(R.id.btnSignUp_logIn);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            if (ParseUser.getCurrentUser().getBoolean("emailVerified")) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                ParseUser.logOut();
            }

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLogin_logIn:

                if (!edtDomain.getEditText().getText().toString().isEmpty()) {
                    edtDomain.setError(null);
                    if (!edtUserName.getEditText().getText().toString().isEmpty()) {
                        edtUserName.setError(null);
                        if (!edtPassword.getEditText().getText().toString().isEmpty()) {
                            edtPassword.setError(null);
                            login();
                        } else {
                            edtPassword.setError("Please Enter Your Password");
                        }
                    } else {
                        edtUserName.setError("Please Enter the User Name / Email");
                    }
                } else {
                    edtDomain.setError("Please Enter Your Domain");
                }

                break;

            case R.id.btnSignUp_logIn:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    private void login() {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setContentText("Please Wait...");
        alertDialog.show();

        ParseUser.logInInBackground(edtUserName.getEditText().getText().toString(), edtPassword.getEditText().getText().toString(), new LogInCallback() {
            @Override
            public void done(final ParseUser user, ParseException e) {

                if (e == null) {

                    if (user.getBoolean("emailVerified")) {

                        if (user != null) {


                            if ((user.get("domain") + "").equals(edtDomain.getEditText().getText().toString())) {

                                alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Login Successful!")
                                        .setContentText("Welcome Home, " + user.getUsername())
                                        .setConfirmButton("Home", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                alertDialog.dismissWithAnimation();
                                                finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog.dismissWithAnimation();

                                alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Invalid Domain!")
                                        .setContentText("Domain does not exists! ")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                user.logOut();
                                                alertDialog.dismissWithAnimation();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            }

                        } else {

                            alertDialog.dismissWithAnimation();

                            alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Login Failed!")
                                    .setContentText("Error: " + e);
                            alertDialog.show();

                        }

                    } else {

                        ParseUser.logOut();

                        alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Login Fail!")
                                .setContentText("Please Verify Your Email first! ")
                                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                    }
                                });
                        alertDialog.setCancelable(false);
                        alertDialog.show();

                    }

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login Failed!")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are You Sure?")
                .setContentText("Ok...Bye-bye then")
                .setConfirmButton("Bye", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        alertDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();

    }
}
