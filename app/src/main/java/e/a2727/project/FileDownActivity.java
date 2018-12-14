package e.a2727.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FileDownActivity extends AppCompatActivity {


    private long pressedTime = 0;
    int point = 10; //포인트 일단 임시적으로 10넣음.

    Button DownButton;
    ImageView ExamImage;
    String url1 = "https://firebasestorage.googleapis.com/v0/b/project-614af.appspot.com/o/image%2FAlgoritm_.Modifiedjpg.jpg?alt=media&token=1756b85a-1045-4244-8ace-093c1f3e7159"; //모자이크된 파일 주소
    String url2 = "https://firebasestorage.googleapis.com/v0/b/project-614af.appspot.com/o/image%2FAlgoritm.jpg?alt=media&token=3827c794-8b47-4fd3-bb93-c569a01ff020"; //모자이크 되지 않은 파일 주소


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_down);

        DownButton = findViewById(R.id.DownButton);

        ExamImage = (ImageView)findViewById(R.id.ExamImage);
        Glide.with(this).load(url1).into(ExamImage); //이거 존나사기임 GIF도 가능함 코드쓰면 50줄넘는데 라이브러리 쓰니까 한줄로 끝냄
        //Firebase내 모자이크된 이미지 출력


        DownButton.setClickable(true);
        DownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( pressedTime == 0 ) {
                    Toast.makeText(FileDownActivity.this, " 한번 더 누르면 포인트가 차감됩니다." , Toast.LENGTH_LONG).show();
                    pressedTime = System.currentTimeMillis();
                }
                else {
                    int seconds = (int) (System.currentTimeMillis() - pressedTime);

                    if ( seconds > 2000 ) {
                        Toast.makeText(FileDownActivity.this, " 한번 더 누르면 포인트가 차감됩니다." , Toast.LENGTH_LONG).show();
                        pressedTime = 0 ;
                    }
                    else {
                        if(point >= 2) { //포인트가 2점넘게 있다면 출력해줌
                            Glide.with(FileDownActivity.this).load(url2).into(ExamImage); // 원본 이미지 출력.
                            point = point-2;
                            Toast.makeText(FileDownActivity.this, " 남은포인트는 " + point + " point 입니다." , Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(FileDownActivity.this, " 포인트가 부족 합니다." , Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(FileDownActivity.this, BoardActivity.class)); //아까 게시판에서 부른거라서 BoardActivity로 돌아가게 해둔거임
        finish();
    }
}
