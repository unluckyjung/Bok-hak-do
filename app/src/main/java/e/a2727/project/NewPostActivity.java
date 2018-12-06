package e.a2727.project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import e.a2727.project.models.Post;
import e.a2727.project.UserProfile;

import java.util.HashMap;
import java.util.Map;

//새로운 글을 올릴 때 사용하는 Activity이다
public class NewPostActivity extends BaseActivity {

    //그냥 log에서 어떠한 걸 출력할 때 TAG를 굳이 final로 선언해야 한다. 아래도 동일
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // 데이터베이스를 참조하기 위해 선언
    private DatabaseReference mDatabase;

    //아래는 그냥 내부 xml 안에 존재하는 것들 맨날 선언하듯이 선언
    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // 데이터베이스의 현재 참조 위치 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        //글 다 쓰고 제출 버튼이다
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    //여기서 실제적인 제출 함수가 적용됨
    private void submitPost() {
        //title은 제목, body는 글 내용을 의미한다. 각각의 적은 내용을 담음
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // 만약 제목이 비어있는 경우 에러 출력
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // 만약 글 내용이 비어있는 경우 에러 출력
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // 수정이 불가능 하게 하여 제출 도중 두 번 글이 안 써지게 한다
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // 이제 실제로 제출 하기 위해 유저의 각각의 값을 받아 사용한다
        //아이디 찾기
        final String userId = getUid();
        //데이터베이스의 child(내부 트리) users - userId 기준으로 데이터 긁어온다. 여기서 내부 트리가 잘못 될 경우(Register send 경로와 다를 경우) 계속 null 유도됨
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    //datasnapshot이라고 현재 user의 값을 userprofile에 맞추어 가져온다
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        //이렇게 설정하여 UserProfile 내 데이터를 읽어 온다
                        UserProfile user = dataSnapshot.getValue(UserProfile.class);
                        // [START_EXCLUDE] - 만약 데이터 없으면? 설정 안됨
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.userName, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    //만약 취소 된다면? 취소됨 하고 수정 가능케 함
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    //그냥 수정 가능케 함을 메시지 출력과 함께 알려주는 부분이다
    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // 이제 전부 끝난 후 글을 데이터베이스 - posts와 user-posts에 넣는데 추후 user-posts를 통해 삭제가 가능하게 하려고 준비중이다.
    //각각의 위치는 Fragment에서 3개 중 2개에 해당한다
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}