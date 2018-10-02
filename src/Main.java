import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Integer[] a = {1,2,3,4,5,6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(a);
        System.out.println(liste.toString() + " " + liste.omvendtString());

        System.out.println(liste.hent(4));
    }

}
