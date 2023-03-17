import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        Levenshtein lev = new Levenshtein();
        lev.PromptNewStrings();

        //test cases
        System.out.println(lev.MAP.size());
        System.out.println(lev.MAP.containsKey(lev.START));
        System.out.println(lev.MAP.get("a"));
        System.out.println(lev.MAP.get(lev.START));
        System.out.println(lev.GetList("a"));
    }
}