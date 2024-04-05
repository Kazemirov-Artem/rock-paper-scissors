import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Master implements Runnable {
    int playerCount;
    CopyOnWriteArrayList<Symbols> selectedSymbols;
    public Master(int playerCount, CopyOnWriteArrayList<Symbols> selectedSymbols) {
        this.playerCount = playerCount;
        this.selectedSymbols = selectedSymbols;
    }

    @Override
    public void run() {
        List<Integer> playerWins = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerWins.add(i, 0);
        }
        while (true) {
            while (selectedSymbols.contains(null)) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int winner = whoWinner(selectedSymbols);
            if (winner >= 0) {
                playerWins.set(winner, playerWins.get(winner) + 1);
            }
            if (playerWins.contains(5)) {
                break;
            }
            Collections.fill(selectedSymbols, null);
        }
        System.out.println("Выйграл игрок под номером " + playerWins.indexOf(5));
    }

    private int whoWinner(List<Symbols> symbols) {
        var winners = List.of(
                checkSymbols(symbols, Symbols.PAPER, Symbols.STONE),
                checkSymbols(symbols, Symbols.STONE, Symbols.SCISSORS),
                checkSymbols(symbols, Symbols.SCISSORS, Symbols.PAPER)
        );
        for (var i : winners) {
            if (i >= 0) {
                System.out.println("По решению судьи победил игрок №" + i);
                return i;
            }
        }
        System.out.println("По решению судьи - ничья");
        return -1;
    }

    private int checkSymbols(List<Symbols> symbols, Symbols winnerSymbol, Symbols lostSymbol) {
        if (!symbols.contains(winnerSymbol)) {
            int stoneNum = 0;
            for (var symbol : symbols) {
                if (symbol.equals(lostSymbol)) {
                    stoneNum++;
                }
            }
            if (stoneNum == 1) {
                return symbols.indexOf(lostSymbol);
            }
        }
        return -1;
    }
}
