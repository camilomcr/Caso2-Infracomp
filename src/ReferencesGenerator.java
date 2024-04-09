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
            FileWriter writer = new FileWriter(FileName);
            int NR = (2*3*3+1)*(NC-2)*(NF-2)+2*(NC)+2*(NF-2);
            int nInt = 2*NC*NF+3*3;
            int NP = (int) Math.ceil(((double)nInt)/(((double)TP)/4.0));
            writer.write(String.format("TP=%d\nNF=%d\nNC=%d\nNF_NC_Filtro=3\nNR=%d\nNP=%d\n", TP, NF, NC, NR, NP));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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