package iis.production.musingo.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.adapter.LevelPagerAdapter;
import iis.production.musingo.adapter.PackagesListAdapter;
import iis.production.musingo.async.ATPackages;
import iis.production.musingo.async.ATPackagesList;
import iis.production.musingo.async.ATSongs;
import iis.production.musingo.db.PackageTable;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.AlertViewFacebookLike;
import iis.production.musingo.objects.AlertViewOrange;
import iis.production.musingo.objects.AlertViewRateApp;
import iis.production.musingo.objects.LevelViewPager;
import iis.production.musingo.objects.Package;
import iis.production.musingo.objects.Playlist;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.service.TimerService;
import iis.production.musingo.utility.DidYouKnow;
import iis.production.musingo.utility.Endpoints;
import iis.production.musingo.utility.NetworkInfo;
import iis.production.musingo.utility.RoundedCornersDrawable;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class LevelSelectionActivity extends Activity {
    // views
    RelativeLayout dropDown;
    ImageView arrow;
    ArrayList<ImageView> levelViews;
//    LinearLayout levels;
    ArrayList<RelativeLayout> playlistViews;
    RelativeLayout loadingAnimation;
    TextViewArchitects didyouknowText;
    TextViewPacifico starNumber;

    ATSongs ATS;
    ATPackages ATP;
    ATPackagesList ATPL;

    int starsToUnlock;
    String packageName;

    LevelViewPager viewPager;

//    ImageView level1;
//    ImageView level2;
//    ImageView level3;
//    ImageView level4;
//    ImageView level5;
//    ImageView level6;
//    ImageView level7;
//    ImageView level8;
//    ImageView level9;

    RelativeLayout playlist1;
    RelativeLayout playlist2;
    RelativeLayout playlist3;
    RelativeLayout playlist4;
    RelativeLayout playlist5;
    RelativeLayout playlist6;
    RelativeLayout playlist7;
    RelativeLayout playlist8;
    RelativeLayout playlist9;

    RelativeLayout container;

    final static int NEXT_LEVEL = 1;

    //variables
    boolean opened = false;
    boolean beat;
    boolean complete;
    boolean powerup;
    boolean listOpen = false;
    boolean likePageOpen = false;

    public static boolean clickable;
    public static boolean unlocked;
    boolean tutorialOpen = false;

    boolean nextLevel = false;
    int scoreToBeat;

    List<View> pages;
    static ArrayList<Song> gameSongs;
    int selectedLevel;
    int selectedPackage;

    boolean packageDownloading = true;
    boolean playlistDownloading = false;
    ViewPager.OnPageChangeListener viewPagerListener;
    ArrayList<Playlist> playlists;
    ArrayList<Package> packagesList;

    int previousSelected = 1;

    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";

    LinearLayout tutorial1Package;
    LinearLayout tutorial2Package;
    LinearLayout tutorial3Package;

    TextViewArchitects packageNumber;
    ListView packagesListView;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        didyouknowText = (TextViewArchitects)findViewById(R.id.didyouknowText);
        DidYouKnow.random(didyouknowText, this);
        opened = false;
        levelViews = new ArrayList<ImageView>();
        gameSongs = new ArrayList<Song>();

        container = (RelativeLayout)findViewById(R.id.container);

        //Views initialize

        pages = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(this);
        View page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);
        page = inflater.inflate(R.layout.package_page, null);
        pages.add(page);

