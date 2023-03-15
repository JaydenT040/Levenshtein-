import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner Reader = new Scanner(new File("Dict.txt"));
        FileWriter F = new FileWriter("MapOfWords.txt");
        LinkedList<String> Pos;
        int laps = 0;

        while (Reader.hasNextLine()) {
            System.out.println(laps);
            String CurrentWord = Reader.nextLine();
            Pos = Scan(CurrentWord);

            F.write(CurrentWord+" ");
            for (int i = 0; i < Pos.size(); i++) {
                F.write(Pos.get(i)+" ");
            }
            F.write("\n");
            laps++;
        }
    }
    public static LinkedList<String> Scan(String CurrentWord) throws FileNotFoundException {
        LinkedList<String> PossibleTransitions = new LinkedList<>();
        Scanner Reader = new Scanner(new File("Dict.txt"));

        while (Reader.hasNextLine()) {
            String STemp = Reader.nextLine();
            int k = CurrentWord.length()-STemp.length();
            if (k <= 1 && k >= -1) {
                if (Dif(CurrentWord,STemp) == 1) {
                    PossibleTransitions.add(STemp);
                }
            }
            else if (k < -1) { break; }
        }

        return PossibleTransitions;
    }
    public static int Dif(String s1, String s2) {
        if (s1.length() == 0 && s2.length() == 0) { return 0; }
        else if (s1.length()==0) { return Dif(s1,s2.substring(1))+1; }
        else if (s2.length()==0) { return Dif(s1.substring(1),s2)+1; }
        else {

            if (s1.charAt(0)==s2.charAt(0)) {
                return Dif(s1.substring(1),s2.substring(1));
            }
            else  {
                if (s1.length()==s2.length()) { return Dif(s1.substring(1),s2.substring(1))+1; }
                else if (s1.length()>s2.length()) { return Dif(s1.substring(1),s2)+1; }
                else {
                    return Dif(s1,s2.substring(1))+1;
                }
            }

        }
    }
}