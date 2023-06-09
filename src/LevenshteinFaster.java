import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LevenshteinFaster {
    private final HashMap<String, ArrayList<String>> MAP;
    private final HashMap<String, ArrayList<String>> PATHS = new HashMap<>();
    private final boolean ShowPrints;
    private final String START, END;

    private LinkedList<String> Q;
    private HashSet<String> NO_LOOPS;

    LevenshteinFaster(String s1, String s2, boolean ShowPrints) throws FileNotFoundException {
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
        Q = new LinkedList<>();
        NO_LOOPS = new HashSet<>();
        int MAP_SIZE = 0;

        //Setup default path
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(START);
        PATHS.put(START, tempList);
        updatePaths(START);

        long startTime = System.currentTimeMillis();

        //Adds to que
        Q.add(START); NO_LOOPS.add(START);
        while (!Q.get(0).equals(END)) {
            //Adds to que
            Scan();
            Q.remove(0);
            MAP_SIZE++;
        }
        double runtime = System.currentTimeMillis() - startTime;

        if (ShowPrints) {
            System.out.println("Map size: " + MAP_SIZE);
            System.out.println("Runtime: " + runtime + "ms");
        }

        return runtime;
    }

    private void Scan() {
        Map<String, Integer> Transitions = new TreeMap<>();

        //fill map
        for (String k : MAP.get(Q.get(0))) {
            if (NO_LOOPS.add(k)) {
                Transitions.put(k,levDis(k.toCharArray(), END.toCharArray()));
                updatePaths(k);
            }
        }

        //Sort
        List<Map.Entry<String,Integer>> list = new ArrayList<>(Transitions.entrySet());
        list.sort(Map.Entry.comparingByValue());
        LinkedHashMap<String,Integer> OrderedMap = new LinkedHashMap<>();

        for (Map.Entry<String,Integer> entry : list) {
            OrderedMap.put(entry.getKey(), entry.getValue());
        }

        //Add to que
        Q.addAll(OrderedMap.keySet());
    }

    private void updatePaths(String s) {
        ArrayList<String> list = MAP.get(s);

        for (String value : list) {
            ArrayList<String> previousPath = new ArrayList<>(PATHS.get(s));
            previousPath.add(value);
            PATHS.put(value, previousPath);
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
    public ArrayList<String> GetPath() {
        return PATHS.get(END);
    }
}
