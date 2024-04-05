import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Player implements Runnable {
    Random random = new Random();

    private final CopyOnWriteArrayList<Symbols> symbols;

    private final int id;

    Boolean isFinish = false;

    public Player(CopyOnWriteArrayList<Symbols> symbols, int id) {
        this.id = id;
        this.symbols = symbols;
    }

    @Override
    public void run() {
        while (!isFinish) {
            try {
                sleep(random.nextInt(200, 500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            var s = random.nextInt() % 3;
            switch (s) {
                case 0 -> {
                    System.out.println("Тред с id " + id + " выкинул бумагу");
                    symbols.set(id, Symbols.PAPER);
                }
                case 1 -> {
                    System.out.println("Тред с id " + id + " выкинул камень");
                    symbols.set(id, Symbols.STONE);
                }
                case 2 -> {
                    System.out.println("Тред с id " + id + " выкинул ножницы");
                    symbols.set(id, Symbols.SCISSORS);
                }
            }

            while (symbols.get(id) != null) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
