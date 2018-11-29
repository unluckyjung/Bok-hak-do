package e.a2727.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private TextView Register;
    private Button Login, back;
    private int counter = 3;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean saveLogin;
    CheckBox savelogincheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        back = (Button)findViewById(R.id.btnBackToLogin);
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        savelogincheckbox = (CheckBox)findViewById(R.id.isRemember);

        //체크박스 초기 설정
        savelogincheckbox.setEnabled(true);

        //로그인 상태 remember 적용시키기 위해 사용
        editor = sharedPreferences.edit();

        //firebase 인증 받아오기
        firebaseAuth = FirebaseAuth.getInstance();

        //유저 상태 확인하기 위해 사용
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //유저 로그인 상태면? 메인으로 넘어감
        if(user != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        //로그인 버튼 누를 시 아이디와 패스워드 전송
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        //back 버튼 누르면 다시 뒤로 갈 수 있게 적용함
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, StartActivity.class));
                finish();
            }
        });

        //로그인 상태 저장
        saveLogin=sharedPreferences.getBoolean("savelogin",false);
        if(saveLogin==true){
            Name.setText(sharedPreferences.getString("username",null));
            Password.setText(sharedPreferences.getString("password",null));
            savelogincheckbox.setChecked(true);
        }
        else if(saveLogin==false){
            savelogincheckbox.setChecked(false);
        }
    }

    //인증하는 함수이다. 유저의 아이디와 패스워드를 받아 인증 확인
    private void validate(String userName, String userPassword) {
        //firebase에서 제공하는 이메일과 패스워드를 사용한 인증방식 적용

        if (userName.isEmpty() || userPassword.isEmpty()) {
            Toast toast = Toast.makeText(LoginActivity.this, "Please enter all the details", LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) { //함수가 저절로 작동하므로 task가 성공했는지의 여부만 따짐
                    if (task.isSuccessful()) {
                        if (savelogincheckbox.isChecked()) { //만약 로그인 기억 방식 적용되었다면 저장함
                            editor.putBoolean("savelogin", true);
                            editor.putString("username", Name.getText().toString());
                            editor.putString("password", Password.getText().toString());
                            editor.commit();
                        } else {
                            editor.putBoolean("savelogin", false);
                            editor.clear();
                            editor.commit();
                        }
                        checkEmailVerification(); //이메일 인증하는지 확인한다
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show(); //로그인 실패 시 적용
                        counter--; //3번의 기회 적용. 3번 다 되면 팅김
                        if (counter == 0) {
                            Login.setEnabled(false);
                        }
                    }
                }
            });
        }
    }

    private void checkEmailVerification(){ //이메일 인증 방식이다
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser(); //현재 유저를 받아옴
        Boolean emailflag = firebaseUser.isEmailVerified(); //email verification이 끝나면 bool이 1인가로 바뀌는데 그거 체크함

        if(emailflag){ //만약 있으면 로그인 창 끝내고 Main으로 넘어감
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{ //그게 아니면 Toast 띄워서 Verify 해달라고 띄움. 원래는 아이디가 존재하면 로그인이 바로 되니, Verify 안되면 그냥 로그아웃 적용해야함
            Toast toast = Toast.makeText(this, "Please Verify your email", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            firebaseAuth.signOut(); //로그아웃 시켜버림
        }
    }

    @Override
    public void onBackPressed() {
                startActivity(new Intent(LoginActivity.this, StartActivity.class));
                finish();
    }

}
