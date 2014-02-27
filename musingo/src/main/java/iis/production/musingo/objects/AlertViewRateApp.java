package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.service.TimerService;

/**
 * Created by AGalkin on 2/6/14.
 */
public class AlertViewRateApp extends AlertDialog {
    String titleText;
    String bodyText;
    String detailText;
    Activity activity;
    public AlertViewRateApp(String title, String body, String detailText, Activity activity)
    {
        super(activity);
        this.titleText = title;
        this.bodyText = body;
        this.detailText = detailText;
        this.activity = activity;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alert_orange);
        TextViewPacifico title = (TextViewPacifico)findViewById(R.id.title);
        title.setText(titleText);

        TextViewArchitects body = (TextViewArchitects)findViewById(R.id.body);
        body.setText(bodyText);

        TextViewArchitects details = (TextViewArchitects)findViewById(R.id.details);
        details.setVisibility(View.GONE);

        final ImageView button = (ImageView)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                }
                activity.startService(new Intent(activity, TimerService.class));
                dismiss();
            }
        });

        TextViewArchitects laterRate = (TextViewArchitects) findViewById(R.id.laterRate);
        laterRate.setVisibility(View.VISIBLE);
        laterRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startService(new Intent(activity, TimerService.class));
                dismiss();
            }
        });
    }
}