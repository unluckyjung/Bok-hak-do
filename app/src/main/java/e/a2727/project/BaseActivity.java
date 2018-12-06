package e.a2727.project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//각 Page의 AppcompatActivity 담당. 물론 각각의 Page는 따로 xml 설정은 해줘야 함
public class BaseActivity extends AppCompatActivity {

    //꽤 구식이긴 하지만 Dialog 창을 띄움
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //여기서 getUid 선언. return시 user id를 반환해준다
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}