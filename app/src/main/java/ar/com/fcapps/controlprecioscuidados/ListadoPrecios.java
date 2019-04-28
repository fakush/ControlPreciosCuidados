package ar.com.fcapps.controlprecioscuidados;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ListadoPrecios extends AppCompatActivity {

    ListView list;
    AdapterListaPrecios adapter;
    public ArrayList<Method_ListadoPrecios> bares_array = new ArrayList<Method_ListadoPrecios>();
    EditText editsearch;

//    String[] Nombre;
//    String[] Direccion;
//    String[] Barrio;
//    String[] Telefono;
//    String[] Descripcion;
//    String[] Foto;
//    String[] Coordenadas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_listado_precios);
        setListData();
        list = findViewById(R.id.VistaListaPrecios);

        adapter = new AdapterListaPrecios(this, bares_array);
        list.setAdapter(adapter);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(1);


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

    }

    // ListView
    private void setListData() {
        String[] array = getResources().getStringArray(R.array.ListaPreciosMayo);
        for (int i = 0; i < array.length; i++) {
            final Method_ListadoPrecios sched = new Method_ListadoPrecios();
            String[] Separado = array[i].split("; ");
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
            bares_array.add(sched);
        }
    }
}