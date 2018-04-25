package game.com.br.genius;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
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

public class MainActivity extends AppCompatActivity {

    /*Script*/
    public static final String START = "Hi, let's see if you have a good memory ";
    public static final String STAGE1 = "We're just getting started!";
    public static final String STAGE2 = "Very Easy!";
    public static final String STAGE3 = "Not Bad!";
    public static final String STAGE4 = "You have a good memory!";
    public static final String STAGE5 = "I want to see the next one hit.";
    public static final String STAGE6 = "Think I underestimated you.";
    public static final String STAGE7 = "I want to see now!!!!";
    public static final String STAGE8 = "ARH!!!!";
    public static final String STAGE9 = "Not bad!!!!!!";
    public static final String STAGE10 = "Ok I give up I'll send the last string!";


    /*Declared global variables*/

    /*texts*/
    TextView txtInfo, txtGameover, txtScores, txtColorSelected,txtSequenceGreen,txtSequenceGray;

    /*Sound*/
    MediaPlayer beepclick, beepblink, risada;

    /*imgButton*/
    ImageButton btnStartGame;

    /*images*/
    ImageView imgBg, imgWin;

    /*animations*/
    Animation fadeInImgBg, fadeOutImgBg, fadeInStartBtn;

    /*Primary Variables*/
    int click = 0; //number of clicks
    int clickcont = 0; //count number of clicks
    int scores = 0; // scores
    int selectedNumber; /*selectedNumber Colors:  1 = green |  2 = red  |  3 = blue | 4 = yellow */
    int seq = 0; //sequency list.

    /*List and Arrays*/
    ArrayList<Integer> listClicks = new ArrayList<Integer>();
    ArrayList<Integer> listSeq = new ArrayList<Integer>();
    int array[] = new int[10]; // set qtd. stage of games

    /*Boolean*/
    Boolean active = false; // block clicks while sequence is run
    Boolean introductionStarting; // block clicks while introduction is run


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        /*Set variables images*/
        imgBg = findViewById( R.id.imgBg );
        imgBg.setVisibility( View.INVISIBLE );
        imgWin = findViewById( R.id.imgWin );
        imgWin.setVisibility( View.INVISIBLE );

        /*Set variables texts*/
        txtColorSelected = findViewById( R.id.txtPosicao );
        txtScores = findViewById( R.id.txtScores );
        txtGameover = findViewById( R.id.txtGameOver );
        txtInfo = findViewById( R.id.info );
        txtSequenceGray = findViewById( R.id.txtSequenceGray );
        txtSequenceGreen = findViewById( R.id.txtSequenceGreen );

        /*Set variables Animation*/
        fadeInImgBg = new AlphaAnimation( 0, 1 ); //show
        fadeInStartBtn = new AlphaAnimation( 0, 1 ); //show
        fadeOutImgBg = new AlphaAnimation( 1, 0 ); //hide

        /*Set variables Buttons*/
        btnStartGame = findViewById( R.id.btnStartGame );

        /*Set variables Audio*/
        beepblink = MediaPlayer.create( getApplicationContext(), R.raw.beepblink );
        beepclick = MediaPlayer.create( getApplicationContext(), R.raw.beepclick );
        risada = MediaPlayer.create( getApplicationContext(), R.raw.risada );


        /*Configurations OnCreate-------------------------------------------------*/
        fadeInStartBtn.setDuration( 4000 ); //time of fadeIn StarBtn
        fadeOutImgBg.setDuration( 3000 ); // time of fadeOut ImgBg
        fadeInImgBg.setDuration( 4000 ); // time of fadeIn ImgBg

        /*Start Animations*/
        btnStartGame.startAnimation( fadeInStartBtn ); // animation fadeIn show btnStartGame

        /*Hide objects*/
        txtInfo.setVisibility( View.INVISIBLE );
        txtGameover.setVisibility( View.INVISIBLE );

        /*Show objects*/
        imgBg.setVisibility( View.VISIBLE );


        /*Score definitions*/
        txtScores.setText( "SCORES : " + scores );

