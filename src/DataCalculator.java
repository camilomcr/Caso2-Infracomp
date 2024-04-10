import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class DataCalculator {
    private final int MP;
    private final String ReferenceFile;
    private int Hits;
    private int Fails;
    public List<Integer> PageTable;
    public List<Integer> PageFrames;
    public List<Integer> PagesR;
    public List<Integer> PagesM;
    public Queue<String> ReferencesQueue;

    public DataCalculator(int mp, String referenceFile) {
        MP = mp;
        ReferenceFile = referenceFile;
        Hits=0;
        Fails=0;
        PageTable = new ArrayList<>();
        PageFrames = new ArrayList<>();
        PagesR = new ArrayList<>();
        PagesM = new ArrayList<>();
        ReferencesQueue = new LinkedList<>();

        for (int i=0; i<MP; i++){
            PageFrames.add(0); // 0 No info, 1 Info
        }
    }

    public void increaseFails(){
        Fails++;
    }

    public void increaseHits(){
        Hits++;
    }

    public synchronized void setBitR(Boolean all, int bit, int index){
        if (!PagesR.isEmpty()){
            if (all){
                for (int i =0; i< PagesR.size(); i++){
                    PagesR.set(i,bit);
                }
            }else{
                PagesR.set(index, bit);
            }
        }
    }

    public synchronized void setBitM(int bit, int index){
        if (!PagesM.isEmpty()){
            PagesM.set(index, bit);
        }
    }

    public void CalculateData(){
        int TP;
        String filePath = "docs/"+ReferenceFile;
        new UpdateState(this).start();
        new UpdateR(this).start();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] splitLine = line.split("=");
                if (splitLine[0].equals("TP")){
                    TP = Integer.parseInt(splitLine[1]);
                    for (int i=0; i<TP; i++){
                        PageTable.add(-1); // -1 No reference, other number reference to that page frame
                        PagesR.add(0);
                        PagesM.add(0);
                    }
                }

                splitLine = line.split(",");
                if (splitLine.length == 4){
                    ReferencesQueue.offer(line);
                    Thread.sleep(100);
                }

            }
            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Hits);
        System.out.println(Fails);

    }
}
