package e.a2727.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FileDownActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private DatabaseReference userpoints;
    private long pressedTime = 0;
    //int point = 10; //포인트 일단 임시적으로 10넣음.
    int point = 10;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Userpoint만 사용하므로 위치 설정
        userpoints = mDatabase.child("users").child(userId).child("userPoint");

        userpoints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Integer 값으로 받아옴
                Integer val = Integer.valueOf(dataSnapshot.getValue().toString());
                //지금 Integer로 선언해서 여기에다 함수 끌어다 쓰면 됨
                Log.d("File", "aaa"+val);

                //데이터 변경
                chagam(val);

                //dataSnapshot.getRef().setValue(chagam(val));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //여기에다 꺼내면 null이 되버림
        //Log.d("FileDownActivity", "point? :"+point);

//        DownButton.setClickable(true);
//        DownButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ( pressedTime == 0 ) {
//                    Toast.makeText(FileDownActivity.this, " 한번 더 누르면 포인트가 차감됩니다." , Toast.LENGTH_LONG).show();
//                    pressedTime = System.currentTimeMillis();
//                }
//                else {
//                    int seconds = (int) (System.currentTimeMillis() - pressedTime);
//
//                    if ( seconds > 2000 ) {
//                        Toast.makeText(FileDownActivity.this, " 한번 더 누르면 포인트가 차감됩니다." , Toast.LENGTH_LONG).show();
//                        pressedTime = 0 ;
//                    }
//                    else {
//                        if(point >= 2) { //포인트가 2점넘게 있다면 출력해줌
//                            Glide.with(FileDownActivity.this).load(url2).into(ExamImage); // 원본 이미지 출력.
//                            point = point-2;
//                            Toast.makeText(FileDownActivity.this, " 남은포인트는 " + point + " point 입니다." , Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(FileDownActivity.this, " 포인트가 부족 합니다." , Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                }
//            }
//        });

    }

    final String userId = getUid();

    public void chagam(final int val) {

        DownButton.setClickable(true);
        DownButton.setOnClickListener(new View.OnClickListener() {
            int point = val;
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
                            //값 포인트 차감한 값으로 변경함
                            userpoints.setValue(point);
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
        startActivity(new Intent(FileDownActivity.this, GangUiActivity.class)); //아까 게시판에서 부른거라서 BoardActivity로 돌아가게 해둔거임
        finish();
    }

    //여기서 getUid 선언. return시 user id를 반환해준다
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
