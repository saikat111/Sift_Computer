package com.logichexa.siftcomputer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.logichexa.siftcomputer.R;

import java.io.IOException;
import java.io.InputStream;

public class TruestActivity extends AppCompatActivity {
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truest);
        pdfView = findViewById(R.id.pdfView);

        displayPDFFromAssets("tu.pdf");
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