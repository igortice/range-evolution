package br.com.igortice.rangeevolution;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sromku.simple.storage.helpers.OrderType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainCompararActivity extends AppCompatActivity {
    Spinner spinnerCategoria;
    Spinner spinnerFoto1, spinnerFoto2;
    LinearLayout layoutFoto1, layoutFoto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_comparar);

        configApp();

        configToolBar();

        configSpinner();
    }

    private void configApp() {
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerSelectCategoria);
        spinnerFoto1 = (Spinner) findViewById(R.id.spinnerFoto1);
        spinnerFoto2 = (Spinner) findViewById(R.id.spinnerFoto2);
        layoutFoto1 = (LinearLayout) findViewById(R.id.layoutFoto1);
        layoutFoto2 = (LinearLayout) findViewById(R.id.layoutFoto2);
    }

    private void configSpinner() {
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, FilesUntil.getCategoriasFoldersNames());
        spinnerCategoria.setAdapter(adapterList);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    layoutFoto1.setVisibility(View.VISIBLE);
                    ArrayList<String> itensList = FilesUntil.getItensFolderName(parent.getItemAtPosition(position).toString());

                    ArrayAdapter<String> adapterList = new ArrayAdapter<String>(MainCompararActivity.this, android.R.layout.simple_spinner_dropdown_item, itensList);
                    spinnerFoto1.setAdapter(adapterList);
                } else {
                    layoutFoto1.setVisibility(View.INVISIBLE);
                    layoutFoto2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void configToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MainCompararActivity.this);
            }
        });
    }

}
