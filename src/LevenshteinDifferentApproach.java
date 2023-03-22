import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class LevenshteinDifferentApproach {
    private final HashMap<String, ArrayList<String>> PATHS = new HashMap<>();
    private final HashMap<String, ArrayList<String>> MAP;
    private final boolean ShowPrints;
    private final String START, END;
    private boolean END_FOUND;

    private ArrayList<String> LEFT_HALF;
    private ArrayList<String> RIGHT_HALF;
    private HashSet<String> NO_REPEATS;

    LevenshteinDifferentApproach(String s1, String s2, boolean showPrints) throws FileNotFoundException {
        //Fill the MAP
        MAP = new HashMap<>();
        System.out.println("Filling map.");

        //Setup variables
        START = s1; END = s2;
        ShowPrints = showPrints;
        END_FOUND = false;

        //Open the file
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
        NO_REPEATS = new HashSet<>();
        LEFT_HALF = new ArrayList<>();
        RIGHT_HALF = new ArrayList<>();
        int MAP_SIZE = 0;

        LEFT_HALF = MAP.get(START);
        RIGHT_HALF = MAP.get(END);

        //Setup default paths
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(START); PATHS.put(START, tempList);
        updatePaths(START);

        tempList.clear(); tempList.add(END);
        PATHS.put(END,tempList);
        updatePaths(END);

        //Start timer
        long StartTime = System.currentTimeMillis();

        //Runs loop until path is found
        while (!END_FOUND) {
            LEFT();
            RIGHT();
        }

        //End timer
        double runtime = System.currentTimeMillis() - StartTime;

        if (ShowPrints) {
            System.out.println("Map size: " + MAP_SIZE);
            System.out.println("Runtime: " + runtime + "ms");
        }

        return runtime;
    }

    private void LEFT() {

    }
    private void RIGHT() {

    }
    private void updatePaths(String s) {
        ArrayList<String> list = MAP.get(s);

        for (String value : list) {
            ArrayList<String> previousPath = new ArrayList<>(PATHS.get(s));
            previousPath.add(value);
            PATHS.put(value, previousPath);
        }
    }
    public ArrayList<String> GetPath() { return new ArrayList<>(); /* FIX LATER */ }
}
