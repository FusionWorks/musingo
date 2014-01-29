package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import iis.production.musingo.R;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.TextViewArchitects;

public class MainGameActivity extends Activity {

    TextView coins;
    TextViewArchitects scoreToBeat;
    TextViewArchitects yourScore;

    MediaPlayer mediaPlayer;
    long start; // temp
    long end; // temp
    AssetFileDescriptor descriptor = null; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        startPlaying();
    }

    public void goBackButton(View view){
        finish();
    }

    public void toTokenShop(View view){
        Intent intent = new Intent();
        intent.setClass(this, TokenShopActivity.class);
        startActivity(intent);
    }

    public void startPlaying(){
        MediaPlayer mediaPlayer;
        mediaPlayer = new MediaPlayer();


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer arg0) {
                // TODO Auto-generated method stub
                arg0.start(); //Запускаем на воспроизведение
            }
        });
//Загрузка файла из внешнего источника
        try {
            mediaPlayer.setDataSource("http://www.tamgdesvet.ru/music/TamGdeSvet-Proshay.mp3");
            //Кстати, я играю в этой группе на басу.
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Не блокировать этот поток, выполняя подготовку в асинхронном режиме
        mediaPlayer.prepareAsync();
    }
}
