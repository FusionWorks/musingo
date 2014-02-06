package iis.production.musingo.main.more;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by AGalkin on 1/18/14.
 */
public class HowToActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        finish();
    }
}