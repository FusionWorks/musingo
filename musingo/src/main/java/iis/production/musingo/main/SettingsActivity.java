package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.R;
import iis.production.musingo.main.more.AboutActivity;
import iis.production.musingo.main.more.HowToActivity;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.AlertViewFacebook;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/18/14.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void goBackButton(View view){
        finish();
    }

    public void goToHowTo(View view){
        Intent intent = new Intent();
        intent.setClass(this, HowToActivity.class);
        startActivity(intent);
    }

    public void toTellFriend(View view){

        if(!FacebookManager.userFb.isSessionValid()){
            AlertViewFacebook dialog = new AlertViewFacebook(this, "Facebook", "Please ", SettingsActivity.this);
            dialog.show();
        }
        else{
            FacebookManager.PostFb(SettingsActivity.this);
        }
    }

    public void toAboutUs(View view){
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        intent.putExtra("mode", "us");
        startActivity(intent);
    }

    public void toAboutPartners(View view){
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        intent.putExtra("mode", "partners");
        startActivity(intent);
    }

    public void toTokenShop(View view){
        Intent intent = new Intent();
        intent.setClass(this, TokenShopActivity.class);
        startActivity(intent);

    }

    public void toSounds(View view){
        ImageView status = (ImageView)view.findViewById(R.id.imageRight);
        if(view.getTag().toString().equals("YES")){
            status.setImageResource(R.drawable.no);
            view.setTag("NO");
        }
        else{
            status.setImageResource(R.drawable.yes);
            view.setTag("YES");
        }
    }
}
