import java.io.FileNotFoundException;
import java.io.File;
import java.sql.SQLOutput;
import java.util.*;

public class Levenshtein {
    private HashMap<String, LinkedList<String>> MAP;
    private HashMap<String, LinkedList<String>> PATHS = new HashMap<>();
    private boolean ShowPrints;
    private String START, END;
    private int MAP_SIZE = 0;

    private LinkedList<String> Q = new LinkedList<>();
    private HashSet<String> NO_LOOPS = new HashSet<>();

    Levenshtein(String s1, String s2, boolean showPrints) throws FileNotFoundException {
        //Fill the MAP
        MAP = new HashMap<>();
        if (ShowPrints) { System.out.println("Filling map"); }

        //Add values
        START = s1; END = s2;
        this.ShowPrints = ShowPrints;

        Scanner FReader = new Scanner(new File("MapOfWords.txt"));
        Scanner Temp;

        for (int i = 0; FReader.hasNextLine(); i++) {
            LinkedList<String> tempList = new LinkedList<>();
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

    //Returns true if a path exists
    public double Run() {
    //Setup default path
        LinkedList<String> tempList;

        //Sets up default path
        tempList = new LinkedList<>(); tempList.add(START);
        PATHS.put(START, tempList);
        updatePaths(START);

        long startTime = System.currentTimeMillis();

        //Adds to que
        Q.add(START); NO_LOOPS.add(START);
        while (!Q.get(0).equals(END)) {
            //Adds to que
            tempList = MAP.get(Q.get(0));
            for (int i = 0; i < tempList.size(); i++) {
                if (NO_LOOPS.add(tempList.get(i))) {
                    Q.add(tempList.get(i));
                    updatePaths(tempList.get(i));
                }
            }
            tempList.clear(); Q.remove(0);
            MAP_SIZE++;
        }
        double runtime = System.currentTimeMillis() - startTime;

        if (ShowPrints) {
            System.out.println("Map size: " + MAP_SIZE);
            System.out.println("Runtime: " + runtime + "ms");
            System.out.println("Path: " + PATHS.get(Q.get(0)));
        }

        return runtime;
    }

    private void updatePaths(String s) {
        LinkedList<String> list = MAP.get(s);

        for (int i = 0; i < list.size(); i++) {
            LinkedList<String> previousPath = new LinkedList<>(PATHS.get(s));
            previousPath.add(list.get(i));
            PATHS.put(list.get(i), previousPath);
        }
    }
}
