package e.a2727.project.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

//최근에 올린 글들을 볼 수 있는 Fragment이다
public class RecentPostsFragment extends PostListFragment {

    public RecentPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}