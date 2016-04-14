package br.com.igortice.rangeevolution;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotoResultActivity extends AppCompatActivity {

    private static final String FOLDER_RAIZ = "RangeEvolution";
    private Storage storage;
    private Spinner spinner;
    private ArrayList<String> itensList;
    private ArrayAdapter<String> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_result);

        configToolBar();

        configGetImage();

        configFolderRaiz();

        configBtnAddCat();

        configSpinner();

        Button btnSalvarFoto = (Button) findViewById(R.id.btnSalvarFoto);
//        btnSalvarFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Storage storage = SimpleStorage.getExternalStorage();
//                storage.deleteDirectory("RangeEvolution");
//                storage.createDirectory("RangeEvolution");
//                storage.createDirectory("RangeEvolution/_saude", true);
//                storage.createFile("RangeEvolution", "saude", "");
//                storage.createDirectory("RangeEvolution/_casa", true);
//                storage.createFile("RangeEvolution", "casa", "");
//            }
//        });
    }

    private void configSpinner() {
        List<File> files = storage.getNestedFiles(FOLDER_RAIZ);
        spinner = (Spinner)findViewById(R.id.spinner);
        itensList = new ArrayList<>(Arrays.asList("Escolha a categoria"));
        for (File f : files) {
            itensList.add(f.getName().toString());
        }
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itensList);
        spinner.setAdapter(adapterList);
    }

    private void configBtnAddCat() {
        Button btnAddCat = (Button) findViewById(R.id.btnAddCat);
        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                EditText editText = (EditText) findViewById(R.id.txtAddCat);
                String val = editText.getText().toString();
                editText.setText("");
                if(!itensList.contains(val))
                    itensList.add(val);
                adapterList.notifyDataSetChanged();
                spinner.setSelection(itensList.indexOf(val));
            }
        });
    }

    private void configFolderRaiz() {
        storage = SimpleStorage.getExternalStorage();
        boolean dirExists = storage.isDirectoryExists(FOLDER_RAIZ);
        if (!dirExists)
            storage.createDirectory(FOLDER_RAIZ);
    }

    private void configGetImage() {
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        ImageView imageView = (ImageView) findViewById(R.id.photoResult);
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android);
        imageView.setImageBitmap(bitmap);
    }

    private void configToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(PhotoResultActivity.this);
            }
        });
    }

}
