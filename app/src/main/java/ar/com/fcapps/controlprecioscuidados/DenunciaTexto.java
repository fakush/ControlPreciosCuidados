package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class DenunciaTexto extends Activity {

    private EditText Lugar, Denuncia, OtraDenuncia;
    private CheckBox checkBox_FaltanteTexto, checkBox_PrecioTexto, checkBox_gondolaTexto;
    private String LugarText, DenunciaText, OtradenunciaText;
    private String Faltante, Precio, Gondola, Tweet, DenunciaLenght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia_texto);

        MobileAds.initialize(this, "@string/ad_DenunciaTexto");
        AdView mAdView = findViewById(R.id.adView_DenunciaTexto);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Lugar = (EditText) findViewById(R.id.field_LugarTexto);
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

        Tweet = "Control Precios Cuidados: Atención! En "+LugarText+". Nuestro usuario ha encontrado "+ Faltante + Precio + Gondola + " Articulo (O Denuncia:) " + DenunciaText + OtradenunciaText + ". @PreciosCuidados @ControlPreciosCuidados";

    }

    public void checkFaltante() {
        checkBox_FaltanteTexto = (CheckBox) findViewById(R.id.checkBox_FaltanteTexto);
        checkBox_FaltanteTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Faltante = "el Falantante de un Articulo.";
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
                    Precio = "el precio incorrecto de un Articulo.";
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
                    Gondola = "un Articulo no señalizado o mal señalizado.";
                } else {
                    Gondola = "";
                }
            }
        });
    }

    public void btnMandarTweet (View view) {
        //Se fija si la denuncia esta en blanco
        if (Lugar.getText().toString().length() > 0 && Denuncia.length() > 0) {
            //MandarTweet
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

    public void btnCancelarTweet (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(DenunciaTexto.this, MainActivity.class);
        startActivity(intent);
    }
}