package edu.sdsmt.soares_raiza;

public class NoArrow extends State{
    public NoArrow(MainActivity ma,GameView model, StateMachine machine) {
        super(ma, model, machine);
    }
    @Override
    public void exitActivity() {
        //nothing to do
    }

    @Override
    public void entryActivity() {


        ma.disableEnableArrow(false);   // GRADING: DISABLE
        if(model.bothArrowsShot())
        {
            machine.setState(StateMachine.StateEnum.Lose);
        }

    }

    @Override
    public void doTask() {
        if(model.bothArrowsShot())
        {
            machine.setState(StateMachine.StateEnum.Lose);
        }
        if(model.batRoomEntered())
        {
            ma.batEvent();
            machine.setState(StateMachine.StateEnum.NoBow);
        }

        else if(model.ArrowFound())
        {
            ma.setArrows();
            machine.setState(StateMachine.StateEnum.Arrow);
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
