import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PageTable {
    private final int[] pageFrames;
    private final boolean[] presentBit;
    private final int size;

    public PageTable(int size) {
        this.size = size;
        this.pageFrames = new int[size];
        this.presentBit = new boolean[size];
        for (int i = 0; i < size; i++) {
            pageFrames[i] = -1;
            presentBit[i] = false;
        }
    }

    public boolean accessPage(int page) {
        for (int i = 0; i < size; i++) {
            if (pageFrames[i] == page && presentBit[i]) {
                return true;
            }
        }
        return false;
    }
}

public class DataCalculator {
    private final int MP;
    private final String ReferenceFile;
    private int Hits;
    private int Fails;
    private final PageTable pageTable;

    public DataCalculator(int mp, String referenceFile) {
        MP = mp;
        ReferenceFile = referenceFile;
        Hits = 0;
        Fails = 0;
        pageTable = new PageTable(MP);
    }

    public void CalculateData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ReferenceFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    int page = Integer.parseInt(parts[1]);
                    boolean hit = pageTable.accessPage(page);
                    if (hit) {
                        Hits++;
                    } else {
                        Fails++;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hits: " + Hits);
        System.out.println("Fails: " + Fails);
    }
}
