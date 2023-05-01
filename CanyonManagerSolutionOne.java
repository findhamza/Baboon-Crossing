import java.util.Random;
import java.util.concurrent.Semaphore;

public class CanyonManagerSolutionOne {
    
    private final int numberOfBaboon;
    private final Semaphore ropeGoingRight, ropeGoingLeft;
    private final Semaphore mutex;
    private int numberCrossingRight, numberCrossingLeft, numberOnLeft, numberOnRight, crossingAmount;

    public CanyonManagerSolutionOne(int numberOfBaboon, int crossingAmount) {
        this.numberOfBaboon = numberOfBaboon;
        this.ropeGoingRight = new Semaphore(numberOfBaboon);
        this.ropeGoingLeft = new Semaphore(numberOfBaboon);
        this.mutex = new Semaphore(1);
        this.numberCrossingRight = 0;
        this.numberCrossingLeft = 0;
        this.numberOnLeft = 0;
        this.numberOnRight = 0;
        this.crossingAmount = crossingAmount;
    }

    public int getNumberOfBaboon() {
        return numberOfBaboon;
    }
    public Semaphore getRopeGoingRight() {
        return ropeGoingRight;
    }
    public Semaphore getRopeGoingLeft() {
        return ropeGoingLeft;
    }
    public Semaphore getMutex() {
        return mutex;
    }
    public int getNumberCrossingRight() {
        return numberCrossingRight;
    }
    public int getNumberCrossingLeft() {
        return numberCrossingLeft;
    }
    public int getNumberOnLeft() {
        return numberOnLeft;
    }
    public int getNumberOnRight() {
        return numberOnRight;
    }
    public int getCrossingAmount() {
        return crossingAmount;
    }

    public void updateNumberOnSide(boolean isOnRight, int i) {
        if(isOnRight) {
            numberOnRight += i;
        }
        else {
            numberOnLeft += i;
        }
    }

    public void updateNumberCrossingRight(int i) {
        numberCrossingRight += i;
    }
    public void updateNumberCrossingLeft(int i) {
        numberCrossingLeft += i;
    }

    public void startCanyonManager() throws InterruptedException {
        
        Thread[] baboonClan = new Thread[numberOfBaboon];
        Random random = new Random();

        for(int i=0; i < numberOfBaboon; i++) {
            baboonClan[i] = new Thread(new BaboonSolutionOne(i, true, 
                                        this.getRopeGoingRight(), this.getRopeGoingLeft(), this.getMutex(), this, 
                                        random.nextInt(9001)+1000,
                                        this.getCrossingAmount()));

            baboonClan[i].start();
        }
        
        for(Thread baboon: baboonClan) {
            baboon.join();
        }

    }

}
