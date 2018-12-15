package e.a2727.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Jang extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jang);
        button1 = findViewById(R.id.JangHome);
        button2 = findViewById(R.id.ITHome);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://dblab.jbnu.ac.kr/new")));
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://it.jbnu.ac.kr/itjbnu/2016/inner.php?sMenu=main")));
                finish();
            }
        });
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(Jang.this, ProfessorActivity.class));
        finish();
    }
}
