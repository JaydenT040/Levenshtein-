import java.io.FileNotFoundException;
import java.io.File;
import java.sql.SQLOutput;
import java.util.*;

public class Levenshtein {
    private HashMap<String, LinkedList<String>> MAP;
    private HashMap<String, LinkedList<String>> PATHS = new HashMap<>();
    private String START, END;
    private int MAP_SIZE = 0;

    private LinkedList<String> Q = new LinkedList<>();
    private HashSet<String> J = new HashSet<>();

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

            if (i % 72000 == 0) {
                System.out.println(i/3600+"%");
            }
        }
        System.out.println("Map is filled\n");
    }

    //Returns true if a path exists
    public void Run() {
        //Sets a start, and an end goal
        PromptNewStrings();

        //Setup default path
        LinkedList<String> tempList;

        //Sets up default path
        tempList = new LinkedList<>(); tempList.add(START);
        PATHS.put(START, tempList);
        updatePaths(START);

        long startTime = System.currentTimeMillis();

        //Temp
        Q.add(START); J.add(START);
        while (!Q.get(0).equals(END)) {
            //Adds to que
            tempList = MAP.get(Q.get(0));
            for (int i = 0; i < tempList.size(); i++) {
                if (J.add(tempList.get(i))) {
                    Q.add(tempList.get(i));
                    updatePaths(tempList.get(i));
                }
            }
            tempList.clear(); Q.remove(0);
            MAP_SIZE++;
        }
        long runtime = System.currentTimeMillis() - startTime;


        System.out.println("Map size: "+MAP_SIZE);
        System.out.println("Runtime: "+ runtime +"ms");
        System.out.println("Path: "+PATHS.get(Q.get(0)));
    }

    private void updatePaths(String s) {
        LinkedList<String> list = MAP.get(s);

        for (int i = 0; i < list.size(); i++) {
            LinkedList<String> previousPath = new LinkedList<>(PATHS.get(s));
            previousPath.add(list.get(i));
            PATHS.put(list.get(i), previousPath);
        }
    }

    private void PromptNewStrings() {
        Scanner input = new Scanner(System.in);
        System.out.print("Start: "); START = input.next().toLowerCase();
        System.out.print("End: "); END = input.next().toLowerCase();

        //Makes sure that START and END both exist as words
        if (!MAP.containsKey(START) || !MAP.containsKey(END)) {
            System.out.println("ONE OF THE WORDS IS NOT REAL!");
        }
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
