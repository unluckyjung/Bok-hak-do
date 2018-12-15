package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Jang extends AppCompatActivity {

    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jang);
        button3 = findViewById(R.id.Backed);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Jang.this, BoardActivity.class));
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
