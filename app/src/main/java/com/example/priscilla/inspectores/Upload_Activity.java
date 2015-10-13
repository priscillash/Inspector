package com.example.priscilla.inspectores;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Upload_Activity extends Activity {

    private String fileUri;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        finish();
    }


    private class WstUploadFoto extends AsyncTask<String, Integer, Boolean> {


        String url = Constantes.GuardarMulta  + "/" + ConsultaInfraccion.tokenSession + "/" + "IMG_"+ Camera_Activity.timeStamp + ".jpg" ;
        Boolean resultado;
        @Override
        protected Boolean doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> pairs=  new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("imagen",encodedImage));
            System.out.println(encodedImage);

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            }catch(Exception e){

            }


            try {
                HttpResponse resp = httpClient.execute(httpPost);
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
        }


        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Boolean result){
            notificacion(result);
        }
    }

    public void notificacion(Boolean result){
        String res;

        if (result){
            res = "Fotografia guardada";
        }else{
            res ="No se ha podido guardar la fotografía";
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Gestion de Estacionamiento")
                .setContentText(res);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int mid = 1;
        mNotificationManager.notify(mid,mBuilder.build());
    }
}
