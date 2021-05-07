package edu.sdsmt.soares_raiza;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class GameView extends View {
    private Paint CircPaint;
    private Paint col;
    private Paint w;
    private int Playerx= 100;
    private int Playery = 40;
    private int playerType= 0;
    boolean flag= false;
    boolean exitflag = false;
    private int WumpX= -1;
    private int WumpY= -1;

    private int bowX= -1;
    private int bowY= -1;

    private int batX = -1;
    private int batY = -1;

    private int a1X = -1;
    private int a1Y = -1;

    private int a2X = -1;
    private int a2Y = -1;

    private int exitX = -1;
    private int exitY = -1;

    private int pitX = -1;
    private int pitY = -1;

    private int xStart = 100;
    private int yStart = 40;
    private int constrX = 340;
    private int constrY = 280;
    private int radius = 40;
    private ArrayList<Integer> Random = new ArrayList<Integer>();
    //private Boolean arrowFound = false;
    private int arrows=0;
    private Boolean noBow= true;
    private int  arrow1= 0;
    private int arrow2 = 0;
    private ArrayList<Integer> visited = new ArrayList<Integer>(Collections.nCopies(16, 0));
    private Boolean pitEntered= false;
    private Boolean WumpusEncountered = false;
    private Boolean WumpusShot = false;
    private Boolean arrow1Picked = false;
    private Boolean arrow2Picked = false;
    private Boolean firstTime = true;
    private Boolean defaultPlayer = true;
    private Boolean batJustShot = false;
    private int defPlayer = 0;
    private int xCord = 0;
    private int yCord = 0;

    private Boolean batHit = false;
    private Boolean arrowLandedinpit = false;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context)
    {
        Random.add(0);
        Random.add(0);
        CircPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        col = new Paint(Paint.ANTI_ALIAS_FLAG);
        col.setColor(Color.BLUE);
        w = new Paint(Paint.ANTI_ALIAS_FLAG);
        w.setColor(Color.GREEN);
        //addTovisited();

    }

    private void randomNums()
    {
        boolean flag = true;
        int x=0;
        int y=0;
        while(flag) {
            int max = 4;
            int min = 0;
            x = (int) ((Math.random() * (max - min)) + min);
            y = (int) ((Math.random() * (max - min)) + min);
            flag= false;
            for(int i=0; i<Random.size()-1; i=i+2)
            {
                if(x== Random.get(i) && y== Random.get(i + 1))
                {
                    flag= true;
                }
            }
        }
        Random.add(x);
        Random.add(y);
    }


    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        drawMap(c);
        colorVisited(c);
        if(exitflag)
        {
            colorExit(c);
        }
        if(defPlayer==0) {
            drawDefaultPlayer(c, xCord, yCord);
        }
        else if(defPlayer==1)
        {
            drawRectPlayer(c, xCord, yCord);
        }
        else if(defPlayer ==2)
        {
            drawTri(c, xCord, yCord);
        }

        if(!flag)
        {
            setOtherLocations(c);
        }

    }
    public void colorVisited(Canvas c)
    {
        w.setColor(Color.LTGRAY);
        int xt=0;
        int yt=0;
        for(int i=0; i<visited.size(); i++)
        {
            if(visited.get(i) == 1)
            {
                yt = i%4;
                xt = i/4;
                xt=  xStart + xt*2*radius ;
                yt = yStart+ yt * 2* radius;
                c.drawCircle(xt, yt, radius, w);
            }
        }
    }

    public void drawMap(Canvas c)
    {
        //row 0
        //if(getWidth() < getHeight()) {
            //radius = getWidth() / 8;
        //}
        //else
        //{
            double r = getHeight()/9;
            radius = (int)r;
            //radius = getHeight()/7;
        //}
        xStart = getWidth() / 2 - 3*radius;
        yStart = radius;
        constrX = xStart + 6*radius;
        constrY = yStart + 6*radius;
        if(firstTime)
        {
            Playerx = xStart;
            Playery = yStart;

            xCord = (Playerx - xStart)/(2*radius);
            yCord = (Playery- yStart)/(2*radius);

            addTovisited();
            firstTime = false;
        }

        //row 0
        c.drawCircle(xStart, yStart, radius, CircPaint );
        c.drawCircle(xStart + 2*radius, yStart, radius, CircPaint);
        c.drawCircle(xStart + 4*radius, yStart, radius, CircPaint);
        c.drawCircle(xStart + 6*radius, yStart, radius, CircPaint);

        //row 1
        c.drawCircle(xStart, yStart + 2*radius, radius, CircPaint );
        c.drawCircle(xStart + 2*radius, yStart + 2*radius, radius, CircPaint);
        c.drawCircle(xStart + 4*radius, yStart + 2*radius, radius, CircPaint);
        c.drawCircle(xStart + 6*radius, yStart + 2*radius, radius, CircPaint);

        //row 2
        c.drawCircle(xStart, yStart + 4*radius, radius, CircPaint );
        c.drawCircle(xStart + 2*radius, yStart + 4*radius, radius, CircPaint);
        c.drawCircle(xStart + 4*radius, yStart + 4*radius, radius, CircPaint);
        c.drawCircle(xStart + 6*radius, yStart + 4*radius, radius, CircPaint);

        //row 1
        c.drawCircle(xStart, yStart + 6*radius, radius, CircPaint );
        c.drawCircle(xStart + 2*radius, yStart + 6*radius, radius, CircPaint);
        c.drawCircle(xStart + 4*radius, yStart + 6*radius, radius, CircPaint);
        c.drawCircle(xStart + 6*radius, yStart + 6*radius, radius, CircPaint);


    }
    public void drawDefaultPlayer(Canvas c, int x, int y)
    {
        Playerx=  xStart + x*2*radius ;
        Playery = yStart+ y* 2* radius;
        c.drawCircle(Playerx, Playery, radius/2, col);
    }

    public void drawRectPlayer(Canvas c, int x, int y)
    {
        Playerx=  xStart + x*2*radius ;
        Playery = yStart+ y* 2* radius;
        int x1 = Playerx- (radius/2);
        int x2 = Playerx + (radius/2);
        int y1 = Playery - (radius/2);
        int y2 = Playery + (radius/2);
        c.drawRect(x1, y1, x2, y2, col );

    }
    public void drawTri(Canvas c, int x, int y)
    {
        Playerx=  xStart + x*2*radius ;
        Playery = yStart+ y* 2* radius;
        drawTriangle(c, col, Playerx, Playery, radius);
    }
    public void drawTriangle(Canvas c, Paint paint, int x, int y, int width) {
        int hW = width / 2;

        Path pT = new Path();
        pT.moveTo(x, y - hW); // Top
        pT.lineTo(x - hW, y + hW); // Bottom left
        pT.lineTo(x + hW, y + hW); // Bottom right
        pT.lineTo(x, y - hW); // Back to Top
        pT.close();

        c.drawPath(pT, paint);
    }

    public void getPlayerCoordinates(int x, int y)
    {
        Playerx = x;
        Playery = y;
        addTovisited();

    }

    public ArrayList<Integer> givePlayerCoordinates()
    {
        ArrayList<Integer> Info = new ArrayList<Integer>();
        Info.add(0, Playerx);
        Info.add(1, Playery);
        Info.add(2, constrX);
        Info.add(3, constrY);
        Info.add(4, xStart);
        Info.add(5, yStart);
        Info.add(6, radius);
        Info.add(7, defPlayer);
        xCord = (Playerx - xStart)/(2*radius);
        yCord = (Playery- yStart)/(2*radius);
        Info.add(8, xCord);
        Info.add(9, yCord);


        return Info;
    }
    public void getPlayerInfo(ArrayList<Integer> Inf)
    {
        constrX = Inf.get(2);
        constrY = Inf.get(3);
        xStart = Inf.get(4);
        yStart = Inf.get(5);
        radius = Inf.get(6);
        defPlayer = Inf.get(7);
        xCord = Inf.get(8);
        yCord = Inf.get(9);
    }

    public void addTovisited()
    {
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        int ind = (xt * 4) + yt;
        visited.set(ind, 1);
    }


    public void setOtherLocations(Canvas c)
    {
        flag= true;
        //wumpus
        w.setColor(Color.GREEN);
        randomNums();
        WumpX = xStart + Random.get(Random.size()-2) *2*radius ;
        WumpY = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(WumpX, WumpY, 20, w);

       //bow and quiver
        w.setColor(Color.YELLOW);
        randomNums();
        bowX = xStart + Random.get(Random.size()-2) *2*radius ;
        bowY = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(bowX, bowY, 20, w);

        //bat
        w.setColor(Color.WHITE);
        randomNums();
        batX = xStart + Random.get(Random.size()-2) *2*radius ;
        batY = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(batX, batY, 20, w);

        //arrow1
        w.setColor(Color.GRAY);
        randomNums();
        a1X = xStart + Random.get(Random.size()-2) *2*radius ;
        a1Y = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(a1X, a1Y, 20, w);

        //arrow2
        w.setColor(Color.CYAN);
        randomNums();
        a2X = xStart + Random.get(Random.size()-2) *2*radius ;
        a2Y = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(a2X, a2Y, 20, w);

        //exit
        w.setColor(Color.RED);
        randomNums();
        exitX = xStart + Random.get(Random.size()-2) *2*radius ;
        exitY = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(exitX, exitY, 20, w);

        //pit
        w.setColor(Color.DKGRAY);
        randomNums();
        pitX = xStart + Random.get(Random.size()-2) *2*radius ;
        pitY = yStart+ Random.get(Random.size()-1) * 2* radius;
        //c.drawCircle(pitX, pitY, 20, w);


    }
    public void setLocationFlag(boolean b)
    {
        flag = b;
    }

    public void colorExit(Canvas c)
    {
        w.setColor(Color.RED);
        int x = xStart + Random.get(12)*2*radius;
        int y = yStart + Random.get(13)*2*radius;
        c.drawCircle(x,y , radius, w);
    }
    public void setExitflag(boolean b)
    {
        exitflag = b;
    }

    public void reset()
    {
        Random.clear();
        Random.add(0);
        Random.add(0);
        visited = new ArrayList<Integer>(Collections.nCopies(16, 0));
        visited.set(0, 1);
        arrow1 =0;
        arrow2 = 0;
        arrow2Picked = false;
        arrow1Picked = false;
        WumpusEncountered = false;
        WumpusShot = false;
        arrowLandedinpit= false;
        batJustShot = false;
        batHit = false;
        pitEntered = false;
        flag= false;
    }

    public ArrayList<Integer> cheat()
    {
        return Random;
    }

    public void getRandom(ArrayList<Integer> rd){
        Random = rd;
    }

    //visited
    public ArrayList<Integer> giveVisited()
    {
        return visited;
    }

    public void getvisited(ArrayList<Integer> vis){
        visited = vis;
    }


    public Boolean ArrowFound(){
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        if(xt == Random.get(8) && yt == Random.get(9))
        {
            if(!arrow1Picked) {
                arrow1 = 1;
                arrow1Picked = true;
                return true;
            }
        }
        else if(xt == Random.get(10) && yt == Random.get(11))
        {
            if(!arrow2Picked) {
                arrow2Picked = true;
                arrow2 = 1;
                return true;
            }
        }

        return false;
    }
    public int getArrow1Count()
    {
      return arrow1;
    }
    public int getArrow2Count()
    {
        return arrow2;
    }

    public void removeLastRandom()
    {
        Random.remove(Random.size() - 1);
        Random.remove(Random.size() - 1);
    }
    public void batEvent()
    {
        //
        //
        randomNums();
        Random.set(0, Random.get(Random.size() - 2));
        Random.set(1, Random.get(Random.size() - 1));
        removeLastRandom();
        Playerx = xStart +Random.get(0)*2*radius;
        Playery = yStart + Random.get(1)*2*radius;
        addTovisited();
        //bow
        randomNums();
        Random.set(4, Random.get(Random.size() - 2));
        Random.set(5, Random.get(Random.size() - 1));
        removeLastRandom();
        if(arrow1 == 1)
        {
            randomNums();
            Random.set(8, Random.get(Random.size() - 2));
            Random.set(9, Random.get(Random.size() - 1));
            removeLastRandom();
            arrow1=0;
            arrow1Picked = false;
        }
        if(arrow2 == 1)
        {
            randomNums();
            Random.set(10, Random.get(Random.size() - 2));
            Random.set(11, Random.get(Random.size() - 1));
            removeLastRandom();
            arrow2=0;
            arrow2Picked = false;
        }


    }
    public Boolean batRoomEntered()
    {
        if(batHit)
        {
            return false;
        }
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        if(xt == Random.get(6) && yt ==Random.get(7))
        {
            return true;
        }
        return false;
    }
    public void setArrows(int a1, int a2)
    {
        arrow1= a1;
        arrow2 = a2;
    }
    public boolean bowStatus()
    {
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        if(xt == Random.get(4) && yt == Random.get(5))
        {
            noBow = false;
            return true;
        }
        return false;
    }
    public Boolean pitOrWump()
    {
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        if((xt == Random.get(14) && yt == Random.get(15)))
        {
            pitEntered = true;
            return true;
        }
        else if((xt == Random.get(2) && yt == Random.get(3)))
        {
            if(!WumpusShot) {
                WumpusEncountered = true;
                return true;
            }
        }
        return false;
    }
    public Boolean pitent()
    {
        return pitEntered;
    }
    public Boolean wumpusEnc()
    {
        return WumpusEncountered;
    }

    public Boolean shootWumpus(int num)  //0 <- up, 1<- down, 2<- left, 3 <- right
    {

        int xtemp = Playerx;
        int ytemp = Playery;
        int xtemp2 = Playerx;
        int ytemp2 = Playery;

        if(num==0)
        {
           ytemp = up(ytemp);
           ytemp2 = up(ytemp);

        }
        else if(num==1)
        {
            ytemp = down(ytemp);
            ytemp2 = down(ytemp);
        }
        else if(num==2)
        {
            xtemp = left(xtemp);
            xtemp2 = left(xtemp);
        }
        else if(num==3)
        {
            xtemp = right(xtemp);
            xtemp2 = right(xtemp);
        }

        if(checkWumpusShot(xtemp, ytemp) || checkWumpusShot(xtemp2, ytemp2))
        {
            WumpusShot = true;
            return true;
        }
        else if(checkBatShot(xtemp ,ytemp) || checkBatShot(xtemp2, ytemp2) )
        {
            batHit = true;
            batJustShot = true;
        }

        else if(arrowInPit(xtemp ,ytemp) || arrowInPit(xtemp2, ytemp2) )
        {
            arrowLandedinpit = true;
        }
        else
        {
            placeArrow(xtemp2, ytemp2); // place arrow in the second room.
        }
        return false;
    }

    public Boolean batJustShot()
    {
        if(batJustShot)
        {
            batJustShot = false;
            return true;
        }
        return false;
    }
    public Boolean IsArrowinPit()
    {
        if(arrowLandedinpit)
        {
            arrowLandedinpit=false;
            return true;
        }
        return false;
    }
    public void placeArrow(int xtemp2, int ytemp2)
    {
        int xt = (xtemp2 - xStart)/(2*radius);
        int yt = (ytemp2- yStart)/(2*radius);
        if(!(xt == Random.get(12) && yt == Random.get(13))) {
            if (arrow1 == 1) {
                if((!arrow2Picked) && (!(xt == Random.get(10) && yt == Random.get(11)))) {
                    Random.set(8, xt);
                    Random.set(9, yt);
                }
                else
                {
                    randomNums();
                    Random.set(8, Random.get(Random.size() - 2));
                    Random.set(9, Random.get(Random.size() - 1));
                    removeLastRandom();
                }
                arrow1 = 0;
                arrow1Picked = false;
            } else {
                if((!arrow1Picked) && (!(xt == Random.get(8) && yt == Random.get(9)))) {
                    Random.set(10, xt);
                    Random.set(11, yt);
                }
                else
                {
                    randomNums();
                    Random.set(10, Random.get(Random.size() - 2));
                    Random.set(11, Random.get(Random.size() - 1));
                    removeLastRandom();
                }
                arrow2 = 0;
                arrow2Picked = false;
            }
        }
        else
        {
            if(arrow1 ==1)
            {
                arrow1 =0;
            }
            else
            {
                arrow2=0;
            }
        }

    }
    public void displacePlayer()
    {
        randomNums();
        Random.set(0, Random.get(Random.size() - 2));
        Random.set(1, Random.get(Random.size() - 1));
        removeLastRandom();
        Playerx = xStart +Random.get(0)*2*radius;
        Playery = yStart + Random.get(1)*2*radius;
        addTovisited();
    }

    public Boolean arrowInPit(int xtemp, int ytemp)
    {
        int xt = (xtemp - xStart)/(2*radius);
        int yt = (ytemp- yStart)/(2*radius);
        if((xt == Random.get(14) && yt == Random.get(15)))
        {
            if(arrow1 ==1)
            {
                arrow1 =0;
            }
            else
            {
                arrow2=0;
            }
            return true;

        }
        return false;

    }



    public Boolean checkWumpusShot(int xtemp, int ytemp)
    {
        int xt = (xtemp - xStart)/(2*radius);
        int yt = (ytemp- yStart)/(2*radius);
        if((xt == Random.get(2) && yt == Random.get(3)))
        {
            if(arrow1 ==1)
            {
                arrow1 =0;
            }
            else
            {
                arrow2=0;
            }

            return true;
        }
        return false;
    }

    public Boolean checkBatShot(int xtemp, int ytemp)
    {
        int xt = (xtemp - xStart)/(2*radius);
        int yt = (ytemp- yStart)/(2*radius);
        if((xt == Random.get(6) && yt == Random.get(7)))
        {
            if(arrow1 ==1)
            {
                arrow1 =0;
            }
            else
            {
                arrow2=0;
            }

            return true;
        }
        return false;
    }



    public int left(int x)
    {
        if(x - 2*radius < xStart)
        {
            x = constrX;
        }
        else{
            x = x - 2*radius;
        }
        return x;
    }

    public int right(int x)
    {
        if(x + 2*radius > constrX)
        {
            x = xStart;
        }
        else{
            x = x + 2*radius;
        }
        return x;
    }
    public int up(int y)
    {
        if(y - 2*radius < yStart)
        {
            y = constrY;
        }
        else{
            y = y - 2*radius;
        }
        return y;
    }
    public int down(int y)
    {
        if(y + 2*radius > constrY)
        {
            y = yStart;
        }
        else{
            y = y + 2*radius;
        }
        return y;
    }

    public Boolean isExit()
    {
        int xt = (Playerx - xStart)/(2*radius);
        int yt = (Playery- yStart)/(2*radius);
        if((xt == Random.get(12) && yt == Random.get(13)))
        {
            setExitflag(true);
            return true;
        }
        return false;
    }
    public Boolean WumpusShotStatus()
    {
        return WumpusShot;
    }
    public Boolean noMoreArrows()
    {
        if(arrow1 ==0 && arrow2 == 0)
        {
            return true;
        }
        return false;
    }

    public Boolean bothArrowsShot()
    {
        return arrow1Picked & arrow2Picked;
    }

    public ArrayList<Boolean> giveBooleans()
    {
        ArrayList<Boolean> boolVals = new ArrayList<Boolean>();

        boolVals.add(firstTime);
        boolVals.add(WumpusShot);
        boolVals.add(WumpusEncountered);
        boolVals.add(pitEntered);
        boolVals.add(arrow1Picked);
        boolVals.add(arrow2Picked);
        boolVals.add(defaultPlayer);
        boolVals.add(batHit);

        return boolVals;
    }

    public void getBooleans(ArrayList<Boolean> boolVals)
    {
        firstTime = boolVals.get(0);
        WumpusShot = boolVals.get(1);
        WumpusEncountered = boolVals.get(2);
        pitEntered = boolVals.get(3);
        arrow1Picked = boolVals.get(4);
        arrow2Picked = boolVals.get(5);
        defaultPlayer = boolVals.get(6);
        batHit = boolVals.get(7);
    }
    public void changeDefaultPlayer(Boolean b, int type)
    {
        defaultPlayer = b;
        defPlayer = type;
    }


}
