package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qainfomate.R;

import java.sql.Time;

public class WebViewer extends AppCompatActivity {

    private WebView wv;
    private TextView title;
    private ImageView dashboard, library, forum, market, timebtale, help;
    private Intent i;
    private String moodle_url;
    private String careers_url;
    private String internships_url;

    /*Here we override the onBackPressed function of the phone to,
    if possible to go back to last webpage visited,
    otherwise used would be taken to last activity visited which
    would mean poor User Experience*/
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

        title = findViewById(R.id.tv_title_web);
        dashboard = findViewById(R.id.iv_dashboard_bottom_web);
        library = findViewById(R.id.iv_library_bottom_web);
        forum = findViewById(R.id.iv_forum_bottom_web);
        market = findViewById(R.id.iv_market_bottom_web);
        timebtale = findViewById(R.id.iv_timetable_bottom_web);
        help = findViewById(R.id.iv_help_bottom_web);

        //initializing our webview
        wv = findViewById(R.id.web);
        wv.setWebViewClient(new WebViewClient());
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);


        moodle_url = "https://partnerships.moodle.roehampton.ac.uk";
        careers_url = "https://www.qa.com/careers/";
        internships_url = "https://qainternships.com/";

        //receiving which site should be loaded from last activity
        i = getIntent();
        String site = i.getStringExtra("site");
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

        // direct the user to Dashboard
        dashboard.setOnClickListener(v -> navigateTo(Dashboard.class));

        //directs user to timetable activity
        timebtale.setOnClickListener(v -> navigateTo(Timetable.class));

        //direct the user to the Library activity
        library.setOnClickListener(v -> navigateTo(Library.class));

        //navigate the user to Forum
        forum.setOnClickListener(v -> {
            Intent i = new Intent(WebViewer.this, ItemListClass.class);
            i.putExtra("ITEM", "Topics");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate user to the Help activity
        help.setOnClickListener(v -> navigateTo(Support.class));

        //navigate the user to the Market activity
        market.setOnClickListener(v -> {
            i = new Intent(WebViewer.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

    }
    public void navigateTo(Class goTo){
        i = new Intent(this, goTo);
        startActivity(i);
    }
}
