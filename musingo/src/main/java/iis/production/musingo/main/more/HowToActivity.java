package iis.production.musingo.main.more;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class HowToActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        Utility.addSelecions(this, R.id.backButton, R.drawable.selected_back, R.drawable.back_button);
    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        finish();
    }
}