import java.util.concurrent.Semaphore;

public class BaboonSolutionOne implements Runnable{

    private final int id, crossingTime;
    private boolean isOnRight = true, keepCrossing = true;
    private Semaphore ropeGoingRight, ropeGoingLeft, mutex;
    private CanyonManagerSolutionOne canyonManager;
    private int crossingAmount;

    BaboonSolutionOne(int id, boolean isOnRight, Semaphore ropeGoingRight, Semaphore ropeGoingLeft, Semaphore mutex, CanyonManagerSolutionOne canyonManager, int crossingTime, int crossingAmount) {
        this.id = id;
        this.isOnRight = Math.random() < 0.5;
        this.ropeGoingRight = ropeGoingRight;
        this.ropeGoingLeft = ropeGoingLeft;
        this.mutex = mutex;
        this.canyonManager = canyonManager;
        this.crossingTime = crossingTime;
        this.crossingAmount = crossingAmount;
        if(crossingAmount > 0) {
            keepCrossing = false;
        }
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "!! Baboon "+id+" is wanting to cross to the "+(isOnRight ? "right" : "left")+" side||\n";
    }
    public String doneTravelingString() {
        return ">>> Baboon "+id+" is done crossing to the "+(isOnRight ? "right" : "left")+" side, it took "+(crossingTime/1000)+" seconds to cross. <<<\n";
    }

    @Override
    public void run() {
        while(crossingAmount>0 || keepCrossing) {
            isOnRight = !isOnRight;

            try {
                if(isOnRight == true) {
                    mutex.acquire();
                    canyonManager.updateNumberOnSide(isOnRight, 1);
    
                    if(canyonManager.getNumberCrossingRight() == 0) {
                        ropeGoingLeft.acquire();
                    }
                    canyonManager.updateNumberCrossingLeft(1);
                    mutex.release();
    
                    System.out.println(toString());
                    //System.out.println(canyonManager.getNumberCrossing()+" baboon on the rope!");
                    Thread.sleep(crossingTime);
                    System.out.println(doneTravelingString());
    
                    mutex.acquire();
                    canyonManager.updateNumberCrossingLeft(-1);
                    canyonManager.updateNumberOnSide(isOnRight, -1);
    
                    System.out.println("-... "+canyonManager.getNumberOnLeft()+" baboon on left and "+canyonManager.getNumberOnRight()+" on the right ...-\n");
    
                    if(canyonManager.getNumberCrossingLeft() == 0) {
                        System.out.println(">> "+canyonManager.getNumberCrossingLeft()+" baboon crossing left and "+canyonManager.getNumberCrossingRight()+" crossing to the right! <<\n");
                        ropeGoingLeft.release();
                    }
                    mutex.release();
                    crossingAmount--;
                }
                else if(isOnRight == false) {
                    mutex.acquire();
                    canyonManager.updateNumberOnSide(isOnRight, 1);
    
                    if(canyonManager.getNumberCrossingLeft() == 0) {
                        ropeGoingRight.acquire();
                    }
                    canyonManager.updateNumberCrossingRight(1);
                    mutex.release();
    
                    System.out.println(toString());
                    //System.out.println(canyonManager.getNumberCrossing()+" baboon on the rope!");
                    Thread.sleep(crossingTime);
                    System.out.println(doneTravelingString());
    
                    mutex.acquire();
                    canyonManager.updateNumberCrossingRight(-1);
                    canyonManager.updateNumberOnSide(isOnRight, -1);
    
                    System.out.println("-... "+canyonManager.getNumberOnLeft()+" baboon on left and "+canyonManager.getNumberOnRight()+" on the right ...-\n");
    
                    if(canyonManager.getNumberCrossingRight() == 0) {
                        System.out.println(">> "+canyonManager.getNumberCrossingLeft()+" baboon crossing left and "+canyonManager.getNumberCrossingRight()+" crossing to the right! <<\n");
                        ropeGoingLeft.release();
                    }
                    mutex.release();
                    crossingAmount--;
                }
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("*** Baboon "+id+" is done crossing and is now on "+(isOnRight ? "right" : "left")+" side ***");
        canyonManager.updateNumberOnSide(isOnRight, 1);
        System.out.println(canyonManager.getNumberOnLeft()+" baboon on left and "+canyonManager.getNumberOnRight()+" on the right!\n");
    }
}
