package iis.production.musingo.objects;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.R;

/**
 * Created by AGalkin on 1/21/14.
 */
public class AlertViewPink extends AlertDialog {
    String titleText;
    String bodyText;
    public AlertViewPink(Context context, String title, String body)
    {
        super(context);
        this.titleText = title;
        this.bodyText = body;

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

        ImageView fbClick = (ImageView)findViewById(R.id.fb_click);
        fbClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Musingo", "click");
            }
        });
    }
}