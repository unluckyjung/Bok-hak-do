package e.a2727.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import junit.framework.Test;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder> mfirebaseAdapter;

    public static final String MESSAGES_CHILD = "messages_child";
    private DatabaseReference mFirebaseDatabaseReference; //파이어베이스 DB접근용 객체
    private EditText mMessageEditText;





    private String mUsername = "익명";//대나무숲이니까 익명으로둠






    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView messageTextView;
        CircleImageView photoImageView;

        public MessageViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }
    }

    private RecyclerView mMessageRecyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference(); //Firebase Realtime DB 에서 시작지점을 받는 레퍼런스.
        mMessageEditText = findViewById(R.id.message_edit);
        mMessageRecyclerView = findViewById(R.id.message_recycler_view);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() { //보내기를 눌렀을시 메시지를 전송하는 코드
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = new ChatMessage(mMessageEditText.getText().toString(),mUsername);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD) //DB에 child라는 디렉토리를 하나만들고 그아래에서 데이터를 넣겠다는 이야기.
                        .push() //새 데이터를 넣는다.
                        .setValue(chatMessage);
                mMessageEditText.setText("");

            }
        });

        Query query = mFirebaseDatabaseReference.child(MESSAGES_CHILD); //난 이쿼리를 실행한다 = 메시지 전체의 데이터를 얻겟다.
        FirebaseRecyclerOptions<ChatMessage> options = new FirebaseRecyclerOptions.Builder<ChatMessage>() //리사이클 어뎁터가 어떤 형태를 어디서 가져올것이며, 어떤 형태의 데이터 class에 결과를 반환할것인지를 정함
                .setQuery(query, ChatMessage.class)
                .build();

        mfirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(options) { //어뎁터 초기화
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull ChatMessage model) {
                //뷰홀더를 생성하는 코드.
                //홀더가 넘어오고 poistion이 넘어오고 Message가 넘어옴.
                holder.messageTextView.setText(model.getText());
                holder.nameTextView.setText(model.getName());
                holder.photoImageView.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this,
                        R.drawable.ic_account_circle_black_24dp));
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message,parent,false);

                return new MessageViewHolder(view);
            }
        };
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecyclerView.setAdapter(mfirebaseAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mfirebaseAdapter.stopListening();
    }
}
