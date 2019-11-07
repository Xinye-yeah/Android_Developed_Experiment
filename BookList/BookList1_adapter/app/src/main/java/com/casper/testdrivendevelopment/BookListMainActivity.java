package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BookListMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        ListView listView = findViewById(R.id.list_view_books);
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book(R.drawable.book_1,getString(R.string.book_1)));
        bookList.add(new Book(R.drawable.book_2,getString(R.string.book_2)));
        bookList.add(new Book(R.drawable.book_no_name,getString(R.string.book_no_name)));

        BookAdapter adapter = new BookAdapter(BookListMainActivity.this, R.layout.list_item_layout, bookList);

        listView.setAdapter(adapter);
    }

}
