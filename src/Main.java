import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

        liste.nullstill();
        System.out.println(liste.toString());

    }

}
