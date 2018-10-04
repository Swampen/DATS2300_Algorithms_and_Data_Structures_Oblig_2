import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Integer[] s = Main.randPermInteger(1700);

        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(s);

        System.out.println(liste.toString());
        DobbeltLenketListe.sorter(liste, Comparator.naturalOrder());
        System.out.println(liste.toString());
    }

    public static Integer[] randPermInteger(int n){
        Integer[] a = new Integer[n];               // en Integer-tabell
        Arrays.setAll(a, i -> i + 1);               // tallene fra 1 til n

        Random r = new Random();   // hentes fra  java.util

        for (int k = n - 1; k > 0; k--)
        {
            int i = r.nextInt(k+1);  // tilfeldig tall fra [0,k]
            bytt(a,k,i);             // bytter om
        }
        return a;  // tabellen med permutasjonen returneres
    }

    public static void bytt(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
