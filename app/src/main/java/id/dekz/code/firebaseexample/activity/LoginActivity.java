package id.dekz.code.firebaseexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dekz.code.firebaseexample.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etLoginEmail)EditText email;
    @BindView(R.id.etLoginPassword)EditText password;
    @BindView(R.id.btnLogin)Button login;
    @BindView(R.id.tvRegister)TextView register;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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
    }
}
