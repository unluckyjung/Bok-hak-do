//맨 처음 Welcome 끝나고 넘어오는 Acivity. 딱히 하는건 없고 회원가입과 Login 창만 담당함
package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private Button btnToLogin, btnToSignUp;
    private FirebaseAuth firebaseAuth;
    private long pressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnToLogin = (Button)findViewById(R.id.btnToLogin);
        btnToSignUp = (Button)findViewById(R.id.btnToSignUp);

        //여기서는 Login 체크만 하려고 firebase 내 현재 유저 받아와서 로그인만 체크함
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //로그인 되어있는지 체크
        if(user != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }

        //로그인 창으로 넘겨줌
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });

        //회원가입 창으로 넘겨줌
        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegistrationActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(StartActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(StartActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }

}
