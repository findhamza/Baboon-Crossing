import java.io.Console;

class BaboonMain {

    public static void main(String[] args) {

        int soulutionToRun = Integer.parseInt(getInput("Which Solution do you want to run? ('1' for solution one, '2' for solution two)\n"));
        if(soulutionToRun == 1)
            solutionOne();
        else if(soulutionToRun == 2)
            solutionTwo();
    }

    private static String getInput(String question) {
        Console console = System.console();
        String inputString = console.readLine(question);

        return inputString;
    }

    public static void solutionOne() {
        int baboonClanCount = Integer.parseInt(getInput("Number of baboons in the baboon clan?\n"));
        int crossingAmount = Integer.parseInt(getInput("How many times should they cross? (0 = infinite times)\n"));

        CanyonManagerSolutionOne canyonManager = new CanyonManagerSolutionOne(baboonClanCount, crossingAmount);
        try {
            canyonManager.startCanyonManager();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void solutionTwo() {
        int baboonClanCount = Integer.parseInt(getInput("Number of baboons in the baboon clan?\n"));
        int crossingAmount = Integer.parseInt(getInput("How many times should they cross? (0 = infinite times)\n"));

        CanyonManagerSolutionTwo canyonManager = new CanyonManagerSolutionTwo(baboonClanCount, crossingAmount);
        try {
            canyonManager.startCanyonManagerSolutionTwo();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}