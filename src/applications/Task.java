package applications;

// top-level nested classes
class Task {
    // data members
    private int machine;
    private int time;

    // constructor
    public Task(int theMachine, int theTime) {
        machine = theMachine;
        time = theTime;
    }

    int getMachine() {
        return machine;
    }

    int getTime() {
        return time;
    }

}