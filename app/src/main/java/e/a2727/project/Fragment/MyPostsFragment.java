package e.a2727.project.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

//PostList 중 내가 글 쓴걸 띄워주는 창이다. 기준은 user-posts에서 아이디가 동일한 것만 띄워준다
public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts").child(getUid());
    }
}