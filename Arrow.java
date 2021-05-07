package edu.sdsmt.soares_raiza;

public class Arrow extends State{
    public Arrow(MainActivity ma,GameView model, StateMachine machine) {
        super(ma, model, machine);

    }

    @Override
    public void exitActivity() {
        //nothing to do
        //model.setArrows(); //sets number of arrows to 0;
    }

    @Override
    public void entryActivity() {
    //enable buttons here?
        ma.disableEnableArrow(true); //GRADING: ENABLE


    }

    @Override
    public void doTask() {


        if(model.batRoomEntered())
        {
            ma.batEvent();
            machine.setState(StateMachine.StateEnum.NoBow);
        }

        else if(model.pitOrWump())
        {
            machine.setState(StateMachine.StateEnum.Lose);
        }

        else if(model.ArrowFound())
        {
            ma.setArrows();
        }

        else if(model.isExit())  // GRADING: EXIT
        {
            ma.setExitFlag(true);
            if(model.WumpusShotStatus())
            {
                machine.setState(StateMachine.StateEnum.Win);
            }
        }



    }
    public void helper()
    {
        if(model.batJustShot())
        {
            ma.batJustShot();
        }
        else if(model.IsArrowinPit())
        {
            ma.ArrowLandedInPit();
        }
        if(model.noMoreArrows())  // GRADING : EMPTY
        {
            machine.setState(StateMachine.StateEnum.NoArrow);
        }

    }

    @Override
    public void buttonPressed(String id) {

        if(id.equals("Aup"))
        {
            if(model.shootWumpus(0))
            {
                ma.shotWumpus();
            }
            helper();
        }
        else if(id.equals("Adown"))
        {
            if(model.shootWumpus(1))
            {
                ma.shotWumpus();
            }
            helper();
        }
        else if(id.equals("Aleft"))
        {
            if(model.shootWumpus(2))
            {
                ma.shotWumpus();
            }
            helper();
        }
        else if(id.equals("Aright"))
        {
            if(model.shootWumpus(3))
            {
                ma.shotWumpus();
            }
            helper();
        }


        else {

            doTask();
        }


    }
}
