package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class DenunciaTexto extends Activity {

    private EditText Lugar, Denuncia, OtraDenuncia;
    private CheckBox checkBox_FaltanteTexto, checkBox_PrecioTexto, checkBox_gondolaTexto;
    private String LugarText, DenunciaText, OtradenunciaText;
    private String Faltante, Precio, Gondola, Tweet, Texto, DenunciaLenght;
    private SharedPreferences StoredSupermercado;
    public String Supermercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_denuncia_texto);

        MobileAds.initialize(this, "@string/ad_DenunciaTexto");
        AdView mAdView = findViewById(R.id.adView_DenunciaTexto);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        StoredSupermercado = PreferenceManager.getDefaultSharedPreferences(this);
//        String ValorSupermercado = StoredSupermercado.getString("ValorSupermercado", "");
//        if(ValorSupermercado.length()==0) {
//            //cagamos no había sharedpreferences!
//            Supermercado = "";
//        } else {
//            Supermercado = ValorSupermercado;
//        }

        Lugar = (EditText) findViewById(R.id.field_LugarTexto);
        Lugar.setText(Supermercado);
        LugarText = Lugar.getText().toString();
        Denuncia = (EditText) findViewById(R.id.field_DenunciaTexto);
        DenunciaText = Denuncia.getText().toString();
        OtraDenuncia = (EditText) findViewById(R.id.field_OtraDenunciaTexto);
        OtradenunciaText = OtraDenuncia.getText().toString();

        Faltante = "";
        Precio = "";
        Gondola = "";
        DenunciaLenght = Faltante + Precio + Gondola + DenunciaText + OtradenunciaText;

        checkFaltante();
        checkPrecio();
        checkGondola();

        //Tweet = "Control Precios Cuidados: Atención! En "+Lugar.getText().toString()+". Nuestro usuario ha encontrado "+ Faltante + Precio + Gondola + " Articulo (O Denuncia:) " + Denuncia.getText().toString() + OtraDenuncia.getText().toString() + ". @PreciosCuidados @ControlPreciosCuidados";
        Texto = "#ControlPreciosCuidados\n" +
                "Informa que el producto "+ Faltante + Precio + Gondola + Denuncia.getText().toString() + OtraDenuncia.getText().toString() +" se encuentra en incumplimiento del Programa Precios Cuidados y Precios Esenciales establecidos en el "+Lugar.getText().toString()+". \n" +
                "\n" +
                "#EntreTodosNosCuidamos #PreciosCuidados #PreciosEsenciales #Supermercados #Compras #Programas #Denuncias #DefensaConsumidor";

    }

    public void checkFaltante() {
        checkBox_FaltanteTexto = (CheckBox) findViewById(R.id.checkBox_FaltanteTexto);
        checkBox_FaltanteTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Faltante = "el falantante del Articulo:";
                } else {
                    Faltante = "";
                }

            }
        });
    }

    public void checkPrecio() {
        checkBox_PrecioTexto = (CheckBox) findViewById(R.id.checkBox_PrecioTexto);
        checkBox_PrecioTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Precio = "el precio incorrecto del Articulo: ";
                } else {
                    Precio = "";
                }
            }
        });
    }

    public void checkGondola() {
        checkBox_gondolaTexto = (CheckBox) findViewById(R.id.checkBox_gondolaTexto);
        checkBox_gondolaTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Gondola = "un articulo no señalizado o mal señalizado:";
                } else {
                    Gondola = "";
                }
            }
        });
    }