//        level1 = (ImageView)findViewById(R.id.level1);
//        level2 = (ImageView)findViewById(R.id.level2);
//        level3 = (ImageView)findViewById(R.id.level3);
//        level4 = (ImageView)findViewById(R.id.level4);
//        level5 = (ImageView)findViewById(R.id.level5);
//        level6 = (ImageView)findViewById(R.id.level6);
//        level7 = (ImageView)findViewById(R.id.level7);
//        level8 = (ImageView)findViewById(R.id.level8);
//        level9 = (ImageView)findViewById(R.id.level9);
//        levelViews = new ArrayList<ImageView>();
//        levelViews.add(level1);
//        levelViews.add(level2);
//        levelViews.add(level3);
//        levelViews.add(level4);
//        levelViews.add(level5);
//        levelViews.add(level6);
//        levelViews.add(level7);
//        levelViews.add(level8);
//        levelViews.add(level9);

        LevelPagerAdapter pagerAdapter = new LevelPagerAdapter(pages);
        viewPager = (LevelViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);
        Log.v("Musingo", "mSettings.getInt(\"currentLevel\", 0)  : " + mSettings.getInt("currentLevel", 0));
        viewPager.setCurrentItem(mSettings.getInt("currentLevel", 0));

        viewPagerListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                selectedPackage = i + 1;
                String url = Endpoints.package_url + selectedPackage;
                Log.v("Musingo", "selectedPackage ------------- "+selectedPackage);
                changePackage();
//
//                for (int y = 0; y < levelViews.size(); y++) {
//                    ImageView imageView = levelViews.get(y);
//                    Log.v("Musingo", "level tagg tag " + imageView.getTag().toString());
//                    if (imageView.getTag().toString().equals("selected")) {
//
//                    }
//                    if (imageView.getTag().toString().equals(String.valueOf(selectedPackage))){
//                        makeSelected(imageView);
//                    }
//                }

//                ImageView ima = (ImageView)levels.findViewWithTag("selected");
//                final float scale = getResources().getDisplayMetrics().density;
//                int pixels = (int) (15 * scale + 0.5f);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ima.getLayoutParams();
//                params.height = pixels;
//                params.width = pixels;
//                ima.setLayoutParams(params);
//                ima.setTag(String.valueOf(previousSelected));
//
//                ima = (ImageView)levels.findViewWithTag(String.valueOf(selectedPackage));
//                Log.v("Musingo", "selected Package "+ selectedPackage);
//                Log.v("Musingo", "ima "+ ima);
//                makeSelected(ima);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        };

        viewPager.setOnPageChangeListener(viewPagerListener);
        loadingAnimation = (RelativeLayout)findViewById(R.id.loadingAnimation);
//        levels = (LinearLayout)findViewById(R.id.levels);
        starNumber = (TextViewPacifico)findViewById(R.id.starNumber);



        dropDown = (RelativeLayout)findViewById(R.id.dropDown);
        arrow = (ImageView)findViewById(R.id.arrow);
        LinearLayout purple = (LinearLayout)findViewById(R.id.purple);
        LinearLayout green = (LinearLayout)findViewById(R.id.green);
        LinearLayout orange = (LinearLayout)findViewById(R.id.orange);
        if (beat) orange.setVisibility(View.VISIBLE);
        if (complete) green.setVisibility(View.VISIBLE);
        if (powerup) purple.setVisibility(View.VISIBLE);

        selectedPackage = 1;

        Utility.addSelecions(this, R.id.backButton, R.drawable.selected_back, R.drawable.back_button);
        getStarsCollected();

        tutorial1Package = (LinearLayout) findViewById(R.id.tutorial1Package);
        tutorial2Package = (LinearLayout) findViewById(R.id.tutorial2Package);
        tutorial3Package = (LinearLayout) findViewById(R.id.tutorial3Package);

        packageNumber = (TextViewArchitects) findViewById(R.id.packageNumber);
        packageNumber.setText(toString().valueOf(viewPager.getCurrentItem() + 1));

        packageNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickable){
                    listOpen = true;
                    clickable = false;
                packagesListView.setVisibility(View.VISIBLE);
                getPackagesList(packagesList);
                }
            }
        });

        ImageView backPackage =  (ImageView) findViewById(R.id.backPackage);
        backPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                if(currentPage > 0 && clickable){
                    clickable = false;
                    viewPager.setCurrentItem(currentPage - 1);
                }
            }
        });

        ImageView nextPackage =  (ImageView) findViewById(R.id.nextPackage);
        nextPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                if(clickable){
                    clickable = false;
                    viewPager.setCurrentItem(currentPage + 1);
                }
            }
        });

        getPackagesList();
        rateApp();
        facebookLike();
    }

    public void dropDown(View view){
        if(clickable){
            MusingoApp.soundButton();
            if(opened)
                hideMenu();
            else
                showMenu();
        }
    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        if(opened)
           hideMenu();
        else if(listOpen){
            packagesListView.setVisibility(View.GONE);
            listOpen = false;
            clickable = true;
        } else if (likePageOpen){
            webView.setVisibility(View.GONE);
            likePageOpen = false;
        }
        else {
            if(ATS != null)
                ATS.cancel(true);
            if(ATP != null)
                ATP.cancel(true);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBackButton(null);
        }
//        return super.onKeyDown(keyCode, event);
        return false;
    }

    public void showMenu(){
        opened = true;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.drop_down);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.setFillAfter(true);
        arrow.setImageResource(R.drawable.arrow_up);
        dropDown.startAnimation(anim);
    }
    public void hideMenu(){
        MusingoApp.soundButton();
        opened = false;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.drop_up);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.setFillAfter(true);

        arrow.setImageResource(R.drawable.arrow_down);
        dropDown.startAnimation(anim);
    }

