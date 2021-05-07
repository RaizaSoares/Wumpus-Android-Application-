/*****************************************************
 * @Author: Raiza Soares
 * @Checklist (Part 2):
 *
 * Mark off what items are complete, and put a P if partially complete. If 'P' include how to test what is working for partial credit below the checklist line.
 *
 * You must “reasonably” complete the lower tiers before gaining points for the later tiers. By “reasonably,”
 * I can reasonably assume you honestly thought it was complete.
 *
Tierless: State machine*	24
_X_ State machine framework is present
_X_ Framework controls bow, arrows, pickup
_X_ Framework controls exit or not
_X_ End conditions


__ Tierless: rotation (-4 each item missed) *	8


1a: Layout *	24
_X_ Arrow buttons
_X_ Player select exit and opens
_P_ Majority of area the game area   (SEE NOTE BELOW)
_X_ Aspect kept, and resize to new devices works
_X_ Different layout for portrait and landscape


1b: Game logic	30
_X_ Bat event (loss of bow, arrows, and new position)
_X_ Rooms start hidden, and then revealed
_X_ Shooting the arrow components (Wumpus and loss)
_X_ Player select works *


2 Tier: End game	14
_X_ End dialog exists
_X_ Both end dialogs open at right time
_X_ End activity score/loss reason correct (50% each)


3: extensions 	30
Extension 1: <1b> <10> <add another 1+ player option that uses another primitive>: <how to test: There is also a triangle player option in the
FAB menu.>
Extension 2: <5c> <20> <Better hunt the wumpus: Allow arrow pickup after shooting with these rules>: <
There is a priority for the arrows. Wumpus > Bat > Pit.
If a Wumpus is within shooting range, even if a bat or pit is closer, the Wumpus will be shot. Similarly if a bat is within shooting range,
even if a pit is closer, the bat gets shot. Arrows are lost permanently to the Wumpus, bat and pit.
If neither of these entities are within the shooting range, the arrow can be picked up two rooms away in the shooting direction, UNLESS:
The room two rooms down is the exit, in which case the arrow flies out, and is lost permanently.
OR
if there is another arrow in the room two doors down, in which case the current arrow is randomly placed in an empty room.>

@NOTE ON Tier 1a Majority of area the game area : for smaller screen sizes, the game area is the majority of the area in portrait mode but is not
always the majority of the are in landscape mode. For bigger screen sizes (checked by WYSIWYG), the game area is the majority of the area in landscape
mode but is not always the majority of the are in portrait mode.

 *
 * @Notes: 1)
 *  Cheat mode displays coordinates in (x, y) fashion, with the top left room having coordinates (0, 0)
 *  2) Reset does not change the Player type. i.e. your preferences for the type of player are stored.
 *
 * @bugs: No known bugs.
 */


