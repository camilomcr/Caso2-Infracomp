import java.util.Scanner;
public class ReferencesGenerator {
    private final int TP;
    private final int NF;
    private final int NC;
    public ReferencesGenerator(int tp, int nf, int nc) {
        TP = tp;
        NF = nf;
        NC = nc;
    }

    public void GenerateReferences(){

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    ReferencesGenerator referenceGenerator= new ReferencesGenerator(tp, nf, nc);
                    referenceGenerator.GenerateReferences();
                    break;
                case 2:
                    System.out.println("Enter page frame size: ");
                    int mp = scanner.nextInt();
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    DataCalculator dataCalculator = new DataCalculator(mp, fileName);
                    dataCalculator.CalculateData();
                    break;
                default:
                    System.out.println("Wrong option.");
            }

        }


    }
}