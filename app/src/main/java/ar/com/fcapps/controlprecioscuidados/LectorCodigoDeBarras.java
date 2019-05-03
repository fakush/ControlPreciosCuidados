package ar.com.fcapps.controlprecioscuidados;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LectorCodigoDeBarras extends Activity {

    private Barcode barcodeResult;
    private String ValorFiltro;
    private TextView result;
    private AdView mAdView;

    //Heredados Listado
    ListView list;
    AdapterListaBarcode adapter;
    public ArrayList<Method_ListadoPrecios> precios_array = new ArrayList<Method_ListadoPrecios>();
    public String regionGuardada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lector_codigo_de_barras);
        setListData();
        list = findViewById(R.id.ListaBarcode);

        MobileAds.initialize(this, "@string/ad_Scanner");
        mAdView = findViewById(R.id.adViewLector);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        adapter = new AdapterListaBarcode(this, precios_array);
        list.setAdapter(adapter);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(1);

        SharedPreferences ValorRegion = PreferenceManager.getDefaultSharedPreferences(this);
        regionGuardada = ValorRegion.getString("ValorRegion", "");

        result = findViewById(R.id.textoCodigo);
        startScan();

    }

    public void OtroScan (View view){
        startScan();
    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(LectorCodigoDeBarras.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withOnly2DScanning()
                .withText("Buscando...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        ValorFiltro = barcode.rawValue;
                        result.setText(barcode.rawValue);
                        adapter.filterEAN(ValorFiltro);
                        CheckArrayLength();
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    // ListView
    private void setListData() {
        String[] array = getResources().getStringArray(R.array.ListaPreciosMayo);
        for (int i = 0; i < array.length; i++) {
            final Method_ListadoPrecios sched = new Method_ListadoPrecios();
            String[] Separado = array[i].split("/;/");
            sched.setCategoria(Separado[0]);
            sched.setProducto(Separado[1]);
            sched.setProveedor(Separado[2]);
            sched.setEAN(Separado[3]);
            sched.setSinTACC(Separado[4]);
            sched.setAMBA(Separado[5]);
            sched.setBuenosAires(Separado[6]);
            sched.setCentroCuyo(Separado[7]);
            sched.setNoresteNoroeste(Separado[8]);
            sched.setPatagonia(Separado[9]);
            sched.setFoto(Separado[10]);
            precios_array.add(sched);
        }
    }

    private void CheckArrayLength () {
        if (precios_array.isEmpty()) {
            Toast.makeText(LectorCodigoDeBarras.this, "Este producto no figura en los listados entregados por el gobierno.", Toast.LENGTH_LONG).show();
        }
    }

    private void filtro() {
        String[] androidStrings = getResources().getStringArray(R.array.ListaPreciosMayo);
        if (Arrays.asList(androidStrings).contains(ValorFiltro)) {
            int i = Arrays.asList(androidStrings).indexOf(ValorFiltro);
            // found a match to "software"
        } else {
            Toast.makeText(LectorCodigoDeBarras.this, "Este producto no aparece en nuestros listados.", Toast.LENGTH_SHORT).show();


        }
    }
}

