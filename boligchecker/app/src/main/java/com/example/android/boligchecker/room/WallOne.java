package com.example.android.boligchecker.room;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.ToggleButton;

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

import com.example.android.boligchecker.SplashActivity;
import com.example.android.boligchecker.base.MyContextWrapper;
import com.squareup.picasso.Picasso;

import static android.R.attr.path;
import static android.support.v4.content.FileProvider.getUriForFile;
import static com.example.android.boligchecker.R.id.button_language;
import static com.example.android.boligchecker.R.id.imageView;
import static com.example.android.boligchecker.R.id.switch_dan;
import static com.example.android.boligchecker.R.id.switch_eng;
import static com.example.android.boligchecker.R.id.switch_nor;

/**
 * Created by Seoyeon on 22/12/2017.
 */

public class WallOne extends AppCompatActivity {

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;

    ImageView btn_capture;
    Button btn_album;

    String mCurrentPhotoPath;
    String nameWall = "wall 1";
   // "boligchecker"

    Uri imageUri;
    Uri photoURI, albumURI;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String localRoomType = MyContextWrapper.getLocalRoomType(this);
        if("1".equals(localRoomType)){
            setTitle(getString(R.string.title_wall_one));
            nameWall = "wall_1";}
        else if("2".equals(localRoomType)){
            setTitle(getString(R.string.title_wall_two));
            nameWall = "wall_2";
            prepareData();
            }
        else if("3".equals(localRoomType)){
            setTitle(getString(R.string.title_wall_three));
            nameWall = "wall_3";
            prepareData();
            }
        else {
            setTitle(getString(R.string.title_wall_four));
            nameWall = "wall_4";
            prepareData();
            }


        setContentView(R.layout.wall);
      //  setTitle(getString(R.string.title_wall_one));
        //   Log.i("getString", getLocalClassName());

        recreateData();

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


        ToggleButton roomButton1 = (ToggleButton) findViewById(R.id.room_button1);
        ToggleButton roomButton2 = (ToggleButton) findViewById(R.id.room_button2);
        ToggleButton roomButton3 = (ToggleButton) findViewById(R.id.room_button3);
        ToggleButton roomButton4 = (ToggleButton) findViewById(R.id.room_button4);

        SharedPreferences sharedPrefs = getSharedPreferences("com.example.android.boligchecker.wall", MODE_PRIVATE);
        roomButton1.setChecked(sharedPrefs.getBoolean("1", true));
        roomButton2.setChecked(sharedPrefs.getBoolean("2", false));
        roomButton3.setChecked(sharedPrefs.getBoolean("3", false));
        roomButton4.setChecked(sharedPrefs.getBoolean("4", false));

        roomButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.android.boligchecker.wall", MODE_PRIVATE).edit();
                editor.putBoolean("1", true);
                editor.putBoolean("2", false);
                editor.putBoolean("3", false);
                editor.putBoolean("4", false);
                editor.commit();
                editor.apply();

                MyContextWrapper.setLocalRoomType(WallOne.this,"1");
                Intent refresh = getIntent();
                finish();
                startActivity(refresh);
            }
        });

        roomButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.android.boligchecker.wall", MODE_PRIVATE).edit();
                editor.putBoolean("1", false);
                editor.putBoolean("2", true);
                editor.putBoolean("3", false);
                editor.putBoolean("4", false);
                editor.commit();
                editor.apply();

                MyContextWrapper.setLocalRoomType(WallOne.this,"2");
                Intent refresh = getIntent();
                finish();
                startActivity(refresh);
            }
        });
        roomButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.android.boligchecker.wall", MODE_PRIVATE).edit();
                editor.putBoolean("1", false);
                editor.putBoolean("2", false);
                editor.putBoolean("3", true);
                editor.putBoolean("4", false);
                editor.commit();
                editor.apply();

                MyContextWrapper.setLocalRoomType(WallOne.this,"3");
                Intent refresh = getIntent();
                finish();
                startActivity(refresh);
            }
        });
        roomButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.android.boligchecker.wall", MODE_PRIVATE).edit();
                editor.putBoolean("1", false);
                editor.putBoolean("2", false);
                editor.putBoolean("3", false);
                editor.putBoolean("4", true);
                editor.commit();
                editor.apply();

                MyContextWrapper.setLocalRoomType(WallOne.this,"4");
                Intent refresh = getIntent();
                finish();
                startActivity(refresh);
            }
        });


        checkPermission();
    }





    private ArrayList<CreateList> prepareData(){

        //https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/

        ArrayList<CreateList> theimage = new ArrayList<>();

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", nameWall);

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        File[] file = storageDir.listFiles();

        if(file != null) {

            for (int i = 0; i < file.length; i++) {
                CreateList createList = new CreateList();
                createList.setImage_Location(file[i]);
                theimage.add(createList);
            }

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
                    Log.e("photoFile", photoFile.toString());
                    Log.e("getPackageName()", "content://com.example.android.boligchecker");
                    Log.e("providerURI", getUriForFile(this, getPackageName() + ".room", photoFile).toString());
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {

                    Uri providerURI = getUriForFile(this, getPackageName() + ".room", photoFile);
                    Log.e("providerURI", getUriForFile(this, getPackageName() + ".room", photoFile).toString());
                    imageUri = providerURI;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                    recreateData();
                }
            }
        } else {
            Toast.makeText(this, "Storage lackage", Toast.LENGTH_SHORT).show();
            return;

        }
    }


    public void recreateData() {

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_image);
             recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(layoutManager);

          ArrayList<CreateList> createLists = prepareData();
          MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
          recyclerView.setAdapter(adapter);

    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", nameWall);

        if (!storageDir.exists()) {
            Log.e("mCurrentPhotoPath1", storageDir.toString());
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
                                intent.setData(Uri.parse("package:" + getPackageName()+ ".room"));
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
