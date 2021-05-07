package edu.sdsmt.soares_raiza;




public class StateMachine {
    public enum StateEnum {Arrow, NoBow, NoArrow, Lose, Win}

    private StateEnum state = StateEnum.NoBow;
    private State[] stateArray = null;

    StateMachine( GameView model, MainActivity ma ) {

        stateArray = new State[]{
                new Arrow( ma, model, this ),
                new NoBow(ma,  model, this ),
                new NoArrow(ma, model, this ),
                new Lose(ma, model, this),
                new Win(ma, model, this),
        };


    }

    /**
     * core part of the state machine
     *
     * @param state
     */
    public void setState( StateEnum state ) {
        /*
         * Exit activities
         */
        stateArray[state.ordinal()].exitActivity( );

        /*
         * New state
         */
        this.state = state;


        //forward on the state to anything nested state that needs it
        //I could have make a second state machine entirely for the diagram

        /*
         * Entry activites
         */
        stateArray[state.ordinal()].entryActivity( );
    }

    /**
     * A button press cause a state change
     */
    public void buttonPressed(String id) {
        //forward it on for the state to decide
        stateArray[state.ordinal()].buttonPressed( id);

    }


    /**
     * \brief refresh if the model has been updated
     */


    StateEnum GetState() {
        return state;
    }

}
