package e.a2727.project;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 2017-08-07.
 */

public class CustomDialog2 {

    private Context context;
    private static final String TAG = "CustomDialog2 : ";

    public CustomDialog2(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final String userId,final DatabaseReference mDatabase) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog2);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText editor = dlg.findViewById(R.id.editor);
        final Button button = (Button) dlg.findViewById(R.id.button);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 버튼을 눌렀을 경우 처리하기 위해 설정해 놓음
                DeletePost(userId,mDatabase);
                //이후 커스텀 다이얼로그를 종료함
                dlg.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정 버튼을 눌렀을 경우 처리하기 위해 설정해놓음

                // 커스텀 다이얼로그를 종료한다.
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

    private void DeletePost(String userId, DatabaseReference mDatabase) {
        String key = mDatabase.child("posts").push().getKey();

        Log.e(TAG, key);
        Query deleteQuery = mDatabase.equalTo(key);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                    delSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


    }

}