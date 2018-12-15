package e.a2727.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GangUiActivity extends AppCompatActivity {

    private String TAG = "GangUiActivity";

    private Button post;
    private EditText search;
    private List<String> list;
    private ListView listView;
    private ArrayList<String> arraylist;
    private SearchAdapter adapter;
    private ArrayList<String> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gangui);
        post = findViewById(R.id.post);
        search = findViewById(R.id.search);
        listView = findViewById(R.id.listview);

        // 리스트를 생성한다.
        list = new ArrayList<String>();
        data = new ArrayList<>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        //리스트 뷰를 눌렀을 시의 이벤트를 진행한다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = arraylist.get(position);
                //여기에다 if 하고 a == 하고싶은 이름 쓴 뒤 액티비티 연결하면 된다.
                if(a == "장재우_2017_알고리즘2차") {
                    startActivity(new Intent(GangUiActivity.this, FileDownActivity.class));
                    finish();
                }
            }
        });

        //올리기 버튼이다.
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GangUiActivity.this, PostActivity.class));
                finish();
            }
        });

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = search.getText().toString();
                search(text);
            }
        });

    }

    //뒤로가기 버튼이다.
    @Override
    public void onBackPressed() {
        startActivity(new Intent(GangUiActivity.this, MainActivity.class));
        finish();
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                        // 검색된 데이터를 리스트에 추가한다.
                        list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다. 여기에 원하는 이름 작성하면 됨
    private void settingList(){
        list.add("장재우_2017_알고리즘2차");
        list.add("장재우_2017_자료구조2차");
        list.add("장재우_2016_데이터베이스2차");
        list.add("홍득조_2017_정보이론중간");
        list.add("홍득조_2017_정보이론기말");
        list.add("홍득조_2017_보안이론중간");
        list.add("안계현_2016_데이터통신중간");
        list.add("안계현_2016_데이터통신기말");
        list.add("조희승_2015_시스템프로그래밍중간");
        list.add("조희승_2015_시스템프로그래밍기말");
        list.add("조희승_2017_운영체제중간");
        list.add("조희승_2017_운영체제기말");
        list.add("곽영태_2016_선형대수학중간");
        list.add("곽영태_2015_선형대수학기말");
        list.add("곽영태_2017_수차해석중간");
        list.add("곽영태_2017_수치해석기말");

    }

}
