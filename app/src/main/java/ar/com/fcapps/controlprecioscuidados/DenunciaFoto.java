package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
    private Uri photoURI;
    private String currentPhotoPath;
    private CheckBox checkBox_Faltante, checkBox_Precio, checkBox_gondola;
    private String Faltante, Precio, Gondola, Tweet, DenunciaLenght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_denuncia_foto);

        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Sacar Foto y ponerla en pantalla
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
            //Guardar foto y pasar a la siguiente activity.
        Intent intent = new Intent(DenunciaFoto.this, MandarDenunciaFoto.class);
        intent.putExtra("imageUri", photoURI.toString());
        intent.putExtra("Donde", lugar.getText().toString());
        intent.putExtra("Denuncia", denuncia.getText().toString());
        intent.putExtra("Checks", DenunciaLenght);
        startActivity(intent);
        } else {
            Toast.makeText(DenunciaFoto.this, "Tenes que completar lugar y denuncia para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCancelar (View view) {
        //Descartar y volver a Main
        Intent intent = new Intent(DenunciaFoto.this, MainActivity.class);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            pegarTitulo();
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void pegarTitulo () {
        //Pegar Imagen
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, 1.0f);
        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 1080, 1440, false);
        Bitmap FotoDenuncia = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
        // Arrancar acá nuevo Pegado de Imagenes.
        Resources r = getResources();
        Drawable d = new BitmapDrawable(r, FotoDenuncia);//converting bitmap to drawable
        Drawable[] layers = new Drawable[2];
        layers[0] = d;
        layers[1] = r.getDrawable(R.drawable.banner_denuncia_43_1080);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        int width = layerDrawable.getIntrinsicWidth();
        int height = layerDrawable.getIntrinsicHeight();
        Bitmap Fotopegada = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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
        File f = new File(currentPhotoPath);
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
                    DenunciaLenght = "el Falantante de un Articulo.";
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
                    DenunciaLenght = "el precio incorrecto de un Articulo.";
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
                    DenunciaLenght = "un Articulo no señalizado o mal señalizado.";
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
