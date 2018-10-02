import java.util.Iterator;

/////////// DobbeltLenketListe ////////////////////////////////////
        import java.util.Comparator;
        import java.util.ConcurrentModificationException;
        import java.util.Iterator;
        import java.util.NoSuchElementException;
        import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T> {
    private static final class Node<T>{  // en indre nodeklasse

        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste){  // konstruktør
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi){  // konstruktør
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks){
        Node<T> node = hode;

        if (indeks < antall/2){
            for (int i = 0; i < indeks; i++){
                node = node.neste;
            }
        }else {
            node = hale;
            for (int i = antall-1; i > indeks; i--){
                node = node.forrige;
            }
        }
        return node;
    }

    // konstruktør
    public DobbeltLenketListe(){
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // Oppgave 1
    public DobbeltLenketListe(T[] a){
        this();
        Objects.requireNonNull(a, "Ikke tillatt med null-verdier!");

        if (a.length == 0){
            return;
        }
        int i = 0;
        if (a[i] != null){
            hode = hale = new Node<>(a[i], null, null);
            antall++;
            i++;
        }else {
            while (a[i] == null) {
                i++;
                if (i == a.length){
                    return;
                }
                if (a[i] != null) {
                    hode = hale = new Node<>(a[i], null, null);
                    antall++;
                    i++;
                    break;
                }
            }
        }
        if (a.length == 1){
            return;
        }

        if (a[i] != null){
            hale = hode.neste = new Node<>(a[i], hode, null);
            antall++;
            i++;
        }else {
            while (a[i] == null) {
                i++;
                if (i == a.length){
                    return;
                }
                if (a[i] != null) {
                    hale = hode.neste = new Node<>(a[i], hode, null);
                    antall++;
                    i++;
                    break;
                }
            }
        }

        for (; i < a.length; i++){
            if (a[i] != null) {
                hale = hale.neste = new Node<T>(a[i], hale, null);
                antall++;
            }
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til){
        T[] a = (T[]) new  Object[til-fra];
        fratilKontroll(antall, fra, til);
        int indeks = 0;

        for(int i = fra; i < til; i++){
            a[indeks] = hent(i);
            indeks++;
        }

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>(a);
        return liste;
    }

    @Override
    public int antall(){
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null, null);
        }else {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi){
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);
        if (indeks == 0){
            hode = new Node<>(verdi, null, hode);
            if (antall == 0){
                hale = hode;
            }else {
                hode.neste.forrige = hode;
            }

        }else if (indeks == antall){
            hale = hale.neste = new Node<>(verdi, hale, null);
        }else {
            Node<T> hode = finnNode(indeks - 1);
            Node<T> hale = hode.neste;
            Node<T> nyNode = new Node<>(verdi, hode, hale);
            hode.neste = nyNode;
            hale.forrige = nyNode;
        }
        endringer++;
        antall++;
    }

    @Override
    public boolean inneholder(T verdi){
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks){
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi){
        for (int i = 0; i < antall; i++){
            T hentet = hent(i);
            if (hentet.equals(verdi)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi){
        Objects.requireNonNull(nyverdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill(){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString() {
        if (antall == 0) {
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        s.append("[");

        if(!tom()){
            Node<T> p = hode;
            s.append(p.verdi);

            p = p.neste;

            while(p != null){
                s.append(",").append(" ").append(p.verdi);
                p = p.neste;
            }
        }

        s.append("]");

        return s.toString();
    }

    public String omvendtString(){
        if (antall == 0) {
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        s.append("[");

        if(!tom()){
            Node<T> p = hale;
            s.append(p.verdi);

            p = p.forrige;

            while(p != null){
                s.append(",").append(" ").append(p.verdi);
                p = p.forrige;
            }
        }

        s.append("]");

        return s.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator(){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks){
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>{
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext(){
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator
}