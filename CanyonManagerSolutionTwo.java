import java.util.Random;

public class CanyonManagerSolutionTwo {

    private int numberOnLeft, numberOnRight, 
        numberCrossingLeft, numberCrossingRight,
        numberOfBaboon, crossingAmount;

    public CanyonManagerSolutionTwo(int numberOfBaboon, int crossingAmount) {
        this.numberOfBaboon = numberOfBaboon;
        this.crossingAmount = crossingAmount;
    }

    public int getNumberOnLeft() {
        return numberOnLeft;
    }

    public int getNumberOnRight() {
        return numberOnRight;
    }

    public void updateNumberCrossing(boolean isOnRight, int i) {
        if(isOnRight)
            numberOnRight += i;
        else
            numberOnLeft += i;
    }

    public void updateNumberOnSide(boolean isGoingLeft, int i) {
        if(isGoingLeft)
            numberCrossingLeft += i;
        else
            numberCrossingRight += i;
    }

    public synchronized void waitForRopeToClear(boolean isGoingRight) throws InterruptedException {
        if(isGoingRight) {
            while(numberCrossingRight > 0) {
                wait();
            }
        }
        else {
            while(numberCrossingLeft > 0) {
                wait();
            }
        }
    }

    public synchronized void notifyCrossingDone() {
        notifyAll();
    }

    public void startCanyonManagerSolutionTwo() throws InterruptedException {
        Thread[] baboonClan = new Thread[numberOfBaboon];
        Random random = new Random();

        for(int i=0; i < numberOfBaboon; i++) {
            baboonClan[i] = new Thread(new BaboonSolutionTwo(i, random.nextBoolean(), this,
                                        crossingAmount, random.nextInt(9001)+1000));

            baboonClan[i].start();
        }
        
        for(Thread baboon: baboonClan) {
            baboon.join();
        }
    }
}
