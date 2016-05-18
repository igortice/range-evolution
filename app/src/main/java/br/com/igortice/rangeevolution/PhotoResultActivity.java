package br.com.igortice.rangeevolution;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sromku.simple.storage.Storage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PhotoResultActivity extends AppCompatActivity {

    private Storage storage;
    private Spinner spinner;
    private ArrayList<String> itensList;
    private ArrayAdapter<String> adapterList;
    private Button btnSalvarFoto;
    private String folderSelecionada;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_result);

        configApp();

        configToolBar();

        configGetImage();

        configBtnAddCat();

        configSpinner();

        configBtnSalvar();
    }

    private void configApp() {
        storage = FilesUntil.getStorage();
    }

    private void configBtnSalvar() {
        btnSalvarFoto = (Button) findViewById(R.id.btnSalvarFoto);
        btnSalvarFoto.setEnabled(false);
        btnSalvarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnSalvarFoto.setEnabled(true);
                ProgressDialog.show(PhotoResultActivity.this, "", "Aguarde...", true).show();
                String nameFile = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                storage.move(new File(mCurrentPhotoPath), folderSelecionada, nameFile + ".jpg");
                Intent intent = new Intent(PhotoResultActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Imagem Salva", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configSpinner() {
        List<File> files = FilesUntil.getFoldersNames();
        spinner = (Spinner) findViewById(R.id.spinner);
        itensList = new ArrayList<>(Arrays.asList("Escolha a categoria"));
        for (File f : files) {
            itensList.add(f.getName().toString());
        }
        Collections.sort(itensList);
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itensList);
        spinner.setAdapter(adapterList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    btnSalvarFoto.setEnabled(true);
                    folderSelecionada = getFolderPath(parent.getItemAtPosition(position).toString());
                } else
                    btnSalvarFoto.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnSalvarFoto.setEnabled(false);
            }
        });
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
                String nome_folder = editText.getText().toString().toLowerCase();
                storage.createDirectory(getFolderPath(nome_folder));
                editText.setText("");
                if (!itensList.contains(nome_folder))
                    itensList.add(nome_folder);
                Collections.sort(itensList);
                adapterList.notifyDataSetChanged();
                spinner.setSelection(itensList.indexOf(nome_folder));
            }
        });
    }

    private void configGetImage() {
        ImageView imageView = (ImageView) findViewById(R.id.photoResult);

        mCurrentPhotoPath = getIntent().getExtras().getString("ImagePath");

        Bitmap bitmapImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
        imageView.setImageBitmap(bitmapImage);
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

    private String getFolderPath(String folder) {
        return FilesUntil.FOLDER_RAIZ + "/" + folder;
    }
}
