package iis.production.musingo.main.more;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class TokenShopActivity extends Activity {
    ImageView lastClickedImage;
    boolean removeFocus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_shop);
        lastClickedImage = new ImageView(this.getApplicationContext());
        lastClickedImage.setTag("0");
        removeFocus = false;
        Utility.addSelecions(this, R.id.backButton, R.drawable.selected_back, R.drawable.back_button);
    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        finish();
    }

    public void toTokenBuy(View view){
        ImageView img = (ImageView)view;
        switch (Integer.parseInt(view.getTag().toString())){
            case 50:
                MusingoApp.soundButton();
                img.setImageResource(R.drawable.fifty);
                break;

            case 120:
                MusingoApp.soundButton();
                img.setImageResource(R.drawable.hundred);
                break;

            case 300:
                MusingoApp.soundButton();
                img.setImageResource(R.drawable.three_hundred);
                break;

            case 650:
                MusingoApp.soundButton();
                img.setImageResource(R.drawable.sixty);
                break;

            case 3000:
                MusingoApp.soundButton();
                img.setImageResource(R.drawable.three_thousend);
                break;
        }
        if(!lastClickedImage.equals(img) || removeFocus){
            switch (Integer.parseInt(lastClickedImage.getTag().toString())){
                case 50:
                    lastClickedImage.setImageResource(R.drawable.fifty_un);
                    break;

                case 120:
                    lastClickedImage.setImageResource(R.drawable.hundred_un);
                    break;

                case 300:
                    lastClickedImage.setImageResource(R.drawable.three_hundred_un);
                    break;

                case 650:
                    lastClickedImage.setImageResource(R.drawable.sixty_un);
                    break;

                case 3000:
                    lastClickedImage.setImageResource(R.drawable.three_thousend_un);
                    break;
                default:
                    break;
            }
        }

        lastClickedImage = (ImageView)view;
    }

    //IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            //mService = IInAppBillingService.Stub.asInterface(service);
        }
    };
}
