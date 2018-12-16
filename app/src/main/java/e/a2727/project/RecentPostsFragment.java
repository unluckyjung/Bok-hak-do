package e.a2727.project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import e.a2727.project.Fragment.PostListFragment;

public class RecentPostsFragment extends PostListFragment {

    public RecentPostsFragment() {}

    //최근에 글 쓴 것들을 모으는 쿼리이다. 간단하다
    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;
    }
}