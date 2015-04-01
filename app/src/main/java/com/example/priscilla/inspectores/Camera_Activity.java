package com.example.priscilla.inspectores;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Camera_Activity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static String timeStamp = null;
    public String encodedImage;
    public Button botonUpload;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_);

        // crear intent para tomar la fotografía y retornar luego el control a la aplicación
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // crear directorio para guardar imágen
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // setear nombre del archivo

        // se lanza el intent para la captura de fotografía
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            TextView titulo1 = (TextView) findViewById(R.id.titulo1);
            if (resultCode == RESULT_OK) {
                titulo1.setText("Se ha guardado la foto");
                //set the properties for button
                botonUpload = new Button(this);
                botonUpload.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                botonUpload.setText("UPLOAD FOTO?");
                botonUpload.setBackgroundColor(Color.argb(5,0,51,102));
                botonUpload.setFocusable(true);

                //add button to the layout

                LinearLayout ll = (LinearLayout)findViewById(R.id.upload_foto);
                ll.addView(botonUpload);


                botonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent openIntent = new Intent(Camera_Activity.this, Upload_Activity.class);
                        openIntent.putExtra("file_uri",fileUri.toString());
                        startActivity(openIntent);

                    }
                });

            } else if (resultCode == RESULT_CANCELED) {
                titulo1.setText("Acción cancelada");
            } else {
                // Image capture failed, advise user
                titulo1.setText("Se ha producido un error");
            }
        }

    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");


        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }




}