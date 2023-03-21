import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class LevenshteinWithDeque {
    private final HashMap<String, ArrayList<String>> MAP;
    private final HashMap<String, ArrayList<String>> PATHS = new HashMap<>();
    private final boolean ShowPrints;
    private final String START, END;

    private Deque<String> DQ;
    private HashSet<String> NO_LOOPS;

    LevenshteinWithDeque(String s1, String s2, boolean ShowPrints) throws FileNotFoundException {
        //Fill the MAP
        MAP = new HashMap<>();
        if (ShowPrints) { System.out.println("Filling map"); }

        //Add values
        START = s1; END = s2;
        this.ShowPrints = ShowPrints;

        Scanner FReader = new Scanner(new File("MapOfWords.txt"));
        Scanner Temp;

        for (int i = 0; FReader.hasNextLine(); i++) {
            ArrayList<String> tempList = new ArrayList<>();
            Temp = new Scanner(FReader.nextLine());
            String key = Temp.next();
            while (Temp.hasNext()) { tempList.add(Temp.next()); }

            MAP.put(key,tempList);

            if (ShowPrints && i % 72000 == 0) {
                System.out.println(i/3600+"%");
            }
        }
        if (ShowPrints) { System.out.println("Map is filled\n"); }
    }

    public double Run() {
        //Setup variables
        NO_LOOPS = new HashSet<>();
        DQ = new ArrayDeque<>();
        int MAP_SIZE = 0;

        NO_LOOPS.add(START);
        DQ.add(START);

        //Setup default path
        ArrayList<String> AList = new ArrayList<>(); AList.add(START);
        PATHS.put(START,AList);

        //Starts timer
        long StartTime = System.currentTimeMillis();

        //Runs until END is found
        while (!DQ.getLast().equals(END)) {
            Scan();
            MAP_SIZE++;
        }

        //Stops timer
        long runtime = System.currentTimeMillis() - StartTime;

        //prints to screen
        if (ShowPrints) {
            System.out.println("Map size: " + MAP_SIZE);
            System.out.println("Runtime: " + runtime + "ms");
        }

        return runtime;
    }

    private void Scan() {
        //Creates a map that holds the word and its edit distance from END
        Map<String,Integer> Transitions = new TreeMap<>();

        //Fills said map
        for (String word : MAP.get(DQ.getLast())) {
            if (NO_LOOPS.add(word)) {
                Transitions.put(word,levDis(word.toCharArray(),END.toCharArray()));
            }
        }
    }

    private void updatePaths(String s1, String s2) {
        ArrayList<String> list = MAP.get(s1);

        list.add(s2);
        PATHS.put(s2,list);
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
    public ArrayList<String> GetPath() { return PATHS.get(END); }
}
