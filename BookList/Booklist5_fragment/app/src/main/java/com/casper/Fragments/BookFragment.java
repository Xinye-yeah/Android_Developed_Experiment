package com.casper.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.casper.testdrivendevelopment.BookAdapter;
import com.casper.testdrivendevelopment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    private BookAdapter bookAdapter;
    public BookFragment(BookAdapter bookAdapter) {
        this.bookAdapter = bookAdapter;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);
        this.registerForContextMenu(listViewBooks);
        return view;
    }

}
