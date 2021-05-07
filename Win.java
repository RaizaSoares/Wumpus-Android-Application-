package edu.sdsmt.soares_raiza;

public class Win extends State {
    public Win(MainActivity ma,GameView model, StateMachine machine) {
        super(ma, model, machine);
    }
    public void exitActivity() {

    }

    public void entryActivity() {
        ma.WinDialog();
    }  // GRADING: DIALOG

    public void doTask() {

    }

    public void buttonPressed(String id) {

    }
}
