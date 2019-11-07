package com.casper.testdrivendevelopment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.casper.Fragments.BookFragment;
import com.casper.controller.BookController;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class BookListMainActivity extends AppCompatActivity {
    int position;
    final int EDITTITLE = 1;
    final int NEWBOOK = 2;

    BookController bookController; //不能在此处构造BookController context仍然为null，需要在onCreate执行后才能构造
    BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        bookController = new BookController(this);
        bookAdapter = new BookAdapter(BookListMainActivity.this, R.layout.list_item_layout, bookController.query());


        BookFragmentAdapter bookFragmentAdapter = new BookFragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        BookFragment bookListFragment = new BookFragment(bookAdapter);
        fragments.add(bookListFragment);
        fragments.add(new BookFragment(bookAdapter));
        bookFragmentAdapter.setData(fragments);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("图书");
        titles.add("新闻");
        bookFragmentAdapter.setTitles(titles);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(bookFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v == findViewById(R.id.list_view_books))
            inflater.inflate(R.menu.list_item_context_menu, menu);
    }

    AdapterView.AdapterContextMenuInfo info;
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ct_create:
                Intent intent_newbook = new Intent(BookListMainActivity.this, EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                startActivityForResult(intent_newbook,NEWBOOK);
                break;

            case R.id.ct_delete:
                final AlertDialog.Builder deleteDialog =
                        new AlertDialog.Builder(BookListMainActivity.this);
                deleteDialog.setTitle("删除");
                deleteDialog.setMessage("你确定要删除此内容吗?");
                deleteDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                                position = info.position;
                                bookController.delete(new BookController.onDeleteBookListener() {
                                    @Override
                                    public void onComplete() {
                                        bookAdapter.notifyDataSetChanged();
                                        Toast.makeText(BookListMainActivity.this, "删除成功",Toast.LENGTH_SHORT).show();
                                    }
                                },position);
                            }
                        });

                deleteDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                deleteDialog.show();

                break;

            case R.id.ct_about:
                Toast.makeText(BookListMainActivity.this, "图书列表",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ct_edit:
                Intent intent = new Intent(BookListMainActivity.this, EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                intent.putExtra("Title",bookController.query().get(position).getStrTitle());
                startActivityForResult(intent,EDITTITLE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDITTITLE:

                if(resultCode == RESULT_OK){
                 bookController.edit(new BookController.onEditBookListener() {
                     @Override
                     public void onComplete() {
                         bookAdapter.notifyDataSetChanged();
                     }
                 },data.getStringExtra("Title"), position);
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this,"修改失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case NEWBOOK:

                if(resultCode == RESULT_OK){
                    bookController.add(new BookController.onAddBookListener() {
                        @Override
                        public void onComplete() {
                            bookAdapter.notifyDataSetChanged();
                        }
                    },R.raw.book_no_name, data.getStringExtra("Title"),position + 1);
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this,"新建失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookController.save();
    }
}
