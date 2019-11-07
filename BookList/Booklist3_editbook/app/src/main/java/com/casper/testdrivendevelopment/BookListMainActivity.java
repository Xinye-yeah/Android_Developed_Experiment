package com.casper.testdrivendevelopment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BookListMainActivity extends AppCompatActivity {
    int position;
    final int EDITTITLE = 1;
    final int NEWBOOK = 2;
    ListView listViewBooks;
    BookAdapter bookAdapter;
    ArrayList<Book> bookList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        listViewBooks = findViewById(R.id.list_view_books);
        //ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book(R.drawable.book_1,getString(R.string.book_1)));
        bookList.add(new Book(R.drawable.book_2,getString(R.string.book_2)));
        bookList.add(new Book(R.drawable.book_no_name,getString(R.string.book_no_name)));

        bookAdapter = new BookAdapter(BookListMainActivity.this, R.layout.list_item_layout, bookList);

        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
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
                                bookList.remove(info.position);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "删除成功",Toast.LENGTH_SHORT).show();
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
                intent.putExtra("Title",bookList.get(position).getStrTitle());
                startActivityForResult(intent,EDITTITLE);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDITTITLE:
                if(resultCode == RESULT_OK){
                 bookList.get(position).setStrTitle(data.getStringExtra("Title"));
                 bookAdapter.notifyDataSetChanged();
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this,"修改失败", Toast.LENGTH_SHORT).show();
                }
            case NEWBOOK:
                if(resultCode == RESULT_OK){
                    bookList.add(position+1,new Book(R.drawable.book_no_name,data.getStringExtra("Title")));
                    bookAdapter.notifyDataSetChanged();
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this,"新建失败", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
