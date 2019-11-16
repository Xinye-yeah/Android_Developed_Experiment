package com.casper.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.casper.controller.BookController;
import com.casper.testdrivendevelopment.BookAdapter;

import com.casper.testdrivendevelopment.EditBookActivity;
import com.casper.testdrivendevelopment.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    private int position;
    private final int EDITTITLE = 1;
    private final int NEWBOOK = 2;
    private Context context;
    private BookController bookController;
    private BookAdapter bookAdapter;
    private OnFragmentInteractionListener mListener;
    public BookFragment() {

        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);






        bookController = new BookController(context);
        bookAdapter = new BookAdapter(context, R.layout.list_item_layout, bookController.query());
        listViewBooks.setAdapter(bookAdapter);
        this.registerForContextMenu(listViewBooks);


        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,@NonNull View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = ((Activity)context).getMenuInflater();

        if(v == ((Activity)context).findViewById(R.id.list_view_books))
            inflater.inflate(R.menu.list_item_context_menu, menu);
    }

    private AdapterView.AdapterContextMenuInfo info;
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ct_create:
                Intent intent_newbook = new Intent(context, EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                startActivityForResult(intent_newbook,NEWBOOK);
                break;

            case R.id.ct_delete:
                final AlertDialog.Builder deleteDialog =
                        new AlertDialog.Builder(context);
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
                                        Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "图书列表",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ct_edit:
                Intent intent = new Intent(context, EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                intent.putExtra("Title",bookController.query().get(position).getStrTitle());
                startActivityForResult(intent,EDITTITLE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    Toast.makeText(context,"修改失败", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context,"新建失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        bookController.save();
    }

    public interface OnFragmentInteractionListener {
        //Callback interface
    }
}


