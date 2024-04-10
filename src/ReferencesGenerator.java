import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class ReferencesGenerator {
    private final int TP;
    private final int NF;
    private final int NC;
    private final String FileName;
    public ReferencesGenerator(int tp, int nf, int nc, String fileName) {
        TP = tp;
        NF = nf;
        NC = nc;
        FileName = fileName;
    }

    public void GenerateReferences(){
        try {
            FileWriter writer = new FileWriter("docs/"+FileName);
            int NR = (2*3*3+1)*(NC-2)*(NF-2)+2*(NC)+2*(NF-2);
            int nInt = 2*NC*NF+3*3;
            int NP = (int) Math.ceil(((double)nInt)/(((double)TP)/4.0));
            writer.write(String.format("TP=%d\nNF=%d\nNC=%d\nNF_NC_Filtro=3\nNR=%d\nNP=%d\n", TP, NF, NC, NR, NP));
            writer.write(MatrixReferences());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String MatrixReferences(){
        String references = "";
        double pageFractionFilter;
        double pageFractionData = (9.0/(TP/4.0));
        double pageFractionResults = (9.0 + NF * NC) / (TP / 4.0);
        int[] startFilter = new int[]{0, 0};
        int[] startData = new int[]{(int) pageFractionData, (int) (TP*(pageFractionData-(int)pageFractionData))};
        int[] startResults = new int[]{(int) pageFractionResults, (int) (TP*(pageFractionResults -(int)pageFractionResults))};

        for (int i=1; i<NF-1; i++){
            for (int j=1; j<NC-1; j++){
                for (int a=-1; a<=1; a++){
                    for (int b=-1; b<=1; b++){
                        int i2 = i+a;
                        int j2 = j+b;
                        int i3 = 1+a;
                        int j3 = 1+b;
                        // Read data[i2][j2]
                        pageFractionData = (i2*NC+j2+9.0)/(TP/4.0);
                        references+=String.format("M[%d][%d],%d,%d,R\n", i2, j2, (int) pageFractionData, (int) (TP*(pageFractionData-(int)pageFractionData)));
                        // Read filter[i3][j3]
                        pageFractionFilter = (i3*3.0+j3)/(TP/4.0);
                        references+=String.format("F[%d][%d],%d,%d,R\n", i3, j3, (int) pageFractionFilter, (int) (TP*(pageFractionFilter-(int)pageFractionFilter)));
                    }
                }
                // Write results[i][j]
                pageFractionResults = (i*NC+j+9.0+NF*NC)/(TP/4.0);
                references+=String.format("R[%d][%d],%d,%d,W\n", i, j, (int) pageFractionResults, (int) (TP*(pageFractionResults-(int)pageFractionResults)));
            }
        }

        for (int i=0; i<NC; i++){
            // Write results[0][i]
            pageFractionResults = (0*NC+i+9.0+NF*NC)/(TP/4.0);
            references+=String.format("R[%d][%d],%d,%d,W\n", 0, i, (int) pageFractionResults, (int) (TP*(pageFractionResults-(int)pageFractionResults)));
            // Write results[NF-1][i]
            pageFractionResults = ((NF-1)*NC+i+9.0+NF*NC)/(TP/4.0);
            references+=String.format("R[%d][%d],%d,%d,W\n", NF-1, i, (int) pageFractionResults, (int) (TP*(pageFractionResults-(int)pageFractionResults)));
        }
        for (int i=1; i<NF-1; i++){
            // Write results[i][0]
            pageFractionResults = (i*NC+0+9.0+NF*NC)/(TP/4.0);
            references+=String.format("R[%d][%d],%d,%d,W\n", i, 0, (int) pageFractionResults, (int) (TP*(pageFractionResults-(int)pageFractionResults)));
            // Write results[i][NC-1]
            pageFractionResults = (i*NC+(NC-1)+9.0+NF*NC)/(TP/4.0);
            references+=String.format("R[%d][%d],%d,%d,W\n", i, NC-1, (int) pageFractionResults, (int) (TP*(pageFractionResults-(int)pageFractionResults)));
        }

        return references;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        while(true) {
            System.out.println("Choose an option: ");
            System.out.println("1) Generate references.");
            System.out.println("2) Calculate data.");
            int option = scanner.nextInt();
            switch (option){
                case 1:
                    System.out.println("Enter page size: ");
                    int tp = scanner.nextInt();
                    System.out.println("Enter matrix row size: ");
                    int nf = scanner.nextInt();
                    System.out.println("Enter matrix column size: ");
                    int nc = scanner.nextInt();
                    System.out.println("Enter file name: ");
                    fileName = scanner.next();
                    ReferencesGenerator referenceGenerator= new ReferencesGenerator(tp, nf, nc, fileName);
                    referenceGenerator.GenerateReferences();
                    break;
                case 2:
                    System.out.println("Enter number of page frames: ");
                    int mp = scanner.nextInt();
                    System.out.println("Enter file name: ");
                    fileName = scanner.next();
                    DataCalculator dataCalculator = new DataCalculator(mp, fileName);
                    dataCalculator.CalculateData();
                    break;
                default:
                    System.out.println("Wrong option.");
            }

        }


    }
}