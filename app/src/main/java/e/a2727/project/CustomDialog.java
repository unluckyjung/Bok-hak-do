package e.a2727.project;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import e.a2727.project.models.Post;

import static android.support.constraint.Constraints.TAG;



public class CustomDialog {

    private Context context;
    private static final String TAG = "CustomDialog : ";

    public CustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final String userId,final DatabaseReference mDatabase, final String str) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button button1 = (Button) dlg.findViewById(R.id.button1);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 버튼을 눌렀을 경우 처리하기 위해 설정해 놓음
                DeletePost(userId,mDatabase, str);
                //이후 커스텀 다이얼로그를 종료함
                dlg.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }

    private void DeletePost(String userId, DatabaseReference mDatabase, String key) {

        Log.e(TAG, key);

        mDatabase.child("posts").child(key).getRef().removeValue();

        if(userId != null) {
            mDatabase.child("user-posts").child(userId).child(key).getRef().removeValue();
        }

        if(mDatabase.child("post-comments").child(key).getRef() != null) {
            mDatabase.child("post-comments").child(key).getRef().removeValue();
        }




    }

}