//    public void MandarMail (View view){
//        if (Lugar.getText().toString().length() > 0 && Denuncia.length() > 0) {
//            Toast.makeText(DenunciaTexto.this, "Redireccionando al Mail", Toast.LENGTH_SHORT).show();
//            try {
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:scdydc@minproduccion.gov.ar"));
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Denuncia Precios Cuidados");
//                intent.putExtra(Intent.EXTRA_TEXT, Tweet);
//                startActivity(intent);
//
//            } catch (final ActivityNotFoundException e) {
//                Toast.makeText(DenunciaTexto.this, "Ups, No detectamos un cliente de correo.", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(DenunciaTexto.this, "Tenes que completar lugar y denuncia para continuar.", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void btnMandarTweet (View view) {
        //Se fija si la denuncia esta en blanco
        if (Lugar.getText().toString().length() > 0 && Denuncia.length() > 0) {
            //MandarTweet
            Tweet = "Control Precios Cuidados: Atención! En "+Lugar.getText().toString()+". Nuestro usuario ha encontrado "+ Faltante + Precio + Gondola + " Articulo (O Denuncia:) " + Denuncia.getText().toString() + OtraDenuncia.getText().toString() + ". @PreciosCuidados @ControlPreciosCuidados";
            Toast.makeText(DenunciaTexto.this, "Redireccionando a Tweeter", Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Tweet);
                intent.setType("text/plain");
                intent.setPackage("com.twitter.android");
                startActivity(intent);
            } catch (final ActivityNotFoundException e) {
                Toast.makeText(DenunciaTexto.this, "Ups, Parece que no tenes twitter Instalado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DenunciaTexto.this, "Tenes que completar lugar y denuncia para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onShareClick(View v) {
        //Guarda SharedPrederences
//        StoredSupermercado = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        SharedPreferences.Editor editorSupermercado = StoredSupermercado.edit();
//        editorSupermercado.putString( "ValorSupermercado", LugarText);
//        editorSupermercado.apply();
        //Se fija si la denuncia esta en blanco
        if (Lugar.getText().toString().length() > 0 && Denuncia.length() > 0) {
            Texto = "#ControlPreciosCuidados\n" +
                    "Informa "+ Faltante + Precio + Gondola + Denuncia.getText().toString() + OtraDenuncia.getText().toString() +". Y se encuentra en incumplimiento del Programa Precios Cuidados y Precios Esenciales. En el establecimiento: "+Lugar.getText().toString()+". \n" +
                    "\n" +
                    "#EntreTodosNosCuidamos #PreciosCuidados #PreciosEsenciales #Supermercados #Compras #Programas #Denuncias #DefensaConsumidor";
            Resources resources = getResources();

            Intent emailIntent = new Intent();
            emailIntent.setAction(Intent.ACTION_SEND);
            // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
            emailIntent.putExtra(Intent.EXTRA_TEXT, Texto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Denuncia Precios Cuidados");
            emailIntent.setType("message/rfc822");

            PackageManager pm = getPackageManager();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");


            Intent openInChooser = Intent.createChooser(emailIntent, "Mandar denuncia por:");

            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
            List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
            for (int i = 0; i < resInfo.size(); i++) {
                // Extract the label, append it, and repackage it in a LabeledIntent
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;
                if(packageName.contains("android.email")) {
                    emailIntent.setPackage(packageName);
                } else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    if(packageName.contains("twitter")) {
                        intent.putExtra(Intent.EXTRA_TEXT, Texto);
                    } else if(packageName.contains("facebook")) {
                        // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                        // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                        // will show the <meta content ="..."> text from that page with our link in Facebook.
                        intent.putExtra(Intent.EXTRA_TEXT, Texto);
                    } else if(packageName.contains("mms")) {
                        intent.putExtra(Intent.EXTRA_TEXT, Texto);
                    } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                        intent.putExtra(Intent.EXTRA_TEXT, Texto);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Denuncia Precios Cuidados");
                        intent.setType("message/rfc822");
                    }

                    intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                }

            }

            // convert intentList to array
            LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

            openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
            startActivity(openInChooser);
        } else {
        Toast.makeText(DenunciaTexto.this, "Tenes que completar lugar y denuncia para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCancelarTweet (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(DenunciaTexto.this, MainActivity.class);
        startActivity(intent);
    }
}