package ar.com.fcapps.controlprecioscuidados;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;

public class MandarDenunciaFoto extends AppCompatActivity {

    private File imgFile;
    private String Lugar,Denuncia,tweet;
    private ImageView imageView;
    private TextView textView;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_denuncia_foto);

        /*TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);*/

        Intent intent = getIntent();

        Bitmap imageUri = intent.getParcelableExtra("imageUri");
        Lugar = intent.getExtras().getString("Donde");
        Denuncia = intent.getExtras().getString("Denuncia");

        imageView = findViewById(R.id.FotoDenunciaFinal);
        imageView.setImageBitmap(imageUri);

        tweet = "Atención! En: "+Lugar+". Nuestro usuario ha encontrado que: "+Denuncia+". @PreciosCuidados @ControlPreciosCuidados";

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

    public void shareTweet (View view){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.setClassName("com.twitter.android", "com.twitter.android.PostActivity");
            intent.putExtra(Intent.EXTRA_TEXT, tweet);
            Uri uri = FileProvider.getUriForFile(MandarDenunciaFoto.this, BuildConfig.APPLICATION_ID + ".provider",imgFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(intent);

        } catch (final ActivityNotFoundException e) {
            Toast.makeText(MandarDenunciaFoto.this, "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void shareTweet (View view) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, tweet);
        Uri uri = FileProvider.getUriForFile(MandarDenunciaFoto.this, BuildConfig.APPLICATION_ID + ".provider",imgFile);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        final PackageManager pm = MandarDenunciaFoto.this.getApplicationContext().getPackageManager();
        final List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList) {
            if("com.twitter.android.composer.ComposerActivity".equals(app.activityInfo.name))
            {

                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                MandarDenunciaFoto.this.getApplicationContext().startActivity(shareIntent);
                break;
            }
        }
    }*/

    public void btnCancelar (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(MandarDenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }
}
