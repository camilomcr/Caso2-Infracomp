import javax.xml.crypto.Data;

public class UpdateState extends Thread{
    private DataCalculator dataCalculator;

    public UpdateState(DataCalculator dataC){
        dataCalculator = dataC;
    }
    public void run(){
        while(true){
            if (!dataCalculator.ReferencesQueue.isEmpty()){
                
            }
        }

    }
}
