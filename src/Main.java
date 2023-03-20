import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        //finals
        final String s1 = "monkey", s2 = "business";
        final boolean ShowPrints = true;
        final boolean RunOption = true;
        final int loopAmount = 5000;

        //for avg score
        double timeInMS = 0;

        ArrayList<String> Path;

        System.out.println("Running...");
        if (RunOption) {
            LevenshteinFaster lev = new LevenshteinFaster(s1,s2,ShowPrints);
            for (int i = 0; i < loopAmount; i++) {
                timeInMS += lev.Run();
            }
            Path = lev.GetPath();
        } else {
            for (int i = 0; i < loopAmount; i++) {
                Levenshtein lev2 = new Levenshtein(s1,s2,ShowPrints);
                timeInMS += lev2.Run();
            }
        }


        //calculate avg time & prints
        System.out.println("Average run time over "+loopAmount+ " runs: "+timeInMS/loopAmount);
        System.out.print("With path: ");
        System.out.print(Path.get(0));
        for (int i = 1; i < Path.size(); i++) {
            System.out.print(" -> "+Path.get(i));
        }
    }
}