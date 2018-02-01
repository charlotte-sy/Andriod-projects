package com.example.android.boligchecker.room;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.boligchecker.MainActivity;
import com.example.android.boligchecker.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Manifest;
import com.squareup.picasso.Picasso;

import static android.R.attr.path;
import static com.example.android.boligchecker.R.id.imageView;

/**
 * Created by Seoyeon on 22/12/2017.
 */

public class WallOne extends AppCompatActivity {

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;

    ImageView btn_capture, imageExample;
    Button btn_album;

    String mCurrentPhotoPath;

    Uri imageUri;
    Uri photoURI, albumURI;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wall);

        setTitle(getString(R.string.title_wall_one));

       RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_image);
          recyclerView.setHasFixedSize(true);

          RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
          ArrayList<CreateList> createLists = prepareData();
         MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
           recyclerView.setAdapter(adapter);

    //    mOverView = (ImageView) findViewById(R.id.take_a_photo_button);
     //   mOverView.setOnClickListener(new btnTakePhotoClicker());

        btn_capture = (ImageView) findViewById(R.id.take_a_photo_button);
        btn_album = (Button) findViewById(R.id.btn_album_button);
       // imageExample = (ImageView) findViewById(R.id.image_example);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCamera();
            }
        });
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
            }
        });

   //     final Uri bringPhotoUri = Uri.parse("content://media/external/photo/boligchecker");

        //     Picasso.with(this)
        //             .load(bringPhotoUri)
        //          .placeholder(R.drawable.empty_room)
        //         .into(imageExample);

        checkPermission();
    }


    private ArrayList<CreateList> prepareData(){

        //https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/

        ArrayList<CreateList> theimage = new ArrayList<>();

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "boligchecker");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        File file[] = storageDir.listFiles();

        for(int i = 0; i< file.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_Location(file[i]);
            theimage.add(createList);
        }

        return theimage;
    }

    /////////////////////////////////////////////////////////////////////////////////


    private void captureCamera() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                      RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_image);
                      recyclerView.setHasFixedSize(true);

                      RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                      recyclerView.setLayoutManager(layoutManager);

                      ArrayList<CreateList> createLists = prepareData();
                      MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
                      recyclerView.setAdapter(adapter);
                }
            }
        } else {
            Toast.makeText(this, "Storage lackage", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "boligchecker");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;

    }


    private void getAlbum() {
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }


    private void galleryAddPic() {
        Log.i("galleryAddpic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();
                        btn_capture.setImageURI(imageUri);
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PICTURE", e.toString());
                    }
                } else {
                    Toast.makeText(WallOne.this, "cancel", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                        } catch (Exception e) {
                            Log.e("TAKE_ALBUM_SIMGEL ERROR", e.toString());
                        }
                    }
                }
                break;
        }
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("Alarm")
                        .setMessage("denied")
                        .setNeutralButton("setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("check", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                        MY_PERMISSION_CAMERA);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i=0;  i<grantResults.length; i++){
                if (grantResults[i] < 0) {
                    Toast.makeText(WallOne.this, "permission required", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            break;
        }

    }





}