//    public ImageView makeSelected(ImageView imageView){
//        final float scale = getResources().getDisplayMetrics().density;
//        int pixels = (int) (20 * scale + 0.5f);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//        params.height = pixels;
//        params.width = pixels;
//        imageView.setLayoutParams(params);
//        selectedPackage = Integer.parseInt(imageView.getTag().toString());
//        previousSelected = selectedPackage;
//        imageView.setTag("selected");
//        return imageView;
//    }
//
//    public ImageView makeUnselected(ImageView imageView){
//        final float scale = getResources().getDisplayMetrics().density;
//        int pixels = (int) (15 * scale + 0.5f);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//        params.height = pixels;
//        params.width = pixels;
//        imageView.setLayoutParams(params);
//        imageView.setTag(String.valueOf(previousSelected));
//        return imageView;
//    }

//    public void onLevelsSelectionClick(View view){
//        if(clickable){
//            MusingoApp.soundButton();
//            ImageView selectedView = (ImageView)levels.findViewWithTag("selected");
//            makeUnselected(selectedView);
//            makeSelected((ImageView)view);
//            viewPager.setCurrentItem(selectedPackage-1);
//        }
//    }

    public void goToLevel(View view){
        PlaySongsTable table = new PlaySongsTable(this);
//        int starBeat = table.getStarBeatByPackage(packageName);
        int levelId = view.getId();
        String levelName = getResources().getResourceName(levelId);
        int level = Integer.parseInt(levelName.substring(levelName.length()-1, levelName.length()));
//        Log.v("Musingo", "levelId : " + levelName + " , level : " + level + " , starBeat : " + starBeat);
//        if(clickable && unlocked && starBeat >= level - 1){


        if(mSettings.getBoolean("firstPlay", true) && level == 1 && viewPager.getCurrentItem() == 0){
            playlistDownloading = true;
            MusingoApp.soundButton();
            selectedLevel = Integer.valueOf(view.getTag().toString());
            for(Playlist playlist : playlists){
                if(selectedLevel == playlist.getListNumber()){
                    scoreToBeat = playlist.getScoreToBeat();
                }
            }
            Log.v("Musingo","tag tap " + view.getTag().toString());
            String url = Endpoints.playlist_url + selectedLevel;
            ATS = new ATSongs(this, url, loadingAnimation);

            NetworkInfo networkInfo = new NetworkInfo(this);
            if(networkInfo.isConnect()){
                ATS.execute();
                playlistDownloading = false;
            }
            else {
                networkAlert();
            }

            tutorial1Package.setVisibility(View.GONE);
            clickable = false;
            viewPager.setPagingEnabled(clickable);
        } else if(clickable && unlocked && !mSettings.getBoolean("firstPlay", true)){
            mSettings.edit().putInt("currentLevel",  viewPager.getCurrentItem()).commit();
            playlistDownloading = true;
            MusingoApp.soundButton();
            selectedLevel = Integer.valueOf(view.getTag().toString());
            for(Playlist playlist : playlists){
                if(selectedLevel == playlist.getListNumber()){
                    scoreToBeat = playlist.getScoreToBeat();
                }
            }
            Log.v("Musingo","tag tap " + view.getTag().toString());
            String url = Endpoints.playlist_url + selectedLevel;
            ATS = new ATSongs(this, url, loadingAnimation);

            NetworkInfo networkInfo = new NetworkInfo(this);
            if(networkInfo.isConnect()){
                ATS.execute();
                playlistDownloading = false;
            } else {
                networkAlert();
            }

            clickable = false;
            viewPager.setPagingEnabled(clickable);
        } else if(!tutorialOpen && viewPager.getCurrentItem()!=0){
            String title = getString(R.string.lock_package_title);
            String body =  getString(R.string.lock_package_body);
            String detail = getString(R.string.lock_package_detail);
            detail = detail.replace("#", String.valueOf(starsToUnlock));
            AlertViewOrange alertViewOrange = new AlertViewOrange(title, body, detail, this);
            alertViewOrange.show();
        }
    }

    public void downloadResultForLevels(ArrayList<Playlist> playLists, String packageName, int starsToUnlock){
        starsToUnlock = 0;
        this.starsToUnlock = starsToUnlock;
        this.packageName = packageName;
        this.playlists = playLists;

        selectPageViews();

        Log.v("Musingo", "size  "+playLists.size());
        TextViewArchitects songsTitle = (TextViewArchitects)findViewById(R.id.songsTitle);
        TextViewArchitects packageUnlock = (TextViewArchitects) findViewById(R.id.packageUnlock);

        PackageTable packageTable = new PackageTable(LevelSelectionActivity.this);
        packageTable.insertIntoPackageTable(packageName, false, 0);

        if(selectedPackage == 1){
            packageTable.updateUnlocked(true, packageName);
        }

        ImageView unlockNow = (ImageView) findViewById(R.id.unlock_now);

        if (packageTable.isUnlocked(packageName)){
            packageUnlock.setBackgroundResource(0);
            packageUnlock.setText("Unlocked:");
            songsTitle.setText(packageName);

            unlockNow.clearAnimation();
            unlockNow.setVisibility(View.GONE);
            Log.v("Musingo", "packageName BLAAA  "+packageName);
//            ImageView image = (ImageView)levels.findViewWithTag("selected");
//            image.setBackgroundResource(R.drawable.level_opened);

            unlocked = true;
        } else {
            packageUnlock.setBackgroundResource(R.drawable.round_corner_time);
            packageUnlock.setText(starsToUnlock + " STARS:");
            songsTitle.setText(packageName);

            LinearLayout packageStatus = (LinearLayout) findViewById(R.id.packageStatus);

            PlaySongsTable playSongsTable = new PlaySongsTable(this);
            if(playSongsTable.getSumStars() >= starsToUnlock){

                final Animation animation = new AlphaAnimation(1, 0);
                animation.setDuration(1000);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);

                unlockNow.setVisibility(View.VISIBLE);
                unlockNow.startAnimation(animation);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) unlockNow.getLayoutParams();
                int marginLeft = packageUnlock.getLeft();
                int marginTop = packageStatus.getBottom() + packageUnlock.getBottom();
                params.setMargins(marginLeft, marginTop, 0, 0);
                unlockNow.setLayoutParams(params);
            }

            unlocked = false;
        }

        for (int i = 0; i<playLists.size(); i++){
            Playlist playList = playLists.get(i);
            RelativeLayout view = playlistViews.get(i);

            view.setTag(String.valueOf(playList.getListNumber()));
            Log.v("Musingo","tag " + view.getTag().toString());
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextViewArchitects textView = (TextViewArchitects)view.findViewById(R.id.title);

            final RoundedCornersDrawable drawable = new RoundedCornersDrawable(getResources(), playList.getImage());

            if (!unlocked || (mSettings.getBoolean("firstPlay", true) && i != 0)){
                drawable.setAlpha(75);
            }

            imageView.setImageDrawable(drawable);

//            Utility.setBackgroundBySDK(imageView, song.getImage());
            textView.setText(playList.getName().toUpperCase());
            PlaySongsTable playSongsTable = new PlaySongsTable(this);
            HashMap<String, Integer> hash = new HashMap<String, Integer>();
            hash = playSongsTable.getSongByLevelNr(playList.getListNumber());

            ImageView beatStar = (ImageView)view.findViewById(R.id.beatStar);
            ImageView boostStar = (ImageView)view.findViewById(R.id.boostStar);
            ImageView completeStar = (ImageView)view.findViewById(R.id.completeStar);

            if(hash.containsKey("beatStar") && hash.get("beatStar") == 1){
                beatStar.setImageResource(R.drawable.star_beat);
            }
            if(hash.containsKey("completeStar") && hash.get("completeStar") == 1){
                completeStar.setImageResource(R.drawable.star_complete);
            }
            if(hash.containsKey("boostStar") && hash.get("boostStar") == 1){
                boostStar.setImageResource(R.drawable.star_boost);
            }

            Log.v("Musingo", "name " + playList.getName());
        }

        clickable = true;
        viewPager.setPagingEnabled(clickable);

