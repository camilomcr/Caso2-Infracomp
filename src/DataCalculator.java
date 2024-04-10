import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
public class DataCalculator {
    private final int MP;
    private final String ReferenceFile;
    private int Hits;
    private int Fails;


    public DataCalculator(int mp, String referenceFile) {
        MP = mp;
        ReferenceFile = referenceFile;
        Hits=0;
        Fails=0;
    }

    public void CalculateData(){
        int TP;
        String filePath = "docs/"+ReferenceFile;

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] splitLine = line.split("=");
                if (splitLine[0].equals("TP")){
                    TP = Integer.parseInt(splitLine[1]);
                }

                splitLine = line.split(",");
                if (splitLine[3].equals("R") || splitLine[3].equals("W")){
                    
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
