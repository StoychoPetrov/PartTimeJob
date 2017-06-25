package eu.mobile.parttimejob.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.mobile.parttimejob.R;
import eu.mobile.parttimejob.httpConnections.HttpConnection;
import eu.mobile.parttimejob.httpConnections.HttpRequests;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button      mLogInButton;
    private Button      mForgotPasswordBtn;
    private Button      mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        setListeners();
    }

    private void initUI(){
        mLogInButton            = (Button)  findViewById(R.id.log_in_button);
        mForgotPasswordBtn      = (Button)  findViewById(R.id.forgot_password_btn);
        mSignUpButton           = (Button)  findViewById(R.id.sign_up_btn);
    }

    private void setListeners(){
        mLogInButton.setOnClickListener(this);
        mForgotPasswordBtn.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_btn:
                startSignUpActivity();
                break;
            case R.id.log_in_button:
                sendLogin();
//                startMainActivity();
                break;
        }
    }

    private void sendLogin(){
        try {
            URL loginUrl = HttpRequests.getLoginUrl("test","0000");
            HttpConnection httpConnection = new HttpConnection(this);
            httpConnection.execute(loginUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void startSignUpActivity(){
        Intent signUpIntent = new Intent(this,RegisterActivity.class);
        startActivity(signUpIntent);
    }

    private void startMainActivity(){
        Intent startMainActivityIntent  = new Intent(this,MainActivity.class);
        startActivity(startMainActivityIntent);
    }
}
