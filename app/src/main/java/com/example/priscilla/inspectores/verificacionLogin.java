package com.example.priscilla.inspectores;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class verificacionLogin extends Activity {
    String user, pass;
    int errorResponse;
    String tokenSessionResponse;
    boolean resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String dataUser[];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verificacion_login);

        Bundle bundle = getIntent().getExtras();
        dataUser = bundle.getStringArray("dataUser");

        //se guarda el contenido de los editText.
        user = dataUser[0];
        pass = dataUser[1];

        WebServiceTask wst = new WebServiceTask();
        wst.execute(user);

    }



    private class WebServiceTask extends AsyncTask<String, Integer, Boolean> {

        private static final String TAG = "verificacionLogin";
        private static final String imei = "/imei";
        String url = Constantes.LOGININSPECTOR + "/"+ user +"/"+pass + imei;
        private ProgressDialog progressDialog=null;

        @Override
        protected Boolean doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(url);
            del.setHeader("content-type", "application/json");

            try {
                publishProgress();
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject respJSON = new JSONObject(respStr);

                errorResponse = respJSON.getInt("codigoError");
                System.out.println(errorResponse);

                if (errorResponse == 200){
                    tokenSessionResponse = respJSON.getString("respuesta");
                    System.out.println(tokenSessionResponse);
                    resultado=true;

                }
                else {

                    resultado= false;

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
            progressDialog = new ProgressDialog(verificacionLogin.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);

        }


        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);

            progressDialog.show(verificacionLogin.this,"Espere","Iniciando sesión");

        }


        protected void onPostExecute(Boolean result){
            progressDialog.dismiss();
            if (result){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("token", tokenSessionResponse);
                returnIntent.putExtra("userLogged",user);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            else{
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }

        }

    }

}


