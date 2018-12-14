package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PostActivity extends AppCompatActivity {

    ImageView ExamImage;
    String url = "https://firebasestorage.googleapis.com/v0/b/project-614af.appspot.com/o/image%2Fdma.PNG?alt=media&token=b5bf9a56-dc74-492b-89e2-da9e5695162d";
    //파이어베이스 파일주소 Space ref이다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ExamImage = (ImageView)findViewById(R.id.ExamImage);
        Glide.with(this).load(url).into(ExamImage); //이거 존나사기임 GIF도 가능함 코드쓰면 50줄넘는데 라이브러리 쓰니까 한줄로 끝냄
        }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(PostActivity.this, GangUiActivity.class));
        finish();
    }

}
