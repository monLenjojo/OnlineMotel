package com.example.user1801.onlinemotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private EditText myPassword, myEmail;
    private FirebaseAuth auth;
    boolean tryLogInState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        tryLogInState = false;
        myEmail = findViewById(R.id.editEmail);
        myPassword = findViewById(R.id.editPassword);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null) {
                    Intent page = new Intent(LogInActivity.this,MainActivity.class);
                    startActivity(page);
                }
                if (tryLogInState) {
                    changeView(false);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void clickLogin(final View view){
        netWorkCheck();
        view.setEnabled(false);
        boolean checkState = true;
        myEmail.setError(null);
        myPassword.setError(null);
        final String email = myEmail.getText().toString();
        final String password = myPassword.getText().toString();
        if (!Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(email).matches()) {
            myEmail.setError("請輸入正確格式的帳號");
            checkState = false;
        }
        if(!Pattern.compile("[[a-zA-Z-]+[0-9-]]{6,12}$").matcher(password).matches()){
            myPassword.setError("請輸入6~12位中英文混合密碼");
            checkState = false;
        }
        if (checkState) {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {
                        if (!task.isSuccessful()) {
                            new AlertDialog.Builder(LogInActivity.this).setMessage("查無此帳戶，是否進行新增")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isComplete()) {
                                                        if (!task.isSuccessful()) {
                                                            new AlertDialog.Builder(LogInActivity.this).setMessage("帳號建立失敗").show();
                                                            changeView(false);
                                                        }else {
                                                            Toast.makeText(LogInActivity.this, "自動登入中，請稍後", Toast.LENGTH_SHORT).show();
                                                            changeView(true);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }).show();
                        }
                    }
                }
            });
        }
        view.setEnabled(true);
    }

    private void  changeView(boolean change) {
        ConstraintLayout logInView = findViewById(R.id.loginView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        if (change) {
            tryLogInState = true;
            logInView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            tryLogInState = false;
            logInView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void netWorkCheck() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Log.d("LogInActivity", "網路連線狀態： "+"錯誤");
            new AlertDialog.Builder(this)
                    .setTitle("無法連線")
                    .setMessage("請確認裝置是否連上網路並可正常使用")
                    .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            netWorkCheck();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(LogInActivity.this)
                                    .setMessage("裝置無法連線將無法完成功能\n")
                                    .setPositiveButton("沒關係", null)
                                    .show();
                        }
                    })
                    .show();
        }else {
            Log.d("LogInActivity", "網路連線狀態： "+"正常");
        }
    }
}
