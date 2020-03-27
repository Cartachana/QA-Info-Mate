package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewer extends AppCompatActivity {

    private WebView wv;
    private TextView title;
    private Intent i;
    private final String moodle_url = "https://partnerships.moodle.roehampton.ac.uk";
    private final String careers_url = "https://www.qa.com/careers/";
    private final String internships_url = "https://qainternships.com/";

    @Override
    public void onBackPressed() {
        if(wv.canGoBack()){
            wv.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viwer);

        wv = findViewById(R.id.web);
        wv.setWebViewClient(new WebViewClient());
        title = findViewById(R.id.tv_title_web);

        i = getIntent();
        String site = i.getStringExtra("site");

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        switch (site){
            case "Moodle":
                title.setText(site);
                wv.loadUrl(moodle_url);
                break;
            case "Careers":
                title.setText(site);
                wv.loadUrl(careers_url);
                break;
            case "Internships":
                title.setText(site);
                wv.loadUrl(internships_url);
                break;
            default:
                break;
        }


    }
}
