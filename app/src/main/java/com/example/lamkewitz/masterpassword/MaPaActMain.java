package com.example.lamkewitz.masterpassword;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MaPaActMain extends ActionBarActivity {

    TextView Passwort;
    String s;

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

    public void onClickgenerate(final View cmd){

       /*String DK = PBKDF2(PRF, Password, Salt, c, dkLen);
*/

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
                ClipData clip = ClipData.newPlainText("password", Passwort.getText());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_LONG).show();


                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
