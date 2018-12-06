package e.a2727.project.models;

import com.google.firebase.database.IgnoreExtraProperties;

// 코멘트 - 댓글에 해당하는 부분이다. 각각의 유저 아이디, 작성자, 글을 한데 모아 돌려준다(반환한다) - PostDetailActivity에서 사용된다
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}
// [END comment_class]