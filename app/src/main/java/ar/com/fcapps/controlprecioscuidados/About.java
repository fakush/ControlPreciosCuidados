package ar.com.fcapps.controlprecioscuidados;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void MandarMail (View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "fcreus.development@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CPC-App Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Escribi lo que quieras.");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "Mandar Mail"));
    }

}
