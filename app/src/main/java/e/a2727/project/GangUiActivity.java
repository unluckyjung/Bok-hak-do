package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class GangUiActivity extends AppCompatActivity {

    private Spinner spinner1;
    private Button post, search2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gangui);
        spinner1 = (Spinner)findViewById(R.id.spinner_lecture);
        post = findViewById(R.id.post);
        search2 = findViewById(R.id.search2);
        ArrayAdapter lectureAdapter = ArrayAdapter.createFromResource(this, R.array.lecture, android.R.layout.simple_spinner_item);
        lectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(lectureAdapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GangUiActivity.this, PostActivity.class));
                finish();
            }
        });

        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GangUiActivity.this, PostActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() { //브랜치 테스트좀해봄
        startActivity(new Intent(GangUiActivity.this, MainActivity.class));
        finish();
    }

}
