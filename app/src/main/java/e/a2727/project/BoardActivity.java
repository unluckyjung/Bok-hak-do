package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class BoardActivity extends AppCompatActivity {

    private LinearLayout linearlayout1, linearlayout2, linearlayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        linearlayout1 = findViewById(R.id.linearLayout1);
        linearlayout2 = findViewById(R.id.linearLayout2);
        linearlayout3 = findViewById(R.id.linearLayout3);

        linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calender class 구현 시 풀어주세요
                startActivity(new Intent(BoardActivity.this, CalenderActivity.class));
                finish();
            }
        });

        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //자유 게시판 class 구현 시 풀어주세요
//                startActivity(new Intent(BoardActivity.this, FreeActivity.class));
//                finish();
            }
        });

        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TIPS 구현 시 풀어주세요
//                startActivity(new Intent(BoardActivity.this, TipsActivity.class));
//                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BoardActivity.this, MainActivity.class));
        finish();
    }

}
