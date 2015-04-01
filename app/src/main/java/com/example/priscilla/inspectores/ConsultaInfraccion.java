package com.example.priscilla.inspectores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import com.example.priscilla.inspectores.dialog_logout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConsultaInfraccion extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private String userLogged;
    public static String tokenSession;
    private String dateTimeConsulta;
    private String dateTimeEndTicket;

    int errorResponse;
    JSONObject consultaResponse;
    JSONArray historicoResponse;
    Boolean resultado;
    String matricula;
    Boolean multa = true;
    public static List unalistaConsultas = new ArrayList<Consulta>();


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_infraccion);

        Bundle unBundle = getIntent().getExtras();
        if (unBundle != null){
            userLogged=unBundle.getString("UserLoged");
            tokenSession=unBundle.getString("tokenSession");
            System.out.println("consulta" + tokenSession);

        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public String getTokenSession(){

        return tokenSession;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment  fragment = null;
        dialog_logout dialog = null;
        switch (position) {
            case 0:
                fragment = new fragment_consulta();
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                //fragment = new fragment_historico();
                mTitle = getString(R.string.title_section2);
                historico();
                break;
            case 2:
                showDialog();
        }

        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(mTitle.toString())
                    .commit();

        }


    }
    public void showDialog() {
        //FragmentManager fm = getSupportFragmentManager();
        dialog_logout newFragment = dialog_logout.newInstance(R.string.desea_cerrar_sesión);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {

        Intent openIntent = new Intent(this, MainActivity.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openIntent);

    }

    public void doNegativeClick() {


    }

    public void consulta(String matricula){

        this.matricula = matricula;
        WstConsulta unwstConsulta = new WstConsulta();
        unwstConsulta.execute(matricula);

    }

    public void historico(){

        WstHistorico unwstHistorico= new WstHistorico();
        unwstHistorico.execute(tokenSession);

    }


    public void onSectionAttached(int number) {

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.consulta_infraccion, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_consulta_infraccion, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ConsultaInfraccion) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    private class WstConsulta extends AsyncTask<String, Integer, Boolean> {

        String url = Constantes.CONSULTAMATRICULA  + "/" + tokenSession + "/" + matricula;
        private ProgressDialog progressDialog = null;

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

                if (errorResponse == 200) {
                    consultaResponse = respJSON.getJSONObject("datosVariables");
                    System.out.println(consultaResponse);
                    dateTimeConsulta = consultaResponse.getString("dateTime");
                    System.out.println(dateTimeConsulta);
                    dateTimeEndTicket = consultaResponse.getString("dateTimeTicket");
                    System.out.println(dateTimeEndTicket);

                    if (dateTimeEndTicket != "null"){

                        SimpleDateFormat fmt = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
                        Date dTConsulta = fmt.parse(dateTimeConsulta);
                        System.out.println(dTConsulta);

                        Date dTticket = fmt.parse(dateTimeEndTicket);
                        System.out.println(dTticket);


                        if (dTticket.before(dTConsulta)){
                            System.out.println("Corresponde multa");
                            multa = true;
                        }else {
                            System.out.println("No Corresponde multa");
                             multa = false;
                        }

                    }

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
            progressDialog = new ProgressDialog(ConsultaInfraccion.this);
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);

        }


        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);

            progressDialog = ProgressDialog.show(ConsultaInfraccion.this,"Espere","Consultando");

        }

        @Override
        protected void onPostExecute(Boolean result){
            progressDialog.dismiss();
            if(result){
                String mTitle = getString(R.string.title_section1);
                fragment_consultaResultado newFragment =fragment_consultaResultado.newInstance(matricula,multa,dateTimeConsulta,dateTimeEndTicket);
                if (newFragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, newFragment)
                            .addToBackStack(mTitle.toString())
                            .commit();
                }

            }
        }
    }


    private class WstHistorico extends AsyncTask<String, Integer, Boolean> {

        String url = Constantes.CONSULTAHISTORICO + "/" + tokenSession;
        private ProgressDialog progressDialog = null;

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

                if (errorResponse == 200) {
                    historicoResponse = respJSON.getJSONArray("historico");
                    System.out.println(historicoResponse);
                    int i;
                    for(i =0;i<historicoResponse.length();i++){
                        Integer unidTicket = historicoResponse.getJSONObject(i).getInt("idTicket");
                        String unaDateTime = historicoResponse.getJSONObject(i).getString("fechaHoraConsulta");
                        String unamatricula = historicoResponse.getJSONObject(i).getString("matricula");
                        Consulta unaConsulta = new Consulta(unidTicket,unaDateTime,unamatricula);

                        unalistaConsultas.add(unaConsulta);
                    }

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
            progressDialog = new ProgressDialog(ConsultaInfraccion.this);
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);

        }


        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);
            progressDialog = ProgressDialog.show(ConsultaInfraccion.this,"Espere","Consultando");

        }

        @Override
        protected void onPostExecute(Boolean result){
            progressDialog.dismiss();
            Fragment  fragment = null;
            fragment = new fragment_historico();

            if (fragment != null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(mTitle.toString())
                        .commit();

            }

        }
    }



}