//        tutorial1();
        tutorial();
    }

    public void downloadResultForGame(ArrayList<Song> songs, String name, int cost){
        gameSongs = songs;
        Log.v("Musingo", "Level selection : " + selectedLevel);
        Intent intent = new Intent();
        intent.setClass(this, MainGameActivity.class);
        intent.putExtra("scoreTobeat", scoreToBeat);
        intent.putExtra("name", name);
        intent.putExtra("cost", cost);
        intent.putExtra("selectedLevel", selectedLevel);
//<--- temp
        intent.putExtra("packageNumber", 2);
//--->>
        intent.putExtra("packageName", packageName);
        startActivityForResult(intent, NEXT_LEVEL);
        clickable = true;
        viewPager.setPagingEnabled(clickable);
    }

    public void changePackage(){

        packageDownloading = true;
        String url = Endpoints.package_url + selectedPackage;
        Drawable progressBar = getResources().getDrawable(R.drawable.progress_bar);
        ATP = new ATPackages(this, url, loadingAnimation, progressBar);

        NetworkInfo networkInfo = new NetworkInfo(this);
        if(networkInfo.isConnect()){
            ATP.execute();
            packageDownloading = false;
        }
        else {
            networkAlert();
        }

        packageNumber.setText(toString().valueOf(viewPager.getCurrentItem() + 1));
        clickable = false;
        viewPager.setPagingEnabled(clickable);

    }

    private void facebookLike() {
        int playlistsPlayed = mSettings.getInt("fbLikeShow", 0);
        Log.v("Musingo", "playlistsPlayed: " + playlistsPlayed);
        if(playlistsPlayed == 9 && mSettings.getBoolean("facebookLike", true)){
            AlertViewFacebookLike like = new AlertViewFacebookLike(this);
            likePageOpen = true;
            webView = (WebView) findViewById(R.id.webLike);
            like.show();
        }
    }

    public void selectPageViews(){
        View page = pages.get(viewPager.getCurrentItem());

        playlist1 = (RelativeLayout) page.findViewById(R.id.playlist1);
        playlist2 = (RelativeLayout) page.findViewById(R.id.playlist2);
        playlist3 = (RelativeLayout) page.findViewById(R.id.playlist3);
        playlist4 = (RelativeLayout) page.findViewById(R.id.playlist4);
        playlist5 = (RelativeLayout) page.findViewById(R.id.playlist5);
        playlist6 = (RelativeLayout) page.findViewById(R.id.playlist6);
        playlist7 = (RelativeLayout) page.findViewById(R.id.playlist7);
        playlist8 = (RelativeLayout) page.findViewById(R.id.playlist8);
        playlist9 = (RelativeLayout) page.findViewById(R.id.playlist9);

        playlistViews = new ArrayList<RelativeLayout>();
        playlistViews.add(playlist1);
        playlistViews.add(playlist2);
        playlistViews.add(playlist3);
        playlistViews.add(playlist4);
        playlistViews.add(playlist5);
        playlistViews.add(playlist6);
        playlistViews.add(playlist7);
        playlistViews.add(playlist8);
        playlistViews.add(playlist9);
    }

