package com.example.priscilla.inspectores;

import java.math.BigInteger;
import java.security.SecureRandom;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class verificacionLogin extends Activity{
    String user, pass;
    private static final String SERVICE_URL = "http://192.168.1.47:8081/RestWebService/rest/person";
    private static final String TAG = "verificacionLogin";

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

        String sampleURL = SERVICE_URL + "/demo";

        //WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this, "GETting data...");

        //wst.execute(new String[] { sampleURL });

        //String response = wst.doInBackground(sampleURL);
        //System.out.println(response);
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet del = new HttpGet(sampleURL);
        del.setHeader("content-type", "application/json");

        try
        {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());

            JSONObject respJSON = new JSONObject(respStr);

            String userResponse = respJSON.getString("userName");
            String passResponse = respJSON.getString("txtPass");
            if (user.equals(userResponse) && pass.equals(passResponse)) {

                //Genero Token para mantener la sesión.
                //String token;
                //SessionIdentifierGenerator sessionId = new SessionIdentifierGenerator();
                //token = sessionId.nextSessionId();
                //Toast.makeText(verificacionLogin.this, token, Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("tokenSession", token);
                returnIntent.putExtra("userLoged", user);
                setResult(RESULT_OK, returnIntent);
                finish();

            } else {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();

            }


        }
        catch(Exception ex)
        {
            Log.e("ServicioRest","Error!", ex);
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }
        }



/*
    public void clearControls(View vw) {

        EditText ed_UserId = (EditText) findViewById(R.id.User_ID);
        EditText ed_Pass = (EditText) findViewById(R.id.Pass);

        ed_UserId.setText("");
        ed_Pass.setText("");

    }

    public void postData(View vw) {

        EditText ed_UserId = (EditText) findViewById(R.id.User_ID);
        EditText ed_Pass = (EditText) findViewById(R.id.Pass);

        String UserId = ed_UserId.getText().toString();
        String Pass = ed_Pass.getText().toString();

        if (UserId.equals("") || Pass.equals("")) {
            Toast.makeText(this, "Please enter in all required fields.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this, "Posting data...");

        wst.addNameValuePair("firstName", UserId);
        wst.addNameValuePair("lastName", Pass);

        // the passed String is the URL we will POST to
        wst.execute(new String[]{SERVICE_URL});

    }

    public void handleResponse(String response) {

        EditText ed_UserId = (EditText) findViewById(R.id.User_ID);
        EditText ed_Pass = (EditText) findViewById(R.id.Pass);

        ed_UserId.setText("");
        ed_Pass.setText("");

        try {

            JSONObject jso = new JSONObject(response);

            String firstName = jso.getString("firstName");
            String lastName = jso.getString("lastName");

            ed_UserId.setText(firstName);
            ed_Pass.setText(lastName);

            } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }

    }
    private void hideKeyboard() {

        InputMethodManager inputManager = (InputMethodManager) verificacionLogin.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(
                verificacionLogin.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class WebServiceTask extends AsyncTask<String, Integer, String> {

        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;

        private static final String TAG = "WebServiceTask";

        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;

        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;

        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Processing...";

        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        private ProgressDialog pDlg = null;

        public WebServiceTask(int taskType, Context mContext, String processMessage) {

            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }

        public void addNameValuePair(String name, String value) {

            params.add(new BasicNameValuePair(name, value));
        }

        private void showProgressDialog() {

            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }

        @Override
        protected void onPreExecute() {

            hideKeyboard();
            showProgressDialog();

        }

        protected String doInBackground(String... urls) {

            String url = urls[0];
            String result = "";

            HttpResponse response = doResponse(url);

            if (response == null) {
                return result;
            } else {

                try {

                    result = inputStreamToString(response.getEntity().getContent());

                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {

            handleResponse(response);
            pDlg.dismiss();

        }

        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {

            HttpParams htpp = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

            return htpp;
        }

        private HttpResponse doResponse(String url) {

            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());

            HttpResponse response = null;

            try {
                switch (taskType) {

                    case POST_TASK:
                        HttpPost httppost = new HttpPost(url);
                        // Add parameters
                        httppost.setEntity(new UrlEncodedFormEntity(params));

                        response = httpclient.execute(httppost);
                        break;
                    case GET_TASK:
                        HttpGet httpget = new HttpGet(url);
                        response = httpclient.execute(httpget);
                        break;
                }
            } catch (Exception e) {

                Log.e(TAG, e.getLocalizedMessage(), e);

            }

            return response;
        }

        private String inputStreamToString(InputStream is) {

            String line = "";
            StringBuilder total = new StringBuilder();

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }

            // Return full string
            return total.toString();
        }

    }

    public final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();
        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }
*/
}
