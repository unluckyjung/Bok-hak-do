//로그인 성공 시 넘어오는 MainActivity. 별건 없고 로그인 성공 시 다시 뒤로 가려고 Logout 버튼만 구현해놓았다.
package e.a2727.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnLogout;
    private LinearLayout linearlayout1, linearlayout2, linearlayout3;
    private long pressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        linearlayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        linearlayout3 = (LinearLayout)findViewById(R.id.linearLayout3);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //강의 class 구현 시 풀어주세요
                startActivity(new Intent(MainActivity.this, GangUiActivity.class));
                finish();
            }
        });

        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "게시판 클릭됨", LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                //게시판 class 구현 시 풀어주세요
//                startActivity(new Intent(MainActivity.this, board.class));
//                finish();
            }
        });

        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //지도 class 구현 시 풀어주세요
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                finish();
            }
        });

    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }

    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 로그아웃 됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 로그아웃 됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                Logout();
            }
        }
    }

}

