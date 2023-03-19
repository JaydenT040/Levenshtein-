import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        //finals
        final String s1 = "monkey", s2 = "business";
        final boolean ShowPrints = true;
        final boolean RunOption = false;
        final int loopAmount = 20;

        //for avg score
        double timeInMS = 0;

        System.out.println("Running...");
        if (RunOption) {
            for (int i = 0; i < loopAmount; i++) {
                LevenshteinFaster lev = new LevenshteinFaster(s1,s2,ShowPrints);
                timeInMS += lev.Run();
            }
        } else {
            for (int i = 0; i < loopAmount; i++) {
                Levenshtein lev2 = new Levenshtein(s1,s2,ShowPrints);
                timeInMS += lev2.Run();
            }
        }


        //calculate avg time
        System.out.println("Average run time over "+loopAmount+ " runs: "+timeInMS/loopAmount);
    }
}