//    public boolean isUnlocked(String packageName){
//        if(selectedPackage == 1)
//            return true;
//        else
//            return false;
//    }

    public void getStarsCollected(){
        PlaySongsTable PST = new PlaySongsTable(this);
        starNumber.setText(String.valueOf(PST.getSumStars())+" ");
    }

    @Override
    public void onResume(){
        super.onResume();

        if (packageDownloading){
            String url = Endpoints.package_url + selectedPackage;
            Drawable progressBar = getResources().getDrawable(R.drawable.progress_bar);
            ATP = new ATPackages(this, url, loadingAnimation, progressBar);

            NetworkInfo networkInfo = new NetworkInfo(this);
            if(networkInfo.isConnect()){
                ATP.execute();
                packageDownloading = false;
            }
            else {
                networkAlert();
            }

            clickable = false;
            viewPager.setPagingEnabled(clickable);
        }
        if (playlistDownloading){
            ArrayList<Song> songs = new ArrayList<Song>();
            String url = Endpoints.playlist_url + selectedLevel;
            ATS = new ATSongs(this, url, loadingAnimation);

            NetworkInfo networkInfo = new NetworkInfo(this);
            if(networkInfo.isConnect()){
                ATS.execute();
                playlistDownloading = false;
            }
            else {
                networkAlert();
            }

            clickable = false;
            viewPager.setPagingEnabled(clickable);
        }
    }

    public void networkAlert(){
        String title = getString(R.string.network_title);
        String body = getString(R.string.network_body);
        String detail = getString(R.string.network_detail);

        AlertViewOrange alertViewOrange =  new AlertViewOrange(title, body, detail, this);
        alertViewOrange.show();
        alertViewOrange.setOnDismissListener( new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case NEXT_LEVEL:
                if (resultCode == NEXT_LEVEL) {
                    selectedLevel +=1;
                    if(clickable){
                        playlistDownloading = true;
                        MusingoApp.soundButton();
                        String url = Endpoints.playlist_url + selectedLevel;
                        ATS = new ATSongs(this, url, loadingAnimation);

                        NetworkInfo networkInfo = new NetworkInfo(this);
                        if(networkInfo.isConnect()){
                            ATS.execute();
                            playlistDownloading = false;
                        }
                        else {
                            networkAlert();
                        }

                        clickable = false;
                        viewPager.setPagingEnabled(clickable);
                    }
                }
                break;
        }
    }

    public void unlockPackage(View v){
//        PlaySongsTable playSongsTable = new PlaySongsTable(LevelSelectionActivity.this);
//        int starsCurrent = playSongsTable.getSumStars();
//        if (starsCurrent >= starsToUnlock){
            PackageTable packageTable = new PackageTable(LevelSelectionActivity.this);
            packageTable.updateUnlocked(true, packageName);
            downloadResultForLevels(playlists, packageName, starsToUnlock);
//        }
    }

    // - Tutorials
    public void nextTutorial(View v){
        switch (v.getId()){
            case R.id.tutorial1Package :
                MusingoApp.soundButton();
                tutorial1Package.setVisibility(View.GONE);
                showMenu();
                tutorial2Package.setVisibility(View.VISIBLE);
//                tutorialOpen = false;
                break;
            case R.id.tutorial2Package :
                MusingoApp.soundButton();
                tutorial2Package.setVisibility(View.GONE);
                hideMenu();
                tutorial3Package.setVisibility(View.VISIBLE);
                break;
            case R.id.tutorial3Package :
                MusingoApp.soundButton();
                tutorialOpen = false;
                clickable = true;
                tutorial3Package.setVisibility(View.GONE);
                mSettings.edit().putBoolean("tutorialPackage",false).commit();
                break;
        }
    }