        /*Call introduction for load img colors on memory*/
        Introduction();
    }


    /*Turn off buttons*/
    public void offButtons() {
        imgBg.setImageResource( R.drawable.btoff );
    }

    /*Turn off buttons*/
    public void onButtons() {
        imgBg.setImageResource( R.drawable.bton );
    }

    /*blink the green button*/
    public void blinkGreen() {
        imgBg.setImageResource( R.drawable.btgreen );
    }

    /*blink the red button*/
    public void blinkRed() {
        imgBg.setImageResource( R.drawable.btred );
    }

    /*blink the blue button*/
    public void blinkBlue() {
        imgBg.setImageResource( R.drawable.btblue );
    }

    /*blink the yellow button*/
    public void blinkYellow() {
        imgBg.setImageResource( R.drawable.btyellow );
    }

    /*Audio Play*/
    public void playAudio(MediaPlayer mp) {

        if (mp != null) {
            mp.start();
        }
    }

    /*This function blink for test all buttons*/
    int i = 0;

    public void Introduction() {
        txtColorSelected.setText( "Loading colors..." );

        introductionStarting = true; /*block click */

        active = false; /*block click */

        new Thread() {
            public void run() {
                while (i++ < 16) {
                    try {
                        runOnUiThread( new Runnable() {

                            @Override
                            public void run() {

                                /*disable buttons */
                                offButtons();

                                if (i == 4 || i == 8 || i == 12) {
                                    blinkGreen();

                                }

                                if (i == 5 || i == 9 || i == 13) {
                                    blinkRed();

                                }

                                if (i == 6 || i == 10 || i == 14) {
                                    blinkBlue();

                                }

                                if (i == 7 || i == 11 || i == 15) {
                                    blinkYellow();
                                }

                                if (i == 16) {

                                    /*enable all buttons */
                                    onButtons();
                                    txtSequenceGreen.setText( txtSequenceGreen.getText()+ " ..........");

                                    /*play sound */
                                    playAudio( beepblink );


                                    introductionStarting = false;/*unlock click */
                                    txtColorSelected.setText( "All ready!" );
                                }
                            }
                        } );
                        Thread.sleep( 250 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }


    public void startGame(View view) {

        if (introductionStarting == false) {
            /*Hide objects */
            btnStartGame.setVisibility( View.INVISIBLE );
            txtGameover.setVisibility( View.INVISIBLE );
            imgWin.setVisibility( View.INVISIBLE );

            /*Show objects*/
            imgBg.setVisibility( View.VISIBLE );
            txtColorSelected.setVisibility( View.VISIBLE );
            txtInfo.setVisibility( View.VISIBLE );

            /*Initial configuration*/
            txtColorSelected.setText( "Get start! :)" );
            txtScores.setText( "SCORES : " + scores );
            txtSequenceGreen.setText( " " );

            Random r = new Random(); ///*mounts the call list*/
            for (int i = 0; i < array.length; i++) {
                array[i] = 1 + r.nextInt( 4 ); /* Generates random numbers with limit 4 and minimum 1.*/
            }

            /*unlock click */
            active = true;

            /*clear counter*/
            clickcont = 0;
            seq = 0;
            listClicks.clear();
            listSeq.clear();
            txtScores.setText( "SCORES : " + scores );

            /*first call color*/
            delayChamaLoad( 1000 );
        }
    }

    public void executaclick() {
        if (active) {
            /*sequence mark*/
            for (int i = 0; i<click ;i++){
                if (click == i){txtSequenceGreen.setText( txtSequenceGreen.getText()+ ".");}
            }

            playAudio( beepclick );
            if (seq == clickcont)
                if (listSeq.equals( listClicks )) {
                    active = false; /*block click */
                    click = click + 1;
                    clickcont = 0;
                    seq = 0;
                    scores = scores + 1;
                    txtScores.setText( "SCORES : " + scores );
                    listClicks.clear();
                    listSeq.clear();
                    delayChamaLoad( 2000 );
                } else {
                    gameover();
                }
        }
    }

    /*variables of position click*/
    Float real_x, real_y;

    @SuppressLint("ClickableViewAccessibility")
    public void playClick(View view) {
        imgBg.setOnTouchListener( new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        /*get image click position*/
                        real_x = (float) (event.getX());
                        real_y = (float) (event.getY());

                        if (active)
                            clickcont = clickcont + 1;
                        if (clickcont > seq) {
                            gameover();
                        }

                        if (active) {
                            if (real_x <= 400 && real_y <= 400) {
                                txtColorSelected.setText( "Clicked on green" );
                                selectedNumber = 1;
                                listClicks.add( selectedNumber );
                                blinkGreen();
                                executaclick();
                            } else if (real_x >= 500 && real_y <= 400) {
                                txtColorSelected.setText( "Clicked on red" );
                                selectedNumber = 2;
                                listClicks.add( selectedNumber );
                                blinkRed();
                                executaclick();
                            } else if (real_x > 500 && real_y > 500) {
                                txtColorSelected.setText( "Clicked on blue" );
                                selectedNumber = 3;
                                listClicks.add( selectedNumber );
                                blinkBlue();
                                executaclick();
                            } else if (real_x < 400 && real_y > 500) {
                                txtColorSelected.setText( "Clicked on yellow" );
                                selectedNumber = 4;
                                listClicks.add( selectedNumber );
                                blinkYellow();
                                executaclick();
                            } else {
                                txtColorSelected.setText( "Are you blind? click on one of the colors" );
                            }
                        }
                        break;
                }
                return true;
            }
        } );
    }

    public void gameover() {
        /*Hide*/
        txtInfo.setVisibility( View.INVISIBLE );
        txtGameover.setVisibility( View.VISIBLE );
        btnStartGame.setVisibility( View.VISIBLE );
        txtColorSelected.setVisibility( View.INVISIBLE );

        /*Actions*/
        txtInfo.setText( "You loser hahaha!" );
        active = false; /*block clicks*/
        imgBg.startAnimation( fadeOutImgBg );
        clickcont = 0;
        seq = 0;
        listClicks.clear();
        listSeq.clear();
        click = 0;
        scores = 0;


        fadeOutImgBg.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                offButtons();
                imgBg.setVisibility( View.INVISIBLE );
                btnStartGame.setVisibility( View.VISIBLE );
                txtColorSelected.setVisibility( View.INVISIBLE );
                txtGameover.setVisibility( View.VISIBLE );
                txtInfo.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        } );

    }


    public void delay(final int milliseconds) {
        runOnUiThread( new Runnable() {
        @Override
        public void run() {
            final Handler handler = new Handler();
            handler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    timeOff( null );

                }
            }, milliseconds );
        }
    } );
}


    public void delayChamaLoad(final int milliseconds) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        /*sequence mark*/
                        txtSequenceGreen.setTextColor( Color.GREEN );
                        for (int i = 0; i<array.length ;i++){
                            if (seq == i){txtSequenceGreen.setText( txtSequenceGreen.getText()+ ".");}
                        }

                        /*Script*/
                        if (scores == 0) txtInfo.setText( START );
                        if (scores == 1) txtInfo.setText( STAGE1 );
                        if (scores == 2) txtInfo.setText( STAGE2 );
                        if (scores == 3) txtInfo.setText( STAGE3 );
                        if (scores == 4) txtInfo.setText( STAGE4 );
                        if (scores == 5) {
                            txtInfo.setText( STAGE5 );
                            playAudio( risada );
                        }
                        if (scores == 6) {
                            txtInfo.setText( STAGE6 );
                            playAudio( risada );
                        }
                        if (scores == 7) txtInfo.setText( STAGE7 );
                        if (scores == 8) txtInfo.setText( STAGE8 );
                        if (scores == 9) txtInfo.setText( STAGE9 );
                        if (scores == 10) txtInfo.setText( STAGE10 );
                        loadList();
                        tempoLoad();
                        delay( 1000 );
                    }
                }, milliseconds );
            }
        } );
    }


    int contador;
    public void loadList() {

        new Thread() {
            public void run() {


                while (seq <= click) {
                    try {
                        runOnUiThread( new Runnable() {

                            @Override
                            public void run() {


                                if (array.length == scores) {
                                    endGame();
                                } else {


                                    if (seq <= click) {

                                        Integer x = array[seq];
                                        seq++;

                                        /*add sequence on the list of click*/
                                        listSeq.add( x );

                                        if (x == 1) {
                                            blinkGreen();
                                        }
                                        if (x == 2) {
                                            blinkRed();
                                        }
                                        if (x == 3) {
                                            blinkBlue();
                                        }
                                        if (x == 4) {
                                            blinkYellow();
                                        }

                                        /*play som and call next sequency */
                                        playAudio( beepblink );
                                        delay( 1000 );
                                    }
                                }
                            }
                        } );
                        Thread.sleep( 600 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    public void timeOff(View view) {
        offButtons();
    }

    public void tempoLoad() {
        new Timer().schedule( new TimerTask() {
            @Override
            public void run() {
                active = true; /*unlock click */
            }
        }, click * 500 );
    }

    public void endGame() {
        /*Show*/
        active = false;
        imgWin.setVisibility( View.VISIBLE );
        btnStartGame.setVisibility( View.VISIBLE );

        /*Hide*/
        txtColorSelected.setVisibility( View.INVISIBLE );
        txtInfo.setVisibility( View.INVISIBLE );

        /*Actions*/
        imgBg.startAnimation( fadeOutImgBg );
        clickcont = 0;
        seq = 0;
        listClicks.clear();
        listSeq.clear();
        click = 0;
        scores = 0;

        fadeOutImgBg.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                offButtons();
                imgBg.setVisibility( View.INVISIBLE );
                btnStartGame.setVisibility( View.VISIBLE );
                txtColorSelected.setVisibility( View.INVISIBLE );
                imgWin.setVisibility( View.VISIBLE );
                txtInfo.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        } );

    }
}
