package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;

public class MandarDenunciaFoto extends Activity {

    private File imgFile;
    private String Lugar,Denuncia,tweet, tweet2, imageUri, Checks;
    private ImageView imageView;
    private TextView textView;
    private AdView mAdView;
    private Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mandar_denuncia_foto);

        /*TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);*/

        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = getIntent();

        imageUri = intent.getExtras().getString("imageUri");
        Lugar = intent.getExtras().getString("Donde");
        Denuncia = intent.getExtras().getString("Denuncia");
        Checks = intent.getExtras().getString("Checks");

        imgUri = Uri.parse(imageUri);
        imgFile = new File(imgUri.getPath());
        imageView = findViewById(R.id.FotoDenunciaFinal);
        imageView.setImageURI(imgUri);

        tweet = "Control Precios Cuidados: Atención! En "+ Lugar +". Nuestro usuario ha encontrado "+ Checks + " Articulo Denunciado: " + Denuncia + ". @PreciosCuidados @ControlPreciosCuidados";
        tweet2 = "*Control Precios Cuidados:* Atención! En "+ Lugar +". Nuestro usuario ha encontrado "+ Checks + " *Articulo Denunciado:* " + Denuncia + ". @PreciosCuidados @ControlPreciosCuidados";

        //armarElTweet();
        //imageView = findViewById(R.id.FotoDenunciaFinal);
        textView = findViewById(R.id.TextoDenunciaFinal);
        //Bitmap myBitmap = BitmapFactory.decodeFile(imageUri);
        //imageView.setImageBitmap(myBitmap);
        textView.setText(tweet);

        MobileAds.initialize(this, "@string/ad_MandarDenunciaFoto");
        mAdView = findViewById(R.id.adView_MandarDenuncia);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /*private void armarElTweet(){
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text(tweet)//any sharing text here
                .image(Uri.fromFile(imgFile));//sharing image uri
        builder.show();
    }*/

    public void MandarMail (View view){
        Toast.makeText(MandarDenunciaFoto.this, "Redireccionando al Mail", Toast.LENGTH_SHORT).show();
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:scdydc@minproduccion.gov.ar"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Denuncia Precios Cuidados");
            intent.putExtra(Intent.EXTRA_TEXT, tweet);
            intent.putExtra(Intent.EXTRA_STREAM, imgUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        } catch (final ActivityNotFoundException e) {
            Toast.makeText(MandarDenunciaFoto.this, "Ups, No detectamos un cliente de correo.", Toast.LENGTH_SHORT).show();
        }
    }


    public void MandarTweet (View view){
        Toast.makeText(MandarDenunciaFoto.this, "Redireccionando a Twitter", Toast.LENGTH_SHORT).show();
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, tweet);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, imgUri);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setPackage("com.twitter.android");
            startActivity(intent);

        } catch (final ActivityNotFoundException e) {
            Toast.makeText(MandarDenunciaFoto.this, "Ups, Parece que no tenes twitter Instalado.", Toast.LENGTH_SHORT).show();
        }
        }

    public void btnCancelar (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(MandarDenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        //Descartar y volver a Main
        Intent intent = new Intent(MandarDenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }
}
