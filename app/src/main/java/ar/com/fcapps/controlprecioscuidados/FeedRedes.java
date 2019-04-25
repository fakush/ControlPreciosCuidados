package ar.com.fcapps.controlprecioscuidados;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FeedRedes extends Activity {

//    public static final String TAG = "TimeLineActivity";
//    private static final String baseURl = "<a href=\"https://twitter.com/intent/tweet?button_hashtag=PreciosCuidados&ref_src=twsrc%5Etfw\" class=\"twitter-hashtag-button\" data-show-count=\"false\">Tweet #PreciosCuidados</a><script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
//    private static final String widgetInfo = "<a href=\"https://twitter.com/intent/tweet?button_hashtag=PreciosCuidados&ref_src=twsrc%5Etfw\" class=\"twitter-hashtag-button\" data-show-count=\"false\">Tweet #PreciosCuidados</a><script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script> ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_redes);

        MobileAds.initialize(this, "@string/ad_FeedRedes");
        AdView mAdView = findViewById(R.id.adView_Feed);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        WebView webView = (WebView) findViewById(R.id.feed);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);

    }
}
