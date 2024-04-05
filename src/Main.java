import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static CopyOnWriteArrayList<Symbols> selectedSymbols;

    public static void main(String[] args) throws InterruptedException {
        int n = 3; //если потоков будет 4, то нукжно изменить n на 4
        selectedSymbols = new CopyOnWriteArrayList<>(new Symbols[n]);
        Thread master = new Thread(new Master(n, selectedSymbols));
        Thread a = new Thread(new Player(selectedSymbols, 0));
        Thread b = new Thread(new Player(selectedSymbols, 1));
        Thread c = new Thread(new Player(selectedSymbols, 2));
        Thread d = new Thread(new Player(selectedSymbols, 3));
        master.start();
        a.start();
        b.start();
        c.start();
        //d.start();
        b.join();
        a.join();
        c.join();
        //d.join();
        master.join();
    }
}