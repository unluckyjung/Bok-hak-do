package e.a2727.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//교수님 액티비티이다.
public class ProfessorActivity extends AppCompatActivity {

    private EditText search;
    private List<String> list;
    private ListView listView;
    private ArrayList<String> arraylist;
    private SearchAdapter adapter2;
    private ArrayList<String> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

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
        adapter2 = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter2);

        //리스트 뷰를 눌렀을 시의 이벤트를 진행한다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = arraylist.get(position);
                //여기에다 if 하고 a == 하고싶은 이름 쓴 뒤 액티비티 연결하면 된다.
                if(a == "장재우") {
                    startActivity(new Intent(ProfessorActivity.this, Jang.class));
                    finish();
                }
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfessorActivity.this, BoardActivity.class));
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
        adapter2.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다. 여기에 원하는 이름 작성하면 됨 ㄹㅇ
    private void settingList(){
        list.add("장재우");
        list.add("홍득조");
        list.add("안계현");
        list.add("조희승");
        list.add("곽영태");
        list.add("김영천");
        list.add("황지원");
        list.add("안동언");

    }

}
