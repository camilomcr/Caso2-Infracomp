import javax.xml.crypto.Data;

public class UpdateState extends Thread{
    private DataCalculator dataCalculator;

    public UpdateState(DataCalculator dataC){
        dataCalculator = dataC;
    }
    public void run(){
        while(true){

            if (!(dataCalculator.ReferencesQueue.peek()==null)){
                System.out.println("Test");
                String data = dataCalculator.ReferencesQueue.poll();
                String[] splitLine = data.split(",");
                if (dataCalculator.PageTable.get(Integer.parseInt(splitLine[1])) == -1){
                    dataCalculator.increaseFails();
                    Boolean foundEmpty = false;
                    for (int i=0; i< dataCalculator.PageFrames.size(); i++){
                        if (dataCalculator.PageFrames.get(i)==0){
                            dataCalculator.PageFrames.set(i, 1);
                            dataCalculator.PageTable.set(Integer.parseInt(splitLine[1]), i);
                            foundEmpty = true;
                            break;
                        }
                    }

                    if (!foundEmpty){
                        boolean deleted = false;
                        for (int i=0; i< dataCalculator.PageTable.size(); i++){
                            if (dataCalculator.PagesR.get(i)==0 && dataCalculator.PagesM.get(i)==0){
                                dataCalculator.PageFrames.set(i, 0);
                                dataCalculator.ReferencesQueue.offer(data);
                                deleted = true;
                                break;
                            }
                        }

                        if (!deleted){
                            for (int i=0; i< dataCalculator.PageTable.size(); i++){
                                if (dataCalculator.PagesR.get(i)==0 && dataCalculator.PagesM.get(i)==1){
                                    dataCalculator.PageFrames.set(i, 0);
                                    dataCalculator.ReferencesQueue.offer(data);
                                    deleted = true;
                                    break;
                                }
                            }
                        }

                        if (!deleted){
                            for (int i=0; i< dataCalculator.PageTable.size(); i++){
                                if (dataCalculator.PagesR.get(i)==1 && dataCalculator.PagesM.get(i)==0){
                                    dataCalculator.PageFrames.set(i, 0);
                                    dataCalculator.ReferencesQueue.offer(data);
                                    deleted = true;
                                    break;
                                }
                            }
                        }

                        if (!deleted){
                            for (int i=0; i< dataCalculator.PageTable.size(); i++){
                                if (dataCalculator.PagesR.get(i)==1 && dataCalculator.PagesM.get(i)==1){
                                    dataCalculator.PageFrames.set(i, 0);
                                    dataCalculator.ReferencesQueue.offer(data);
                                    deleted = true;
                                    break;
                                }
                            }
                        }

                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    dataCalculator.increaseHits();
                    if (splitLine[3].equals("R")){
                        dataCalculator.setBitR(false, 1, Integer.parseInt(splitLine[1]));
                    }

                    if (splitLine[3].equals("W")){
                        dataCalculator.setBitM(1, Integer.parseInt(splitLine[1]));
                    }
                    try {
                        Thread.sleep(0, 30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
}
