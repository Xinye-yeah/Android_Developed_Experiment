package com.casper.testdrivendevelopment;
import androidx.appcompat.app.AppCompatActivity;
import android.media.Image;
import android.text.Layout;
import android.widget.ImageView;
import android.os.Bundle;
/**
 * Created by jszx on 2019/9/24.
 */

public class Book {
    private int coverID;
    private String strTitle;

    Book(int coverID, String strTitle) {
        this.coverID = coverID;
        this.strTitle = strTitle;
    }

    public int getCoverResourceID() {
        return coverID;
    }

    public String getStrTitle() {
        return strTitle;
    }

    void setStrTitle(String newTitle){
        strTitle = newTitle;
    }

}
