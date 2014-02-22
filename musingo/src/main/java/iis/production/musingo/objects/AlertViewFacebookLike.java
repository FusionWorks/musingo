package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by dima on 2/22/14.
 */
public class AlertViewFacebookLike  extends AlertDialog {
    Activity activity;

    public AlertViewFacebookLike(Activity activity)
    {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alert_like_fb);

        TextViewArchitects notNowFb = (TextViewArchitects) findViewById(R.id.notNowLike);
        notNowFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                dismiss();
            }
        });

        final ImageView fbClick = (ImageView)findViewById(R.id.fb_like_click);
        fbClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                dismiss();
            }
        });
    }

}