package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by AGalkin on 2/6/14.
 */
public class AlertViewOrange extends AlertDialog {
    String titleText;
    String bodyText;
    String detailText;
    Activity activity;
    public AlertViewOrange(String title, String body, String detailText, Activity activity)
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
        details.setText(detailText);

        final ImageView button = (ImageView)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                dismiss();
            }
        });
    }

}