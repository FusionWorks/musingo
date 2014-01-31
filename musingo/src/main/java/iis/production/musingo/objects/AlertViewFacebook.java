package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.R;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/21/14.
 */
public class AlertViewFacebook extends AlertDialog {
    String titleText;
    String bodyText;
    Activity activity;
    public AlertViewFacebook(Context context, String title, String body, Activity activity)
    {
        super(context);
        this.titleText = title;
        this.bodyText = body;
        this.activity = activity;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alert_fb);
        TextViewPacifico title = (TextViewPacifico)findViewById(R.id.title);
        title.setText(titleText);

        TextViewArchitects body = (TextViewArchitects)findViewById(R.id.body);
        body.setText(bodyText);

        //final FacebookManager fbUser = new FacebookManager();

        final ImageView fbClick = (ImageView)findViewById(R.id.fb_click);
        fbClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookManager.SessionFb(activity);
                dismiss();
            }
        });
    }
}