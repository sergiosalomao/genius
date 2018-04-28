package game.com.br.genius;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.renderscript.Script;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HighScore extends AppCompatActivity {

    TextView txtYourScore,txtHighScore,txtScreenTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_high_score );

        txtScreenTitle = findViewById( R.id.txtScreenTitle );
        txtYourScore = findViewById( R.id.txtYourScore );
        txtHighScore = findViewById( R.id.txtHighScore );

        txtScreenTitle.setText(getScreenTitle() );
        txtYourScore.setText( "YOUR SCORE: " + getLastScore() );
        txtHighScore.setText( "HIGH SCORE: " + getHighScore() );

    }

    public void goBack(View view){

        Intent intent = new Intent(HighScore.this,MainActivity.class);
        startActivity(intent);


    }

    public int getHighScore(){
        SharedPreferences arquivoSalvo = getSharedPreferences( "user_preferences.xml",0);
        SharedPreferences.Editor manipuladorArquivo;
        manipuladorArquivo = arquivoSalvo.edit();

        int highscore = arquivoSalvo.getInt("highscore",0);
        manipuladorArquivo.apply();
        return highscore;
    }


    public int getLastScore(){
        SharedPreferences arquivoSalvo = getSharedPreferences( "user_preferences.xml",0);
        SharedPreferences.Editor manipuladorArquivo;
        manipuladorArquivo = arquivoSalvo.edit();

        int yourScore = arquivoSalvo.getInt("lastscore",0);
        manipuladorArquivo.apply();
        return yourScore;
    }

    public String getScreenTitle(){
        SharedPreferences arquivoSalvo = getSharedPreferences( "user_preferences.xml",0);
        SharedPreferences.Editor manipuladorArquivo;
        manipuladorArquivo = arquivoSalvo.edit();

        String screentitle = arquivoSalvo.getString("screentitle","GAME OVER");
        manipuladorArquivo.apply();
        return screentitle;
    }
}
