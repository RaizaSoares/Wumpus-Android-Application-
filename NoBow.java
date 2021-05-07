package edu.sdsmt.soares_raiza;

public class NoBow extends State {
    public NoBow(MainActivity ma,GameView model, StateMachine machine) {
        super(ma, model, machine);
    }
    @Override
    public void exitActivity() {
        //nothing to do
    }

    @Override
    public void entryActivity() {
    //remove arrows and deactivate buttons
        ma.disableEnableArrow(false);


    }

    @Override
    public void doTask() {
        if(model.bothArrowsShot())
        {
            machine.setState(StateMachine.StateEnum.Lose);
        }
        if(model.bowStatus()) // GRADING: BOW
        {
            ma.setBow("yes");
            machine.setState(StateMachine.StateEnum.NoArrow);
        }
        else if(model.pitOrWump())
        {
            machine.setState(StateMachine.StateEnum.Lose);
        }

        else if(model.batRoomEntered())  //GRADING: BAT
        {
            model.displacePlayer();
            ma.bat();
        }
        else if(model.isExit())
        {
            ma.setExitFlag(true);
            if(model.WumpusShotStatus())
            {
                machine.setState(StateMachine.StateEnum.Win);
            }
        }


    }

    @Override
    public void buttonPressed(String id) {
        doTask();


    }
}
