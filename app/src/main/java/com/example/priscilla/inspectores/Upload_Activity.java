package com.example.priscilla.inspectores;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Upload_Activity extends Activity {

    private String fileUri;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);

        Bundle bundle = getIntent().getExtras();
        fileUri = bundle.getString("file_uri");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream ios = null;
        System.out.println(fileUri);
        File file = new File(fileUri.substring(7));
        System.out.println(fileUri.substring(7));

        try  {
            Bitmap bitmap = null;
            byte[] buffer = new byte[1024];
            ios = new FileInputStream(file);
            int read = 0;
            while((read= ios.read(buffer))!=-1){

                baos.write(buffer, 0 , read);
            }

            byte[] imageBytes = baos.toByteArray();
            System.out.println(imageBytes.length);
            encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
            //System.out.println(encodedImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {

                if (baos != null) {
                    baos.close();

                }
            }catch (Exception e){}
            try {

                if (ios != null) {
                    ios.close();

                }
            }catch (Exception e){}
        }
        WstUploadFoto unwstUploadFoto= new WstUploadFoto();
        unwstUploadFoto.execute();
    }






    private class WstUploadFoto extends AsyncTask<String, Integer, Boolean> {


        String url = Constantes.GuardarMulta  + "/" + ConsultaInfraccion.tokenSession + "/" + "IMG_"+ Camera_Activity.timeStamp + ".jpg" + "/" + encodedImage;
        //private ProgressDialog progressDialog = null;
        Boolean resultado;
        @Override
        protected Boolean doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            //System.out.println(url);
            HttpGet del = new HttpGet(url);
            del.setHeader("content-type", "application/json");

            try {
                //publishProgress();
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject respJSON = new JSONObject(respStr);

                int errorResponse = respJSON.getInt("codigoError");
                System.out.println(errorResponse);

                if (errorResponse == 200) {


                    resultado = true;

                } else {

                    resultado = false;

                }

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resultado = false;
            }
            return resultado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = new ProgressDialog(Camera_Activity.this);
            //progressDialog.setCancelable(true);
            //progressDialog.setIndeterminate(true);

        }


        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);

            //progressDialog = ProgressDialog.show(Camera_Activity.this,"Espere","Consultando");

        }

        @Override
        protected void onPostExecute(Boolean result){
            //progressDialog.dismiss();

        }
    }


}
