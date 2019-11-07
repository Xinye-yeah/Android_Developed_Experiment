package com.casper.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.casper.testdrivendevelopment.Book;
import com.casper.testdrivendevelopment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class BookModel {
    private static ArrayList<Book> bookList = new ArrayList<>();
    private Context context;


    public BookModel(Context context) {
        this.context = context;
        try{
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("Serializable.txt"));
            bookList = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bookList.add(new Book(R.raw.book_1,"信息安全数学基础"));
            bookList.add(new Book(R.raw.book_2,"软件项目管理案例教程"));
            bookList.add(new Book(R.raw.book_no_name,"创新工程实践"));
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
            bookList.add(new Book(R.raw.book_1,"信息安全数学基础"));
            bookList.add(new Book(R.raw.book_2,"软件项目管理案例教程"));
            bookList.add(new Book(R.raw.book_no_name,"创新工程实践"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*添加书籍
     @param image 图片资源
     @param title 书名
     @param position 新建位置，建议使用info.position
     */
    public void addBook(int image,String title,int position){
        bookList.add(position, new Book(image,title));
    }

    //删除书籍
    public void deleteBook(int position){
        bookList.remove(position);
    }

    //编辑书籍
    public void editBook(String title, int position) {
        bookList.get(position).setStrTitle(title);
    }

    //获取书籍列表
    public ArrayList<Book> query() {
        return bookList;
    }

    //保存数据
    public void saveData() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE));
            outputStream.writeObject(bookList);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
