package e.a2727.project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class BoardActivity extends AppCompatActivity {

    private LinearLayout linearlayout1, linearlayout2, linearlayout3, linearlayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        linearlayout1 = findViewById(R.id.linearLayout1);
        linearlayout2 = findViewById(R.id.linearLayout2);
        linearlayout3 = findViewById(R.id.linearLayout3);
        linearlayout4 = findViewById(R.id.linearLayout4);

        linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(BoardActivity.this, CalenderActivity.class));
                //일단 냄겨는둠 아무기능도 없는 캘린더뷰 띄우는거, 일단 레이아웃도 냄겨놧음
                //finish();
                Uri calendarUri = CalendarContract.CONTENT_URI //구글 캘린더 띄우기
                        .buildUpon()
                        .appendPath("time") //특정시간 맞춰서 띄우기도 가능 NUll값으로 넣으면 오늘날짜.
                        .build();
                startActivity(new Intent(Intent.ACTION_VIEW, calendarUri)); //구글 캘린더 띄우기
            }
        });

        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoardActivity.this, FreeActivity.class));
                finish();
            }
        });

        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoardActivity.this, ChatActivity.class));
                finish();
            }
        });

        linearlayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoardActivity.this, ProfessorActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BoardActivity.this, MainActivity.class));
        finish();
    }

}
