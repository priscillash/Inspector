package com.example.priscilla.inspectores;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    String user, pass;
    private static final String TAG_INI = "Iniciando Main Activity";
    private static final String TAG_DEST = "Finalizando Main Activity ";
    private static final String TAG ="Main Activity";
    private static final String KEY_INDEX = "Index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG_INI,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        //se define la estructura de datos que identifica al Usuario.
        final EditText etUser = (EditText)findViewById(R.id.User_ID);
        final EditText etPass = (EditText)findViewById(R.id.Pass);

        Button unpButton = (Button) findViewById(R.id.pButton);
        unpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se setean los parametros del usuario para validar
                user=etUser.getText().toString();
                pass=etPass.getText().toString();
                String dataUser[]={user,pass};
                //Se envia informacion del usuario para validar sus credenciales
                Intent openIntent=new Intent(MainActivity.this, verificacionLogin.class);
                openIntent.putExtra("dataUser", dataUser);
                startActivityForResult(openIntent,1);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            //Las credenciales del usuario son validas
            if(resultCode == RESULT_OK){
                Bundle bundExtra=data.getExtras();
                //String token=bundExtra.getString("tokenSession");
                String userLoged=bundExtra.getString("userLoged");
                //Se envia datos a la actividad de Consulta de Infracciones.
                Intent openIntent=new Intent(MainActivity.this, ConsultaInfraccion.class);
                //openIntent.putExtra("tokenSession", token);
                openIntent.putExtra("UserLoged", userLoged);
                startActivity(openIntent);
            }
            //Las credenciales del usuario no son validas
            if (resultCode == RESULT_CANCELED) {
                String errorLogin=getResources().getString(R.string.errorLoginMsg).toString();
                Toast.makeText(MainActivity.this,errorLogin, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG_DEST,"onDestroy() called");
    }

}
