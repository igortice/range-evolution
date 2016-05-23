package br.com.igortice.rangeevolution;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CompararResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comparar_result);

        String[] mystringArray = getIntent().getStringArrayExtra("fotosSelecionadas");
        TextView txtData1 = (TextView) findViewById(R.id.txtData1);
        txtData1.setText(mystringArray[0]);
        TextView txtData2 = (TextView) findViewById(R.id.txtData2);
        txtData2.setText(mystringArray[1]);
    }

}
