import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class Levenshtein {
    public HashMap<String, LinkedList<String>> MAP;
    public LinkedList<String> Path = new LinkedList<>();
    public String START, END;

    Levenshtein() throws FileNotFoundException {
        //Fill the MAP
        MAP = new HashMap<>();
        System.out.println("Filling map");

        Scanner FReader = new Scanner(new File("MapOfWords.txt"));
        Scanner Temp;

        for (int i = 0; FReader.hasNextLine(); i++) {
            LinkedList<String> tempList = new LinkedList<>();
            Temp = new Scanner(FReader.nextLine());
            String key = Temp.next();
            while (Temp.hasNext()) { tempList.add(Temp.next()); }

            MAP.put(key,tempList);
            //System.out.println(MAP.get(key));

            if (i % 37000 == 0) {
                System.out.println(i/3700+"%");
            }
        }
        System.out.println("Map is filled\n");
    }

    private void Main() throws FileNotFoundException {
        Levenshtein lev = new Levenshtein();

        //Get a new START and END

    }



    public void PromptNewStrings() {
        Scanner input = new Scanner(System.in);
        System.out.print("Start: "); START = input.next().toLowerCase();
        System.out.print("End: "); END = input.next().toLowerCase();

        //Makes sure that START and END both exist as words
        if (!MAP.containsKey(START) || !MAP.containsKey(END)) {
            System.out.println("ONE OF THE WORDS IS NOT REAL!");
        }
    }

    public LinkedList<String> GetList(String j) { //used in trying to figure out problem
        return MAP.get(j);
    }

    private int levDis(char[] s1, char[] s2) {
        int[] prev = new int[ s2.length + 1 ];

        for (int j = 0; j < s2.length + 1; j++) { prev[ j ] = j; }

        for (int i = 1; i < s1.length + 1; i++) {
            int[] curr = new int[ s2.length + 1 ];
            curr[0] = i;

            for (int j = 1; j < s2.length + 1; j++) {
                int d1 = prev [j] + 1;
                int d2 = curr [j-1] + 1;
                int d3 = prev [j-1];
                if ( s1[ i - 1 ] != s2[ j - 1 ] ) { d3 += 1; }
                curr[ j ] = Math.min( Math.min( d1, d2 ), d3 );
            }

            prev = curr;
        }
        return prev[ s2.length ];
    }
}
