package br.com.igortice.rangeevolution;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompararResultActivity extends AppCompatActivity {
    TextView txtData1, txtData2;
    ImageView imgEsq, imgDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comparar_result);

        String[] mystringArray = getIntent().getStringArrayExtra("fotosSelecionadas");
        txtData1 = (TextView) findViewById(R.id.txtData1);
        txtData2 = (TextView) findViewById(R.id.txtData2);
        imgEsq = (ImageView) findViewById(R.id.imgEsq);
        imgDir = (ImageView) findViewById(R.id.imgDir);


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        try {
            Date date1 = format.parse(mystringArray[0]);
            Date date2 = format.parse(mystringArray[1]);
            String img1 = null, img2 = null;
            if (date1.compareTo(date2) < 0) {
                img1 = mystringArray[0];
                img2 = mystringArray[1];
            } else {
                img1 = mystringArray[1];
                img2 = mystringArray[0];
            }
            txtData1.setText(img1);
            String pathImg1 = Environment.getExternalStorageDirectory() + "/" + FilesUntil.getFolderPath(mystringArray[2]) + "/" + FilesUntil.dateHumanToFormat(img1) + ".jpg";
            Bitmap bitmapImage1 = BitmapFactory.decodeFile(pathImg1);
            imgEsq.setImageBitmap(bitmapImage1);

            txtData2.setText(img2);
            String pathImg2 = Environment.getExternalStorageDirectory() + "/" + FilesUntil.getFolderPath(mystringArray[2]) + "/" + FilesUntil.dateHumanToFormat(img2) + ".jpg";
            Bitmap bitmapImage2 = BitmapFactory.decodeFile(pathImg2);
            imgDir.setImageBitmap(bitmapImage2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
