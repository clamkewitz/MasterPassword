package com.example.lamkewitz.masterpassword;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class MaPaActMain extends ActionBarActivity {

    String hashed;
    TextView passwort;
    EditText masterpasswort =null;
    String mapa1 =null;
    String mapa2=null;
    String kenn=null;
    String vers=null;
    EditText masterpasswort2 = null;
    EditText version = null;
    Spinner dienst;
    EditText kennung = null;
    String zeichenkette = null;
    String salt = null;
    int iteration = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_mapa_act);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_mapa_act, menu);
        return true;
    }

    public void onClickgenerate(final View cmd) {

        passwort = (TextView) findViewById((R.id.Passwort));
        kennung = (EditText) findViewById(R.id.kennung);
        dienst = (Spinner) findViewById(R.id.spin);
        version = (EditText) findViewById(R.id.version);
        masterpasswort = (EditText) findViewById(R.id.mapa1);
        masterpasswort2 = (EditText) findViewById(R.id.mapa2);

        mapa1=masterpasswort.getText().toString();
        mapa2=masterpasswort2.getText().toString();
        kenn=kennung.getText().toString();
        vers=version.getText().toString();


    if (kenn.equals(null) || kenn.equals("") || mapa1.equals(null) || mapa1.equals("") || mapa2.equals(null) || mapa2.equals("") ||vers.equals(null) || vers.equals("")) {


        Toast.makeText(this, "FEHLER: Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
    } else {

        if(mapa1.equals(mapa2)) {

            zeichenkette = masterpasswort.getText().toString() + version.getText().toString()
                    + dienst.getSelectedItem() + kennung.getText().toString();

            Toast.makeText(this, "Zeichenkette erstellt!", Toast.LENGTH_LONG).show();

            hashing(zeichenkette, salt);
        }
        else{
            Toast.makeText(this, "FEHLER: Überprüfen sie Ihre Eingaben!!", Toast.LENGTH_LONG).show();
        }
        }

    }

    public void salting() {

    /*Bildet einen zufälligen SALT
    char[] chars =
            "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!§$%&/()=?".toCharArray();
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for(int i = 0;i<9;i++)
            {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
        salt=sb.toString();
    */


    //Salt festlegen
        salt = masterpasswort2.getText().toString() + "sadfahjFDAFaGH" + version.getText().toString() +"123456"+"§%$%&§"+kennung.getText().toString();
        Toast.makeText(this, "Salt wurde erstellt: "+salt, Toast.LENGTH_LONG).show();
}


String hashing (String zeichenkette, String Salt){
String generatedhash=null;

    try {
    //ruft die Methode für die Erstellung eines Salt auf.
    salting();

    //Zeichenkette zum Char umwandeln für den PBKDF2-Algorithmus
    char[] charpass = zeichenkette.toCharArray();

    // Bytes vom Salt erhalten
    byte[] saltBytes = salt.getBytes();

    // Schluessellaenge in Bits festlegen
    int bitlaenge = 256;

    PBEKeySpec spec = new PBEKeySpec(charpass, saltBytes, iteration, bitlaenge);

    SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hashedPassword = key.generateSecret(spec).getEncoded();
    generatedhash = String.format("%x", new BigInteger(hashedPassword));

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (InvalidKeySpecException e) {
        e.printStackTrace();
    }
    passwort.setText(generatedhash);
    return generatedhash;
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
        switch(item.getItemId()){
            case R.id.actExit:
            {
                Toast.makeText(this, "Beendet", Toast.LENGTH_LONG).show();
                finish();
                break;
            }

            case R.id.actCopy:
            {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);

                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("password", passwort.getText());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_LONG).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
