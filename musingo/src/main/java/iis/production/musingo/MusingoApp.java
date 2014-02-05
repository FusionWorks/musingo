package iis.production.musingo;

import android.app.Application;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * Created by AGalkin on 1/29/14.
 */
public class MusingoApp extends Application {

    private MixpanelAPI mixpanelAPI;

    public MixpanelAPI getMixpanelobj(){
        return mixpanelAPI;
    }

    public void setMixpanelobj(MixpanelAPI mixpanelAPI){
        this.mixpanelAPI = mixpanelAPI;
    }
}
