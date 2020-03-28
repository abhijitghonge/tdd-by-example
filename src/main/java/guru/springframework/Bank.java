package guru.springframework;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Abhijit.Ghonge
 */
public class Bank {

    private Map<Pair, Integer> rateMap;

    public Bank() {
        this.rateMap = new HashMap<>();
        this.rateMap.put(Pair.identity("USD"), 1);
        this.rateMap.put(Pair.identity("CHF"), 1);

    }

    public Money reduce(Expression source, String toCurrency) {
        return source.reduce(this, toCurrency);
    }

    public void addRate(String from, String to, int rate) {
        rateMap.put(Pair.of(from, to), rate);
    }

    public int rate(String from, String to) {
        return rateMap.computeIfAbsent(Pair.of(from, to), pair -> 1);
    }
}
