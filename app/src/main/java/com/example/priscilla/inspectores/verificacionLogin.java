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

public class verificacionLogin extends Activity {
    String user, pass;
    String userResponse;
    String passResponse;
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

        private static final String SERVICE_URL = "http://192.168.1.47:8081/RestWebService/rest/person";
        private static final String TAG = "verificacionLogin";
        String sampleURL = SERVICE_URL + "/demo";
        private ProgressDialog progressDialog=null;

        @Override
        protected Boolean doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(sampleURL);
            del.setHeader("content-type", "application/json");

            try {
                publishProgress();
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject respJSON = new JSONObject(respStr);

                userResponse = respJSON.getString("userName");
                passResponse = respJSON.getString("txtPass");

                if (user.equals(userResponse) && pass.equals(passResponse)) {
                    //Genero Token para mantener la sesión.
                    //String token;
                    //SessionIdentifierGenerator sessionId = new SessionIdentifierGenerator();
                    //token = sessionId.nextSessionId();
                    //Toast.makeText(verificacionLogin.this, token, Toast.LENGTH_LONG).show();

                    resultado=true;


                } else {

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

            progressDialog.show(verificacionLogin.this,"wait","Iniciando sesión");

        }


        protected void onPostExecute(Boolean result){
            progressDialog.dismiss();
            if (result){
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("tokenSession", token);
                returnIntent.putExtra("userLoged", user);
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


