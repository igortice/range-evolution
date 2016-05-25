package br.com.igortice.rangeevolution;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainSlideActivity extends AppCompatActivity {
    Spinner spinnerCategoria;
    FrameLayout slideArea;
    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_slide);

        configApp();

        configToolBar();

        configSpinner();
    }

    private void configApp() {
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerSelectCategoriaSlide);
        slideArea = (FrameLayout) findViewById(R.id.slideArea);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        customSwipeAdapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(customSwipeAdapter);
    }

    private void configToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MainSlideActivity.this);
            }
        });
    }

    private void configSpinner() {
        final ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, FilesUntil.getCategoriasFoldersNames());
        spinnerCategoria.setAdapter(adapterList);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    slideArea.setVisibility(View.VISIBLE);
                    String p = parent.getItemAtPosition(position).toString();
                    ArrayList<String> itensList = FilesUntil.getItensFolderNameFullPath(p);
                    viewPager.setAdapter(null);
                    customSwipeAdapter = new CustomSwipeAdapter(MainSlideActivity.this);
                    customSwipeAdapter.addNewCategoriaImages(itensList);
                    viewPager.setAdapter(customSwipeAdapter);
                } else {
                    slideArea.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
