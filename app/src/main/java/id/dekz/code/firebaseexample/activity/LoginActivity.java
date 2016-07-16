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
import id.dekz.code.firebaseexample.MainActivity;
import id.dekz.code.firebaseexample.R;
import id.dekz.code.firebaseexample.util.Session;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etLoginEmail)EditText email;
    @BindView(R.id.etLoginPassword)EditText password;
    @BindView(R.id.btnLogin)Button login;
    @BindView(R.id.tvRegister)TextView register;

    private FirebaseAuth auth;
    private ProgressDialog progressLogin;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                reg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(reg);
                LoginActivity.this.finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0 ||
                        password.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                }else{
                    String strEmail = email.getText().toString();
                    Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
                    Matcher emailMatcher = emailPattern.matcher(strEmail);

                    if(emailMatcher.matches()){
                        //email valid
                        String pass = password.getText().toString();
                        if(pass.length()<6){
                            Toast.makeText(LoginActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                        }else{
                            loginProccess(strEmail,pass);
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Email Format Not Valid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void loginProccess(final String email, String pass){
        progressLogin = ProgressDialog.show(
                this,
                "Login","Login in progress, please wait..",
                true,
                false);

        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressLogin.dismiss();
                        if(task.isSuccessful()){
                            /*Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();*/
                            Session session = new Session(LoginActivity.this);
                            session.saveLoginSession(email);
                            Intent main = new Intent(LoginActivity.this, MainActivity.class);
                            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(main);
                            LoginActivity.this.finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressLogin.dismiss();
                        Log.d(TAG, e.getMessage());
                    }
                });
    }
}
