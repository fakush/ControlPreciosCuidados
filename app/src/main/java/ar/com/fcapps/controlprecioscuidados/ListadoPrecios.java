package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ListadoPrecios extends Activity {

    ListView list;
    AdapterListaPrecios adapter;
    public ArrayList<Method_ListadoPrecios> precios_array = new ArrayList<Method_ListadoPrecios>();
    EditText editsearch;
    public String regionGuardada;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SharedPreferences AdCounter;
    private int CuantoVamos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listado_precios);
        setListData();
        list = findViewById(R.id.VistaListaPrecios);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        MobileAds.initialize(this, "@string/ad_ListaPrecios");
        mAdView = findViewById(R.id.adViewListado);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("@string/ad_Popup");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        adapter = new AdapterListaPrecios(this, precios_array);
        list.setAdapter(adapter);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(1);

        SharedPreferences ValorRegion = PreferenceManager.getDefaultSharedPreferences(this);
        regionGuardada = ValorRegion.getString("ValorRegion", "");

        editsearch = (EditText) findViewById(R.id.SearchBar);

        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        //Ad2Counter();
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

    /*public void Ad2Counter() {
        AdCounter = PreferenceManager.getDefaultSharedPreferences(this);
        CuantoVamos = AdCounter.getInt("Contador", 0);
        if(AdCounter == null) {
            //cagamos no había sharedpreferences!
            CuantoVamos = 1;
            SharedPreferences.Editor editorCounter = AdCounter.edit();
            editorCounter.putInt( "Contador", CuantoVamos);
            editorCounter.apply();
        } else {
            CuantoVamos++;
            SharedPreferences.Editor editorCounter = AdCounter.edit();
            editorCounter.putInt( "Contador", CuantoVamos);
            editorCounter.apply();
        }
    }

    public void DaParaUnAviso(){
        if (CuantoVamos > 3) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        finish();
                    }
                });
                SharedPreferences.Editor editorCounter = AdCounter.edit();
                editorCounter.putInt( "Contador", 0);
                editorCounter.apply();
            } else {

            }
        }
    }

    @Override
    public void onBackPressed() {
        DaParaUnAviso();
        super.onBackPressed();
    }*/
}
