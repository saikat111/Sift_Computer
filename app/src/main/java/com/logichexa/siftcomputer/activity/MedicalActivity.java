package com.logichexa.siftcomputer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.logichexa.siftcomputer.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MedicalActivity extends AppCompatActivity {
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        // Get the PDF file from the assets folder.

        pdfView = findViewById(R.id.pdfView);

        displayPDFFromAssets("me.pdf");



    }
    private void displayPDFFromAssets(String assetFileName) {
        try {
            InputStream inputStream = getAssets().open(assetFileName);
            pdfView.fromStream(inputStream)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .fitEachPage(true)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}