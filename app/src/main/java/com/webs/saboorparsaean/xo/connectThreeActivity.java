package com.webs.saboorparsaean.xo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class connectThreeActivity extends AppCompatActivity {

    static MediaPlayer select;
    static MediaPlayer background;
    static MediaPlayer win;
    static MediaPlayer gameover;
    public static final int X_CODE=0;
    public static final int O_CODE=1;
    public static final int NOT_PLAYED=2;
    private static final int NO_WINNER =-1 ;
    int winner=NO_WINNER;
    int[] gameState ={NOT_PLAYED, NOT_PLAYED, NOT_PLAYED,
                  NOT_PLAYED, NOT_PLAYED, NOT_PLAYED,
                  NOT_PLAYED, NOT_PLAYED, NOT_PLAYED};

    int [][] winningPositions={{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    ImageView btn_play_again;
    int activePlayer=X_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_three);


        //initializing the media players for the game sounds
        select = MediaPlayer.create(this,R.raw.select);
        background = MediaPlayer.create(this,R.raw.background);
        win = MediaPlayer.create(this,R.raw.win);
        gameover = MediaPlayer.create(this,R.raw.gameover);

        //starting the game music as the game starts
        background.start();

        btn_play_again=(ImageView) findViewById(R.id.btn_play_again);
        btn_play_again.setVisibility(View.INVISIBLE);
    }
        public void dropIn(View view){
            int tag=Integer.parseInt((String)view.getTag());
            if(winner!=NO_WINNER || gameState[tag] !=NOT_PLAYED){
                return;
            }
            ImageView img=(ImageView)view;
            ImageView imgWinOne=(ImageView)findViewById(R.id.imgWinOne);
            ImageView imgWinTwo=(ImageView)findViewById(R.id.imgWinTwo);
            ImageView fireworks=(ImageView)findViewById(R.id.fireworks);

            img.setTranslationY(-2000f);
            imgWinOne.setTranslationY(-2000f);
            imgWinTwo.setTranslationY(-2000f);
            fireworks.setTranslationY(2000f);
            if(activePlayer==X_CODE){
                select.start();
                img.setImageResource(R.drawable.xx);
                img.animate().translationY(0f).rotationBy(360f).setDuration(500);
                gameState[tag]=X_CODE;
                activePlayer=O_CODE;
            }else if(activePlayer==O_CODE){
                select.start();
                img.setImageResource(R.drawable.oo);
                img.animate().translationY(0f).rotationBy(360f).setDuration(500);
                gameState[tag]=O_CODE;
                activePlayer=X_CODE;
            }



            winner=checkWinner();
            if (winner!=NO_WINNER || filled()){

                String winnername=(winner==X_CODE)? "X" :(winner==O_CODE)?"O":"no Winner";
                if(winnername=="X"){
                    background.stop();
                    win.start();
                    imgWinOne.setImageResource(R.drawable.xx);
                    imgWinOne.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    imgWinTwo.setImageResource(R.drawable.xx);
                    imgWinTwo.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    fireworks.setImageResource(R.drawable.fireworks);
                    fireworks.animate().translationY(0f).rotationBy(2160f).setDuration(3000);
                }else if(winnername=="O"){
                    background.stop();
                    win.start();
                    imgWinOne.setImageResource(R.drawable.oo);
                    imgWinOne.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    imgWinTwo.setImageResource(R.drawable.oo);
                    imgWinTwo.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    fireworks.setImageResource(R.drawable.fireworks);
                    fireworks.animate().translationY(0f).rotationBy(2160f).setDuration(3000);
                }else if(winnername=="no Winner"){
                    background.stop();
                    gameover.start();
                    imgWinOne.setImageResource(R.drawable.oo);
                    imgWinOne.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    imgWinTwo.setImageResource(R.drawable.xx);
                    imgWinTwo.animate().translationY(0f).rotationBy(2160f).setDuration(1000);
                    Toast.makeText(this,"DROW!", Toast.LENGTH_LONG).show();
                }

                btn_play_again.setVisibility(View.VISIBLE);

                btn_play_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reset(null);
                        btn_play_again.setVisibility(View.INVISIBLE);
                        background.start();
                    }
                });

            }


        }

    public int checkWinner(){
        for(int[] positions : winningPositions){
            if (gameState[positions[0]]==gameState[positions[1]] &&
                    gameState[positions[1]]==gameState[positions[2]] &&
                    gameState[positions[0]]!=NOT_PLAYED){
                return gameState[positions[0]];
            }
        }

        return  NO_WINNER;
    }

    public boolean filled(){
        for(int i = 0; i < gameState.length;i++){
            if(gameState[i]==NOT_PLAYED){
                return false;
            }
        }
        return true;
    }

    public void reset(View v){
        activePlayer=X_CODE;
        winner=NO_WINNER;
        background = MediaPlayer.create(this,R.raw.background);
        background.start();
        btn_play_again=(ImageView) findViewById(R.id.btn_play_again);
        btn_play_again.setVisibility(View.INVISIBLE);
        for(int i=0; i<gameState.length;i++){
            gameState[i]=NOT_PLAYED;
        }

        GridLayout pg_layout=(GridLayout)findViewById(R.id.pg_layout);
        for(int i=0;i<pg_layout.getChildCount();i++){
            ImageView iv=(pg_layout.getChildAt(i) instanceof ImageView)?(ImageView)pg_layout.getChildAt(i):null;
            if (iv==null)return;
            iv.setImageResource(0);
        }

        LinearLayout imgLinearlayout=(LinearLayout)findViewById(R.id.imgLinearlayout);
        ImageView fireworks=(imgLinearlayout.getChildAt(0) instanceof ImageView)?(ImageView)imgLinearlayout.getChildAt(0):null;
        if (fireworks==null)return;
        fireworks.setImageResource(0);

        LinearLayout winner_sign=(imgLinearlayout.getChildAt(1) instanceof LinearLayout)?(LinearLayout)imgLinearlayout.getChildAt(1):null;
        if (winner_sign==null)return;
        ImageView imgWinOne=(winner_sign.getChildAt(11) instanceof ImageView)?(ImageView)winner_sign.getChildAt(11):null;
        if (imgWinOne==null)return;
        imgWinOne.setImageResource(0);
        ImageView imgWinTwo=(winner_sign.getChildAt(33) instanceof ImageView)?(ImageView)winner_sign.getChildAt(33):null;
        if (imgWinTwo==null)return;
        imgWinTwo.setImageResource(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem resetItem=menu.add("RESET");
        resetItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        resetItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                reset(null);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
