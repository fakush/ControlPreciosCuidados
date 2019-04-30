package ar.com.fcapps.controlprecioscuidados;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int backButtonCount = 0;
    private AdView mAdView;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private CheckBox checkBox_AMBA, checkBox_BuenosAires, checkBox_CentroCuyo, checkBox_NoresteyNoroeste,checkBox_Patagonia;
    public String region;
    private SharedPreferences StoredRegion;
    public static Context contextOfApplication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();

        MobileAds.initialize(this, "@string/ad_main");
        mAdView = findViewById(R.id.adView_Main);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Si es la primera vez que se ejecuta la app, muestra el dialogo Region.
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //Abre el Alert
            region = "0";
            setRegion();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();

        StoredRegion = PreferenceManager.getDefaultSharedPreferences(this);
        String Valorregion = StoredRegion.getString("ValorRegion", "");
        if(Valorregion.length()==0) {
            //cagamos no había sharedpreferences!
            region = "1";
        } else {
            region = Valorregion;
        }

        contextOfApplication = getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void ListadoPrecios (View view) {
        Intent intent = new Intent(MainActivity.this, ListadoPrecios.class);
        startActivity(intent);
    }

    public void LectoCodigoBarras (View view) {
        Intent intent = new Intent(MainActivity.this, ListadoPrecios.class);
        startActivity(intent);
    }

    public void DenunciaFoto (View view) {
        Intent intent = new Intent(MainActivity.this, DenunciaFoto.class);
        startActivity(intent);
    }

    public void DenunciaTexto (View view) {
        Intent intent = new Intent(MainActivity.this, DenunciaTexto.class);
        startActivity(intent);
    }

    public void setRegion () {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Ingrese su Region");
        dialog.setContentView(R.layout.dialog_set_region);
        final EditText edittext = (EditText) dialog.findViewById(R.id.field_dialogLugar);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialogRegionOk);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (region.equals("0")) {
                    Toast.makeText(MainActivity.this, "Tenes que seleccionar una región para continuar.", Toast.LENGTH_LONG).show();
                } else {
                    dialog.cancel();
                }

                //Reseteo el valor para el resto de la app
                //edittext.setText("");
            }
        });
        checkBox_AMBA = (CheckBox) dialog.findViewById(R.id.checkBox_AMBA);
        checkBox_AMBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    region = "1";
                    StoredRegion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editorRegion = StoredRegion.edit();
                    editorRegion.putString( "ValorRegion", region);
                    editorRegion.apply();
                } else {
                    region = "";
                }

            }
        });
        checkBox_BuenosAires = (CheckBox) dialog.findViewById(R.id.checkBox_BuenosAires);
        checkBox_BuenosAires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    region = "2";
                    StoredRegion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editorRegion = StoredRegion.edit();
                    editorRegion.putString( "ValorRegion", region);
                    editorRegion.apply();
                } else {
                    region = "";
                }

            }
        });
        checkBox_CentroCuyo = (CheckBox) dialog.findViewById(R.id.checkBox_CentroCuyo);
        checkBox_CentroCuyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    region = "3";
                    StoredRegion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editorRegion = StoredRegion.edit();
                    editorRegion.putString( "ValorRegion", region);
                    editorRegion.apply();
                } else {
                    region = "";
                }

            }
        });
        checkBox_NoresteyNoroeste = (CheckBox) dialog.findViewById(R.id.checkBox_NoresteyNoroeste);
        checkBox_NoresteyNoroeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    region = "4";
                    StoredRegion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editorRegion = StoredRegion.edit();
                    editorRegion.putString( "ValorRegion", region);
                    editorRegion.apply();
                } else {
                    region = "";
                }

            }
        });
        checkBox_Patagonia = (CheckBox) dialog.findViewById(R.id.checkBox_Patagonia);
        checkBox_Patagonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    region = "5";
                    StoredRegion = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editorRegion = StoredRegion.edit();
                    editorRegion.putString( "ValorRegion", region);
                    editorRegion.apply();
                } else {
                    region = "";
                }

            }
        });
        dialog.show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Intent about = new Intent(MainActivity.this, About.class);
                startActivity(about);
                return true;

                case R.id.compartir:
                    Intent intent = new Intent(Intent.ACTION_SEND);

                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Control Precios Cuidados - Bajate la App");
                    intent.putExtra(Intent.EXTRA_TEXT, "Busca en el Play Store de Android la App 'Control Precios Cuidados' Entre todos nos cuidamos!!!\n https://play.google.com/store/apps/details?id=ar.com.fcapps.controlprecioscuidados");

                    startActivity(Intent.createChooser(intent, "Compratir Nuestra App"));
                return true;

            case R.id.region:
                setRegion();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Presione ora vez para salir de la aplicación.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    @Override    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //You did not accept the request can not use the functionality.
                }
                break;
        }
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}
