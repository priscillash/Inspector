package com.example.priscilla.inspectores;

import java.math.BigInteger;
import java.security.SecureRandom;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class verificacionLogin extends Activity{
    String user, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String dataUser[];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla2);

        Bundle bundle = getIntent().getExtras();
        dataUser = bundle.getStringArray("dataUser");

        //se guarda el contenido de los editText.
        user = dataUser[0];
        pass = dataUser[1];

        //Aca hay que hacer una consulta al servidor para obtener la validacion del usuario y el token.

        if (user.equals("USER") && pass.equals("PASS")) {

            //Genero Token para mantener la sesión.
            String token;
            SessionIdentifierGenerator sessionId = new SessionIdentifierGenerator();
            token = sessionId.nextSessionId();
            Toast.makeText(verificacionLogin.this, token, Toast.LENGTH_LONG).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("tokenSession", token);
            returnIntent.putExtra("userLoged", user);
            setResult(RESULT_OK, returnIntent);
            finish();

        } else {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();

        }
        }

    public final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();
        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }

}

