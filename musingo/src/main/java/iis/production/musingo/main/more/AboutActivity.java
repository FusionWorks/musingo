package iis.production.musingo.main.more;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;

/**
 * Created by AGalkin on 1/18/14.
 */
public class AboutActivity extends Activity {
    String mode;
    TextViewPacifico barTitle;
    LinearLayout aboutUs;
    LinearLayout aboutOurPartners;
    String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutUs = (LinearLayout)findViewById(R.id.aboutUs);
        aboutOurPartners = (LinearLayout)findViewById(R.id.aboutOurPartners);
        barTitle = (TextViewPacifico) findViewById(R.id.barTitle);
        Intent i = getIntent();
        mode = i.getStringExtra("mode");
        if(mode.equals("us")){
            setupUs();
            string = getString(R.string.site);
        }else if (mode.equals("partners")){
            setupPartners();
            string = getString(R.string.partner_site);
        }
        TextViewArchitects url1 = (TextViewArchitects)findViewById(R.id.text1);
        url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSite(string);
            }
        });

        TextViewArchitects url2 = (TextViewArchitects)findViewById(R.id.text2);
        url2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSite(string);
            }
        });
    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        finish();
    }

    public void setupUs(){
        barTitle.setText("About Us");
        aboutUs.setVisibility(View.VISIBLE);
    }

    public void setupPartners(){
        barTitle.setText("About Our Partners");
        aboutOurPartners.setVisibility(View.VISIBLE);
    }

    public void goToSite(String url){
        Log.v("Musingo", "url "+url);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);
    }

}
