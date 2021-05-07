package edu.sdsmt.soares_raiza;

public class Lose extends State{
    public Lose(MainActivity ma,GameView model, StateMachine machine) {
        super(ma, model, machine);
    }
    @Override
    public void exitActivity() {

    }
    @Override
    public void entryActivity() {
        String death = ma.causeOfDeath();  // GRADING: DIALOG
        ma.loseDialog(death);
    }
    @Override
    public void doTask() {


    }
    @Override
    public void buttonPressed(String id) {

    }
}
