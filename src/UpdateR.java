public class UpdateR extends Thread{
    private DataCalculator dataCalculator;
    private int TP;

    public UpdateR(DataCalculator dataC){
        dataCalculator = dataC;
    }
    public void run(){
        while(true){
            dataCalculator.setBitR(true, 0, 0);
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
