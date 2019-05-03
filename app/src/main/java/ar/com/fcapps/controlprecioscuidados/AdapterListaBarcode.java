package ar.com.fcapps.controlprecioscuidados;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListaBarcode extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Method_ListadoPrecios> listaprecios = null;
    private ArrayList<Method_ListadoPrecios> arraylist;

    public AdapterListaBarcode (Context context, List<Method_ListadoPrecios> listaprecios) {
        mContext = context;
        this.listaprecios = listaprecios;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Method_ListadoPrecios>();
        this.arraylist.addAll(listaprecios);
    }

    public class ViewHolder {

        TextView Categoria;
        TextView Producto;
        TextView Proveedor;
        TextView EAN;
        TextView SinTACC;
        TextView AMBA;
        TextView BuenosAires;
        TextView CentroCuyo;
        TextView NoresteNoroeste;
        TextView Patagonia;
        ImageView Foto;
        TextView Region;
        TextView Precio;

    }

    @Override
    public int getCount() {
        return listaprecios.size();
    }

    @Override
    public Method_ListadoPrecios getItem(int position) {
        return listaprecios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_listado_codigo_de_barras, null);
            // Locate the TextViews in listview_item.xml
            holder.Categoria = (TextView) view.findViewById(R.id.LV_Categoria_Barcode);
            holder.Proveedor = (TextView) view.findViewById(R.id.LV_Proveedor_Barcode);
            holder.Producto = (TextView) view.findViewById(R.id.LV_Producto_Barcode);
//            holder.AMBA = (TextView) view.findViewById(R.id.Precio_AMBA);
//            holder.BuenosAires = (TextView) view.findViewById(R.id.Precio_BuenosAires);
//            holder.CentroCuyo = (TextView) view.findViewById(R.id.Precio_CentroyCuyo);
//            holder.NoresteNoroeste = (TextView) view.findViewById(R.id.Precio_NoresteyNoroeste);
//            holder.Patagonia = (TextView) view.findViewById(R.id.Precio_Patagonia);
            //holder.EAN = (TextView) view.findViewById(R.id.LV_EAN);
            //holder.SinTACC = (TextView) view.findViewById(R.id.LV_SinTACC);
            holder.Region = (TextView) view.findViewById(R.id.Tit_Region_Barcode);
            holder.Precio = (TextView) view.findViewById(R.id.Tit_Precio_Barcode);

            // Locate the ImageView in listview_item.xml
            holder.Foto = (ImageView) view.findViewById(R.id.FotoProductoBarcode);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.Categoria.setText(listaprecios.get(position).getCategoria());
        holder.Proveedor.setText(listaprecios.get(position).getProveedor());
        holder.Producto.setText(listaprecios.get(position).getProducto());
        String FotoSelector = listaprecios.get(position).getFoto();
        if (FotoSelector.equals("icon_pe")) {
            holder.Foto.setImageResource(R.drawable.icon_pe);
        }
        else {
            holder.Foto.setImageResource(R.drawable.icon_pc);
        }

        SharedPreferences ValorRegion = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        String regionGuardada = ValorRegion.getString("ValorRegion", "");
        switch (regionGuardada) {
            case "1":
                holder.Region.setText("Precio AMBA");
                holder.Precio.setText(listaprecios.get(position).getAMBA());
                break;
            case "2":
                holder.Region.setText("Precio Buenos Aires");
                holder.Precio.setText(listaprecios.get(position).getBuenosAires());
                break;
            case "3":
                holder.Region.setText("Precio Centro y Cuyo");
                holder.Precio.setText(listaprecios.get(position).getCentroCuyo());
                break;
            case "4":
                holder.Region.setText("Precio Noreste y Noroeste");
                holder.Precio.setText(listaprecios.get(position).getNoresteNoroeste());
                break;
            case "5":
                holder.Region.setText("Precio Patagonia");
                holder.Precio.setText(listaprecios.get(position).getPatagonia());
                break;
        }
//        holder.AMBA.setText(listaprecios.get(position).getAMBA());
//        holder.BuenosAires.setText(listaprecios.get(position).getBuenosAires());
//        holder.CentroCuyo.setText(listaprecios.get(position).getCentroCuyo());
//        holder.NoresteNoroeste.setText(listaprecios.get(position).getNoresteNoroeste());
//        holder.Patagonia.setText(listaprecios.get(position).getPatagonia());

        // Set the results into ImageView
        /*String ContImagen = listaprecios.get(position).getFoto().toLowerCase();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://bsaspatrimonio.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("bares-thumbs/" + ContImagen + "_1.png");
        GlideApp.with(mContext)
                .load(pathReference)
                .error(R.drawable.loading_thumb)
                .thumbnail(GlideApp.with(mContext).load(R.drawable.cargado_thumb))
                .diskCacheStrategy(DiskCacheStrategy.ALL) //using to load into cache then second time it will load fast.
                .override(75, 75)
                .into(holder.Foto);
        holder.Foto.setText(listaprecios.get(position).getFoto());*/

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                /*Intent intent = new Intent(mContext, FichaBar.class);
                // Pass all data rank
                intent.putExtra("Nombre",(listabares.get(position).getNombre()));
                intent.putExtra("Bar_Nombre", (listabares.get(position).getNombre()));
                intent.putExtra("Bar_Direccion", (listabares.get(position).getDireccion()));
                intent.putExtra("Bar_Barrio", (listabares.get(position).getBarrio()));
                intent.putExtra("Bar_Telefono", (listabares.get(position).getTelefono()));
                intent.putExtra("Bar_Descripcion", (listabares.get(position).getDescripcion()));
                intent.putExtra("Bar_Foto", (listabares.get(position).getFoto()));
                intent.putExtra("Bar_Coordenadas", (listabares.get(position).getCoordenadas()));
                // Start SingleItemView Class
                mContext.startActivity(intent);*/
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listaprecios.clear();
        if (charText.length() == 0) {
            listaprecios.addAll(arraylist);
        } else {
            for (Method_ListadoPrecios wp : arraylist) {
                if (wp.getProducto().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    listaprecios.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterEAN(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listaprecios.clear();
        if (charText.length() == 0) {
            listaprecios.addAll(arraylist);
        } else {
            for (Method_ListadoPrecios wp : arraylist) {
                if (wp.getEAN().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    listaprecios.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    Context applicationContext = MainActivity.getContextOfApplication();



}