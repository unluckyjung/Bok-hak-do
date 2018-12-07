package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GangUiActivity extends AppCompatActivity {

    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gangui);
        //강의 셀렉트 박스 어레이 채워줌
        spinner1 = (Spinner)findViewById(R.id.spinner_lecture);
        ArrayAdapter lectureAdapter = ArrayAdapter.createFromResource(this, R.array.lecture, android.R.layout.simple_spinner_item);
        lectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(lectureAdapter);

    }

    @Override
    public void onBackPressed() { //브랜치 테스트좀해봄
        startActivity(new Intent(GangUiActivity.this, MainActivity.class));
        finish();
    }

}
