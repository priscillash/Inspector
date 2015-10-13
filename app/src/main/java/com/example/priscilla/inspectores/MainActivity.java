package com.example.priscilla.inspectores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    String user, pass;
    private static final String TAG_INI = "Iniciando Main Activity";
    private static final String TAG_DEST = "Finalizando Main Activity ";
    private static final String TAG ="Main Activity";
    private static final String KEY_INDEX = "Index";
    public static final int MY_COLOR = Color.rgb(12,111,183);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG_INI,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        custom_button unpButton = new custom_button.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_forward))
                .withButtonColor(MY_COLOR)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .withButtonSize(130)
                .create();

        //se define la estructura de datos que identifica al Usuario.
        final EditText etUser = (EditText)findViewById(R.id.User_ID);
        final EditText etPass = (EditText)findViewById(R.id.Pass);

        unpButton.setOnClickListener(new OnClickListener() {
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
                String tokenSession=bundExtra.getString("token");
                String userLogged = bundExtra.getString("userLogged");
                //Se envia datos a la actividad de Consulta de Infracciones.
                Intent openIntent=new Intent(MainActivity.this, ConsultaInfraccion.class);
                openIntent.putExtra("UserLoged", userLogged);
                openIntent.putExtra("tokenSession",tokenSession);
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
        //Trae a pantalla el layout de inicio
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.finish();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG_DEST,"onDestroy() called");
    }


}
