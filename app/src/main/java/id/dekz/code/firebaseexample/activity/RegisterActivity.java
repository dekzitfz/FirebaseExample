package id.dekz.code.firebaseexample.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dekz.code.firebaseexample.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etRegisterEmail)EditText email;
    @BindView(R.id.etRegisterPassword)EditText password;
    @BindView(R.id.etRegisterPasswordConfirm)EditText passwordConfirm;
    @BindView(R.id.btnRegister)Button register;
    @BindView(R.id.tvLogin)TextView login;

    private FirebaseAuth auth;
    private ProgressDialog progressRegister;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                RegisterActivity.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0 ||
                        password.getText().length()==0 ||
                        passwordConfirm.getText().length()==0){
                    Toast.makeText(RegisterActivity.this, "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                }else{
                    String strEmail = email.getText().toString();
                    Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
                    Matcher emailMatcher = emailPattern.matcher(strEmail);

                    if(emailMatcher.matches()){
                        //email valid
                        String pass = password.getText().toString();
                        String passCOnfirm = passwordConfirm.getText().toString();
                        if(pass.length()<6){
                            Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                        }else{
                            if(pass.equals(passCOnfirm)){
                                registerProccess(strEmail,pass);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Passwords Doesn't Match!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Email Format Not Valid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registerProccess(String email, String pass){
        progressRegister = ProgressDialog.show(
                this,
                "Registering","register in progress, please wait..",
                true,
                false);

        auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressRegister.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Register Success! Please Login", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(login);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Register Failed! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressRegister.dismiss();
                        Log.d("authFailure",e.getMessage());
                    }
                });
    }
}
