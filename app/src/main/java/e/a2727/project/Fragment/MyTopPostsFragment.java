package e.a2727.project.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

//자신이 올린 글 중 별점이 높은 순으로 글을 보여준다
public class MyTopPostsFragment extends PostListFragment {

    public MyTopPostsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)
                .orderByChild("starCount");
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}