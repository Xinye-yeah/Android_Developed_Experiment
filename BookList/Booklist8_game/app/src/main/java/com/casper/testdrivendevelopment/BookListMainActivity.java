package com.casper.testdrivendevelopment;

import android.os.Bundle;
import android.provider.CalendarContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.casper.Fragments.BookFragment;
import com.casper.Fragments.GameFragment;
import com.casper.Fragments.MapViewFragment;
import com.casper.Fragments.WebViewFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class BookListMainActivity extends AppCompatActivity implements BookFragment.OnFragmentInteractionListener {
    static public BookListMainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);
        instance = this;

        BookFragmentAdapter bookFragmentAdapter = new BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BookFragment());
        //BookFragment bookListFragment = new BookFragment();
        //fragments.add(bookListFragment);
        fragments.add(new WebViewFragment());
        fragments.add(new MapViewFragment());
        fragments.add(new GameFragment());

        bookFragmentAdapter.setData(fragments);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("卖家");
        titles.add("游戏");
        bookFragmentAdapter.setTitles(titles);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(bookFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}

