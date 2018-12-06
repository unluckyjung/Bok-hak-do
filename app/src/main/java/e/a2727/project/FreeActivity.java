package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import e.a2727.project.Fragment.MyPostsFragment;
import e.a2727.project.Fragment.MyTopPostsFragment;

//BaseActivity가 Appcompat Activity인데, 내부에 Dialog 적용하기 위해 통일함
public class FreeActivity extends BaseActivity {

    //그냥 Tag값 설정
    private static final String TAG = "FreeActivity";

    //Fragment 사용하기 위해 Adapter 설정함
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        // 각각의 상황에 맞추어 어댑터가 Fragment를 반환하게 함
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            //각각의 fragment를 설정
            private final Fragment[] mFragments = new Fragment[] {
                    new RecentPostsFragment(),
                    new MyPostsFragment(),
                    new MyTopPostsFragment(),
            };
            //맨 위에 3개의 버튼 보이게 하는 것
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };
            //그 상황에 맞는 Fragment에 대한 position 반환
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            //개수 세기
            @Override
            public int getCount() {
                return mFragments.length;
            }
            //그 Fragment에 맞는 Page 타이틀 따오기
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // View Pager를 어댑터에 맞추어 설정해버림
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // 새로 글쓰기 버튼
        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreeActivity.this, NewPostActivity.class));
            }
        });
    }

    //옵션 메뉴라고는 하는데 어디있는거임?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //로그아웃 버튼 클릭 시 맨 처음 페이지로 돌아가게 하는데 어디있는 건지는 나도 잘 모름
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}