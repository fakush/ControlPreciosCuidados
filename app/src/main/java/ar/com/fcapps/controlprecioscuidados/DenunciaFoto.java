package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DenunciaFoto extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imageView;
    private TextView lugar,denuncia;
    private Bitmap imageBitmap;
    private File photoFile;
    private Uri photoURI, imageUri;
    private String mCurrentPhotoPath, filename;
    private CheckBox checkBox_Faltante, checkBox_Precio, checkBox_gondola;
    private String Faltante, Precio, Gondola, Tweet, DenunciaLenght;
    private SharedPreferences StoredSupermercado;
    public String Supermercado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_denuncia_foto);

        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

//        StoredSupermercado = PreferenceManager.getDefaultSharedPreferences(this);
//        String ValorSupermercado = StoredSupermercado.getString("ValorSupermercado", "");
//        if(ValorSupermercado.length()==0) {
//            //cagamos no había sharedpreferences!
//            Supermercado = "";
//        } else {
//            Supermercado = ValorSupermercado;
//            lugar.setText(Supermercado);
//        }

//        //Sacar Foto y ponerla en pantalla
//        filename = "tempfile.jpg";
//        imageUri = Uri.fromFile(new File(filename));

        // start default camera
        dispatchTakePictureIntent();
        imageView = findViewById(R.id.FotoDenuncia);

        MobileAds.initialize(this, "@string/ad_DenunciaFoto");
        AdView mAdView = findViewById(R.id.adView_Denuncia);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        lugar = findViewById(R.id.field_lugar);
        denuncia = findViewById(R.id.field_denuncia);

        Faltante = "";
        Precio = "";
        Gondola = "";
        DenunciaLenght = Faltante + Precio + Gondola;
    }

    public void btnOK (View view) {
        //Se fija si la denuncia esta en blanco
        if (lugar.getText().toString().length() > 0 && denuncia.getText().toString().length() > 0) {
            Toast.makeText(DenunciaFoto.this, "Preparando tu Denuncia.", Toast.LENGTH_LONG).show();
            //Guardar foto y pasar a la siguiente activity.
            Intent intent = new Intent(DenunciaFoto.this, MandarDenunciaFoto.class);
            intent.putExtra("imageUri", photoURI.toString());
            intent.putExtra("Donde", lugar.getText().toString());
            intent.putExtra("Denuncia", denuncia.getText().toString());
            intent.putExtra("Checks", DenunciaLenght);
            startActivity(intent);
            galleryAddPic();
        } else {
            Toast.makeText(DenunciaFoto.this, "Tenes que completar lugar y denuncia para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCancelar (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(DenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }

    public void dispatchTakePictureIntent() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CPC");
        if(dir.exists() && dir.isDirectory()) {
            // do something here
        } else {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CPC", "CPC_" + timeStamp + ".jpg");
        photoFile.getParentFile().mkdirs();
        //filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "tempfile.jpg";
        imageUri = Uri.fromFile(photoFile);

        // start default camera
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult (cameraIntent, REQUEST_TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Uri imageUri = data.getData();
            imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pegarTitulo();
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void pegarTitulo () {
        //Pegar Imagen
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, 1.0f);
        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth()/3, imageBitmap.getHeight()/3, false);
        Bitmap FotoDenuncia = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
        //Bitmap FotoDenuncia = ;
        // Arrancar acá nuevo Pegado de Imagenes.
        Resources r = getResources();
        Drawable d = new BitmapDrawable(r, FotoDenuncia);//converting bitmap to drawable
        Drawable[] layers = new Drawable[2];
        layers[0] = d;
        layers[1] = r.getDrawable(R.drawable.banner_denuncia_43_1080);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        int width = layerDrawable.getIntrinsicWidth();
        int height = layerDrawable.getIntrinsicHeight();
        //Bitmap Fotopegada = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap Fotopegada = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(Fotopegada);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);
        imageBitmap = Fotopegada;
        /* create a file to write bitmap data */
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CPC");
        if(dir.exists() && dir.isDirectory()) {
            // do something here
        } else {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CPC", "CPC_" + timeStamp + ".jpg");
        photoFile.getParentFile().mkdirs();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        photoURI = Uri.fromFile(photoFile);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void btnDonde (View view) {
        final Dialog dialog = new Dialog(DenunciaFoto.this);
        dialog.setTitle("Ingrese el Lugar");
        dialog.setContentView(R.layout.dialog_lugar);
        final EditText edittext = (EditText) dialog.findViewById(R.id.field_dialogLugar);
        if (lugar.getText().toString().length() > 0) {
          edittext.setText(lugar.getText().toString());
        };
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialogLugar_ok);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext.getText().toString().length() > 0) {
                    lugar.setText(edittext.getText().toString());
//                    StoredSupermercado = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                    SharedPreferences.Editor editorSupermercado = StoredSupermercado.edit();
//                    editorSupermercado.putString( "ValorSupermercado", edittext.getText().toString());
//                    editorSupermercado.apply();
                }
                dialog.cancel();
                //Reseteo el valor para el resto de la app
                //edittext.setText("");
            }
        });
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    public void btnDenuncia (View view) {
        final Dialog dialog = new Dialog(DenunciaFoto.this);
        dialog.setTitle("Ingrese su Denuncia");
        dialog.setContentView(R.layout.dialog_denuncia);
        final EditText edittext = (EditText) dialog.findViewById(R.id.field_dialogDenuncia);
        if (denuncia.getText().toString().length() >0) {
            edittext.setText(denuncia.getText().toString());
        };
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialogDenuncia_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext.getText().toString().length() > 0) {
                    denuncia.setText(edittext.getText().toString());
                }
                dialog.cancel();
            }
        });
        checkBox_Faltante = (CheckBox) dialog.findViewById(R.id.checkBox_Faltante);
        checkBox_Faltante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    DenunciaLenght = "el falantante del Articulo:";
                } else {
                    Faltante = "";
                }

            }
        });
        checkBox_Precio = (CheckBox) dialog.findViewById(R.id.checkBox_Precio);
        checkBox_Precio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    DenunciaLenght = "el precio incorrecto del Articulo:";
                } else {
                    Precio = "";
                }
            }
        });
        checkBox_gondola = (CheckBox) dialog.findViewById(R.id.checkBox_gondola);
        checkBox_gondola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    DenunciaLenght = "un articulo no señalizado o mal señalizado:";
                } else {
                    Gondola = "";
                }
            }
        });
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    @Override
    public void onBackPressed()
    {
        //Descartar y volver a Main
        Intent intent = new Intent(DenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }

}
