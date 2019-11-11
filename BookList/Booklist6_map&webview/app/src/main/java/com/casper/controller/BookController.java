package com.casper.controller;

import android.content.Context;

import com.casper.model.BookModel;
import com.casper.testdrivendevelopment.Book;

import java.util.ArrayList;

public class BookController {

    private BookModel bookModel;
    public BookController(Context context) {
        bookModel = new BookModel(context);
    }

    /*
    添加书籍
    @param Listener
     */

    public void add(onAddBookListener listener,int image,String title,int position) {
        //Check if list is empty
        if(bookModel.query().isEmpty()) return;

        bookModel.addBook(image,title,position);
        if(listener != null) {
            listener.onComplete();
        }
    }

    public void delete(onDeleteBookListener listener,int position) {
        bookModel.deleteBook(position);
        if(listener != null) {
            listener.onComplete();
        }
    }

    public void edit(onEditBookListener listener,String title,int position) {
        bookModel.editBook(title,position);
        if(listener != null) {
            listener.onComplete();
        }
    }

    public void save(){
        bookModel.saveData();
    }

    public ArrayList<Book> query() {
        return bookModel.query();
    }

    public interface onAddBookListener{
        void onComplete();
    }

    public interface onDeleteBookListener{
        void onComplete();
    }

    public interface onEditBookListener{
        void onComplete();
    }
}