//    public void tutorial1(){
//        if(mSettings.getBoolean("firstPlay", true) && viewPager.getCurrentItem() == 0){
//            RelativeLayout.LayoutParams params;
//            int marginTop;
//            params = (RelativeLayout.LayoutParams) tutorial1Package.getLayoutParams();
//
//            LinearLayout line1 = (LinearLayout) findViewById(R.id.line1);
//            RelativeLayout topBar = (RelativeLayout) findViewById(R.id.topBar);
//            LinearLayout packageStatus = (LinearLayout) findViewById(R.id.packageStatus);
//            marginTop = line1.getHeight() + topBar.getHeight() + packageStatus.getHeight();
//            Log.v("Musingo", "line1.getHeight() : " + line1.getHeight() + " , topBar.getHeight() : " + topBar.getHeight() + " , packageStatus.getHeight() : " + packageStatus.getHeight());
//            params.setMargins(0, marginTop, 0, 0);
//            tutorial1Package.setVisibility(View.VISIBLE);
//            tutorialOpen = true;
//        } else if(mSettings.getBoolean("firstPlay", true)) {
//            tutorial1Package.setVisibility(View.GONE);
//            tutorialOpen = false;
//            clickable = true;
//        }
//    }

    public void tutorial(){
        if(!mSettings.getBoolean("firstPlay", true) && mSettings.getBoolean("tutorialPackage", true) && viewPager.getCurrentItem() == 0){
            RelativeLayout.LayoutParams params;
            int marginTop;
            params = (RelativeLayout.LayoutParams) tutorial1Package.getLayoutParams();

            LinearLayout line1 = (LinearLayout) findViewById(R.id.line1);
            RelativeLayout topBar = (RelativeLayout) findViewById(R.id.topBar);
            LinearLayout packageStatus = (LinearLayout) findViewById(R.id.packageStatus);
            marginTop = line1.getHeight() + topBar.getHeight() + packageStatus.getHeight();
            params.setMargins(0, marginTop, 0, 0);

            tutorialOpen = true;
            clickable = false;
            tutorial3Package.setVisibility(View.GONE);
            tutorial2Package.setVisibility(View.GONE);
            tutorial1Package.setVisibility(View.VISIBLE);
        } else if (!mSettings.getBoolean("firstPlay", true) && mSettings.getBoolean("tutorialPackage", true)){
            hideMenu();
            tutorial3Package.setVisibility(View.GONE);
            tutorial2Package.setVisibility(View.GONE);
            tutorial1Package.setVisibility(View.GONE);
            tutorialOpen = false;
            clickable = true;
        }
    }

    public void getPackagesList(){

        String url = Endpoints.packages_url;
        Drawable progressBar = getResources().getDrawable(R.drawable.progress_bar);
        ATPL = new ATPackagesList(this, url, loadingAnimation, progressBar);

        NetworkInfo networkInfo = new NetworkInfo(this);
        if(networkInfo.isConnect()){
            ATPL.execute();
        }
        else {
            networkAlert();
        }

        clickable = false;
        viewPager.setPagingEnabled(clickable);
    }

    public void getPackagesList(ArrayList<Package> packagesList){
        this.packagesList = packagesList;
        PackagesListAdapter packagesListAdapter = new PackagesListAdapter(this, packagesList);
        packagesListView = (ListView)findViewById(R.id.packagesList);
        packagesListView.setAdapter(packagesListAdapter);

        packagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPackage = i+1;
                viewPager.setCurrentItem(i);
                packagesListView.setVisibility(View.GONE);
            }
        });
    }

    public void rateApp(){
        PlaySongsTable table = new PlaySongsTable(this);
        int playedGames = table.getPlayedLevels();
        if(playedGames == 3){
            mSettings.edit().putBoolean("timerService",false).commit();
        }

        if(!mSettings.getBoolean("timerService", true)){
            stopService(new Intent(this, TimerService.class));
            mSettings.edit().putBoolean("timerService",true).commit();
            String title = getString(R.string.title_rate);
            String body = getString(R.string.body_rate);
            AlertViewRateApp rate = new AlertViewRateApp(title, body, "", this);
            rate.show();
        }
    }
}