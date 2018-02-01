package com.example.android.boligchecker.room;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Seoyeon on 01/02/2018.
 */

public class CreateList {

    private String image_title;
    private File image_location;
    private Integer image_id;


    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {return image_id;}
    public void setImage_ID(Integer android_image_url) {this.image_id = android_image_url;}

    public void setImage_Location(File uri) {this.image_location = uri;}
    public File getImage_Location() {return image_location;}

}