package edu.sdsmt.soares_raiza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameView gv;
    private StateMachine Sm;
    private TextView scoreDisplay;
    private int scoreVal = 0;
    private TextView cheatDisplay;
    private String cheat = "";
    private String bow = "Bow: no";
    private int arrow1 = 0;
    private int arrow2 = 0;
    private int arrows = 0;
    private boolean bundleFlag = false;
    private boolean exitFlag = false;

    private TextView bowDisplay;
    private TextView arrowDisplay;
    private Button aL;
    private Button aR;
    private Button aU;
    private Button aD;

    private Boolean Arrowbuttonflag= false;
    private ArrayList<Integer> Inf = new ArrayList<Integer>();
    private ArrayList<Integer> Inf2 = new ArrayList<Integer>();
    private ArrayList<Integer> visited = new ArrayList<Integer>(Collections.nCopies(16, 0));
    private ArrayList<Boolean> boolVals = new ArrayList<Boolean>();

    private Boolean pitEntered= false;
    private Boolean WumpusEncountered = false;
    private Boolean WumpusShot = false;
    private Boolean arrow1Picked = false;
    private Boolean arrow2Picked = false;
    private Boolean firstTime = true;
    private StateMachine.StateEnum st;
    private int state = 0;

    private FloatingActionButton fabSquare;
    private FloatingActionButton fabCircle;
    private FloatingActionButton fabTriangle;
    private Boolean isFABOpen = false;
    private Boolean defaultPlayer = true;
    private int defPlayer = 0;
    private Boolean batHit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreDisplay = findViewById(R.id.textView);
        cheatDisplay = findViewById(R.id.ch);
        bowDisplay = findViewById(R.id.textView2);
        arrowDisplay = findViewById(R.id.textView3);
        aD = findViewById(R.id.Adown);
        aR = findViewById(R.id.Aright);
        aL = findViewById(R.id.Aleft);
        aU = findViewById(R.id.Aup);
        fabSquare = findViewById(R.id.fabSquare);
        fabCircle = findViewById(R.id.fabCircle);
        fabTriangle = findViewById(R.id.fabTriangle);

        gv = findViewById(R.id.gameView);
        Sm = new StateMachine(gv, this);
        Sm.setState(StateMachine.StateEnum.NoBow);
        //Inf2 = gv.cheat();
        miniupdateUI();
    }
    public void onL(View GameView)
    {
        scoreVal = scoreVal + 1;
        List<Integer> coords = gv.givePlayerCoordinates();
        int x = coords.get(0);
        int y = coords.get(1);
        int xCons = coords.get(2);
        int yCons = coords.get(3);
        int xSt = coords.get(4);
        int ySt = coords.get(5);
        int radius = coords.get(6);

        if(x - 2*radius < xSt)
        {
            x = xCons;
        }
        else{
            x = x - 2*radius;
        }
        gv.getPlayerCoordinates(x, y);

        // statemachine call
        Sm.buttonPressed("moveLeft");

        //updateUI();
        miniupdateUI();
        gv.invalidate();

    }
    public void onR(View GameView)
    {
        scoreVal = scoreVal + 1;
        List<Integer> coords = gv.givePlayerCoordinates();
        int x = coords.get(0);
        int y = coords.get(1);
        int xCons = coords.get(2);
        int yCons = coords.get(3);
        int xSt = coords.get(4);
        int ySt = coords.get(5);
        int radius = coords.get(6);

        if(x + 2*radius > xCons)
        {
            x = xSt;
        }
        else{
            x = x + 2*radius;
        }
        gv.getPlayerCoordinates(x, y);
        Sm.buttonPressed("moveRight");
        miniupdateUI();
        gv.invalidate();
    }

    public void onU(View GameView)
    {
        scoreVal = scoreVal + 1;
        List<Integer> coords = gv.givePlayerCoordinates();
        int x = coords.get(0);
        int y = coords.get(1);
        int xCons = coords.get(2);
        int yCons = coords.get(3);
        int xSt = coords.get(4);
        int ySt = coords.get(5);
        int radius = coords.get(6);

        if(y - 2*radius < ySt)
        {
            y = yCons;
        }
        else{
            y = y - 2*radius;
        }
        gv.getPlayerCoordinates(x, y);

        Sm.buttonPressed("moveUp");
        //updateUI();
        miniupdateUI();
        gv.invalidate();
    }

    public void onD(View GameView)
    {
        scoreVal = scoreVal + 1;
        List<Integer> coords = gv.givePlayerCoordinates();
        int x = coords.get(0);
        int y = coords.get(1);
        int xCons = coords.get(2);
        int yCons = coords.get(3);
        int xSt = coords.get(4);
        int ySt = coords.get(5);
        int radius = coords.get(6);

        if(y + 2*radius > yCons)
        {
            y = ySt;
        }
        else{
            y = y + 2*radius;
        }
        gv.getPlayerCoordinates(x, y);

        Sm.buttonPressed("moveDown");
        //updateUI();
        miniupdateUI();
        gv.invalidate();
    }

    public void onAup(View GameView)
    {
        Sm.buttonPressed("Aup");
        arrowUpdates();
    }
    public void onAdown(View GameView)
    {
        Sm.buttonPressed("Adown");
        arrowUpdates();
    }
    public void onAleft(View GameView)
    {
        Sm.buttonPressed("Aleft");
        arrowUpdates();
    }
    public void onAright(View GameView)
    {
        Sm.buttonPressed("Aright");
        arrowUpdates();
    }
    public void shotWumpus()
    {
        cheat = "Wumpus shot!";
        WumpusShot = true;
    }
    public void arrowUpdates()
    {
        arrow1= gv.getArrow1Count();
        arrow2= gv.getArrow2Count();
        miniupdateUI();
        gv.invalidate();
    }

    public void setBow(String status)
    {
        bow = "Bow: "+ status;
        cheat = "BOW";
        //miniupdateUI();

    }
    public String causeOfDeath()
    {
        if(gv.pitent())
        {
            //pitEntered = true;
            miniupdateUI();
            return "Fell in pit";

        }
        else if(gv.wumpusEnc())
        {
            //WumpusEncountered = true;
            miniupdateUI();
            return "Eaten by Wumpus";
        }
        else if(gv.bothArrowsShot())
        {
            //arrow1Picked = true;
            //arrow2Picked = true;
            miniupdateUI();
            return "Cannot defeat the Wumpus";
        }
        return "";
    }
    public void setArrows()
    {
        arrow1 = gv.getArrow1Count();
        arrow2 = gv.getArrow2Count();
        cheat = "ARROW";
        //miniupdateUI();
    }
    public void batEvent()
    {
        gv.batEvent();
        arrow1=0;
        arrow2=0;
        cheat= "BAT";
        bow = "Bow: no";
        //miniupdateUI();
    }
    public void bat()
    {
        cheat = "BAT";
    }

    public void onReset(View GameView)
    {
        disableEnableArrow(false);
        scoreVal = 0;
        bow = "Bow: no";
        arrows = 0;
        arrow1 = 0;
        arrow2 = 0;
        exitFlag = false;
        List<Integer> coords = gv.givePlayerCoordinates();
        int xSt = coords.get(4);
        int ySt = coords.get(5);
        gv.getPlayerCoordinates(xSt, ySt);
        gv.reset();
        cheat = "";
        gv.setExitflag(false);
        Sm.setState(StateMachine.StateEnum.NoBow);
        miniupdateUI();
        gv.invalidate();
        Inf = gv.givePlayerCoordinates();
        Inf2 = gv.cheat();
        visited= gv.giveVisited();
        boolVals = gv.giveBooleans();
        extractBooleans(boolVals);

    }
    public void onBurst(View view) {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }
    private void showFABMenu() {
        isFABOpen = true;
        fabCircle.animate().translationY(+DpToPixels(55));
        fabSquare.animate().translationY(+DpToPixels(105));
        fabTriangle.animate().translationY(+DpToPixels(155));
    }
    private void closeFABMenu() {
        isFABOpen = false;
        fabCircle.animate().translationY(0);
        fabSquare.animate().translationY(0);
        fabTriangle.animate().translationY(0);
    }
    public float DpToPixels(float dp) {
        float pxPerDp = (float) getResources().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT;
        return dp * pxPerDp;
    }
    public void extractBooleans(ArrayList<Boolean> bools)
    {
        firstTime = bools.get(0);
        WumpusShot = bools.get(1);
        WumpusEncountered = bools.get(2);
        pitEntered = bools.get(3);
        arrow1Picked = bools.get(4);
        arrow2Picked = bools.get(5);
        defaultPlayer = bools.get(6);
        batHit = bools.get(7);
    }
    public void giveBooleans()
    {
        boolVals = new ArrayList<Boolean>();

        boolVals.add(firstTime);
        boolVals.add(WumpusShot);
        boolVals.add(WumpusEncountered);
        boolVals.add(pitEntered);
        boolVals.add(arrow1Picked);
        boolVals.add(arrow2Picked);
        boolVals.add(defaultPlayer);
        boolVals.add(batHit);

        gv.getBooleans(boolVals);
    }
    public void batJustShot()
    {
        cheat = "BAT SHOT";

    }
    public void ArrowLandedInPit()
    {
        cheat = "Arrow landed in pit";

    }

    public void onCheat(View GameView)
    {
        List<Integer> locs = gv.cheat();
        cheat = "(" + locs.get(12) + "," + locs.get(13) + ") Exit player\n"+
                "(" + locs.get(14) + "," + locs.get(15) + ") Pit\n"+
                "(" + locs.get(2) + "," + locs.get(3) + ") Wumpus\n" +
                "(" + locs.get(6) + "," + locs.get(7) + ") bat\n" +
                "(" + locs.get(4) + "," + locs.get(5) + ") bow\n" +
                "(" + locs.get(8) + "," + locs.get(9) + ") arrow0\n" +
                "(" + locs.get(10) + "," + locs.get(11) + ") arrow1";
        miniupdateUI();
        gv.invalidate();

    }
    public void setExitFlag(Boolean b)
    {
        exitFlag = b;
    }
    private void setBundleFlag()
    {
        bundleFlag = true;
    }

    public void onRectangle(View Gameview)
    {
        gv.changeDefaultPlayer(false, 1);
        miniupdateUI();
        gv.invalidate();
    }
    public void onCircle(View Gameview)
    {
        gv.changeDefaultPlayer(true, 0);
        miniupdateUI();
        gv.invalidate();
    }
    public void onTriangle(View Gameview)
    {
        gv.changeDefaultPlayer(true, 2);
        miniupdateUI();
        gv.invalidate();
    }


    private void miniupdateUI()
    {
        String disp = "Score: " + scoreVal;
        arrows = arrow1+ arrow2;
        String arr = "Arrows: " + arrows;
        scoreDisplay.setText(disp);
        bowDisplay.setText(bow);
        arrowDisplay.setText(arr);
        cheatDisplay.setText(cheat);
        cheat= "";
        if(bundleFlag)
        {
            gv.getRandom(Inf2);
            gv.getPlayerInfo(Inf);
            gv.getvisited(visited);
            gv.setArrows(arrow1, arrow2);
            giveBooleans();
            int x= 0;  //100
            int y = 0; //40
            if(!Inf.isEmpty())
            {
                x = Inf.get(0);
                y = Inf.get(1);
            }
            gv.getPlayerCoordinates(x, y);
            gv.setLocationFlag(true);
            bundleFlag = false;
            if(exitFlag)
            {
                gv.setExitflag(true);
            }
            getState();
            Sm.setState(st);

        }
        else
        {
            Inf = gv.givePlayerCoordinates();
            Inf2= gv.cheat();
            boolVals = gv.giveBooleans();
            visited = gv.giveVisited();
            extractBooleans(boolVals);
            st = Sm.GetState();
            setState();
        }
    }

    public void setState()
    {
        if(st == StateMachine.StateEnum.NoBow)
        {
            state = 0;
        }
        else if(st == StateMachine.StateEnum.NoArrow)
        {
            state = 1;

        }
        else if(st == StateMachine.StateEnum.Arrow)
        {
            state = 2;
        }
        else if(st == StateMachine.StateEnum.Lose)
        {
            state = 3;
        }
        else if(st == StateMachine.StateEnum.Win)
        {
            state = 4;
        }
    }

    public void getState()
    {
        if(state==0)
        {
            st = StateMachine.StateEnum.NoBow;
        }
        else if(state==1)
        {
            st = StateMachine.StateEnum.NoArrow;
        }
        else if(state==2)
        {
            st = StateMachine.StateEnum.Arrow;
        }
        else if(state==3)
        {
            st = StateMachine.StateEnum.Lose;
        }
        else if(state==4)
        {
            st = StateMachine.StateEnum.Win;
        }


    }

    public void loseDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Game Lost");
        //String sc = "Score: " + scoreVal;
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void WinDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Game Won!");
        String sc = "Score: " + scoreVal;
        builder.setMessage(sc);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void disableEnableArrow(Boolean b)
    {
        Arrowbuttonflag = b;
        aD.setEnabled(Arrowbuttonflag);
        aR.setEnabled(Arrowbuttonflag);
        aU.setEnabled(Arrowbuttonflag);
        aL.setEnabled(Arrowbuttonflag);
    }

    //////BUNDLE STUFF

    private static final String ARROWS = "arrows";
    private static final String SCORE = "scoreVal";
    private static final String BOW = "bow";
    private static final String INF = "Inf";
    private static final String INF2 = "Inf2";
    private static final String EXITFLAG = "exitFlag";
    private static final String ARROW1 = "arrow1";
    private static final String ARROW2 = "arrow2";
    private static final String ARROWBUTTONFLAG= "Arrowbuttonflag";
    private static final String VISITED = "visited";
    private static final String FIRSTTIME = "firstTime";
    private static final String WUMPUSSHOT = "WumpusShot";
    private static final String WUMPUSENCOUNTERED = "WumpusEncountered";
    private static final String PITENTERED = "pitEntered";
    private static final String ARROW1PICKED = "arrow1Picked";
    private static final String ARROW2PICKED = "arrow2Picked";
    private static final String DEFAULTPLAYER = "defaultPlayer";
    private static final String STATE = "state";
    private static final String DEFPLAYER = "defPlayer";
    private static final String BATHIT = "batHit";




    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        arrows = savedInstanceState.getInt(ARROWS);
        arrow1 = savedInstanceState.getInt(ARROW1);
        arrow2 = savedInstanceState.getInt(ARROW2);
        scoreVal = savedInstanceState.getInt(SCORE);
        bow = savedInstanceState.getString(BOW);
        Inf = savedInstanceState.getIntegerArrayList(INF);
        Inf2 = savedInstanceState.getIntegerArrayList(INF2);
        exitFlag = savedInstanceState.getBoolean(EXITFLAG);
        Arrowbuttonflag = savedInstanceState.getBoolean(ARROWBUTTONFLAG);
        visited = savedInstanceState.getIntegerArrayList(VISITED);
        firstTime = savedInstanceState.getBoolean(FIRSTTIME);
        WumpusShot = savedInstanceState.getBoolean(WUMPUSSHOT);
        WumpusEncountered = savedInstanceState.getBoolean(WUMPUSENCOUNTERED);
        pitEntered = savedInstanceState.getBoolean(PITENTERED);
        arrow1Picked = savedInstanceState.getBoolean(ARROW1PICKED);
        arrow2Picked = savedInstanceState.getBoolean(ARROW2PICKED);
        state = savedInstanceState.getInt(STATE);
        defaultPlayer = savedInstanceState.getBoolean(DEFAULTPLAYER);
        defPlayer = savedInstanceState.getInt(DEFPLAYER);
        batHit = savedInstanceState.getBoolean(BATHIT);

        setBundleFlag();
        miniupdateUI();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(ARROWS, arrows);
        savedInstanceState.putInt(SCORE, scoreVal);
        savedInstanceState.putString(BOW, bow);
        savedInstanceState.putIntegerArrayList(INF, Inf);
        savedInstanceState.putIntegerArrayList(INF2, Inf2);
        savedInstanceState.putBoolean(EXITFLAG, exitFlag);
        savedInstanceState.putInt(ARROW1, arrow1);
        savedInstanceState.putInt(ARROW2, arrow2);
        savedInstanceState.putBoolean(ARROWBUTTONFLAG, Arrowbuttonflag);
        savedInstanceState.putIntegerArrayList(VISITED, visited);
        savedInstanceState.putBoolean(WUMPUSSHOT, WumpusShot);
        savedInstanceState.putBoolean(FIRSTTIME, firstTime);
        savedInstanceState.putBoolean(WUMPUSENCOUNTERED, WumpusEncountered);
        savedInstanceState.putBoolean(PITENTERED, pitEntered);
        savedInstanceState.putBoolean(ARROW1PICKED, arrow1Picked);
        savedInstanceState.putBoolean(ARROW2PICKED, arrow2Picked);
        savedInstanceState.putInt(STATE, state);
        savedInstanceState.putBoolean(DEFAULTPLAYER, defaultPlayer);
        savedInstanceState.putInt(DEFPLAYER, defPlayer);
        savedInstanceState.putBoolean(BATHIT, batHit);
    }

}