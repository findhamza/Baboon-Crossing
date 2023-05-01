
public class BaboonSolutionTwo implements Runnable {

    private final int id, crossingTime;
    private int crossingAmount;
    private boolean isOnRight, keepCrossing;
    private CanyonManagerSolutionTwo canyonManager;

    public BaboonSolutionTwo(int id, boolean isOnRight, CanyonManagerSolutionTwo canyonManagerSolutionTwo, int crossingAmount, int crossingTime) {
        this.id = id;
        this.isOnRight = isOnRight;
        this.canyonManager = canyonManagerSolutionTwo;
        this.crossingAmount = crossingAmount;
        this.crossingTime = crossingTime;
        if(crossingAmount == 0) {
            keepCrossing = true;
        }
    }

    @Override
    public void run() {
        while(crossingAmount > 0 || keepCrossing) {
            isOnRight = !isOnRight;

            try {
                canyonManager.waitForRopeToClear(isOnRight);
                canyonManager.updateNumberOnSide(isOnRight, -1);
                canyonManager.updateNumberCrossing(isOnRight, 1);

                System.out.println("!! Baboon "+id+" is wanting to cross to the "+(isOnRight ? "right" : "left")+" side||\n");
                Thread.sleep(crossingTime);
                System.out.println(">>> Baboon "+id+" is done crossing to the "+(isOnRight ? "right" : "left")+" side, it took "+(crossingTime/1000)+" seconds to cross. <<<\n");
            
                canyonManager.updateNumberOnSide(!isOnRight, 1);
                canyonManager.updateNumberCrossing(isOnRight, -1);
                canyonManager.notifyCrossingDone();
                
                crossingAmount--;
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("*** Baboon "+id+" is done crossing and is now on "+(isOnRight ? "right" : "left")+" side ***");
        canyonManager.updateNumberOnSide(isOnRight, 1);
    }

}
