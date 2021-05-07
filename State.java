package edu.sdsmt.soares_raiza;

public class State {
    protected GameView model;
    protected StateMachine machine;
    protected MainActivity ma;
    public State( MainActivity ma, GameView model, StateMachine machine){
        this.model = model;
        this.machine = machine;
        this.ma = ma;
    }

    public void exitActivity() {

    }

    public void entryActivity() {

    }

    public void doTask() {

    }

    public void buttonPressed(String id) {

    }


}
