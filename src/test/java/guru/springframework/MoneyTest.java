package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static guru.springframework.Expression.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Abhijit.Ghonge
 */
public class MoneyTest {

    private Bank bank;

    @BeforeEach
    void setup(){
        bank = new Bank();
        bank.addRate("CHF", "USD", 2);
    }

    @Test
    void testMultiplication(){
        Money fiveDollars = Money.dollar(5);

        assertEquals(Money.dollar(10),
                identity(fiveDollars)
                        .times(2)
                        .reduce(bank,"USD"));
        assertEquals(Money.dollar(15), identity(fiveDollars).times(3).reduce(bank,"USD"));

        Money fiveFrancs = Money.franc(5);

        assertEquals(Money.franc(10),identity(fiveFrancs).times(2).reduce(bank,"CHF"));
        assertEquals(Money.franc(15), identity(fiveFrancs).times(3).reduce(bank,"CHF"));
    }


    @Test
    void testEquality() {
        assertEquals(Money.franc(5), Money.franc(5));
        assertNotEquals(Money.franc(5), Money.franc(8));
        assertEquals(Money.dollar(5), Money.dollar(5));
        assertNotEquals(Money.franc(5), Money.dollar(5));
    }

    @Test
    void testCurrency() {
        assertEquals("USD", Money.dollar(5).currency());
        assertEquals("CHF", Money.franc(5).currency());
    }

    @Test
    void testPlusReturnSum() {
        Money fiveDollars = Money.dollar(5);
        Money fiveFrancs = Money.franc(5);
        Money result = identity(fiveDollars).plus(fiveDollars).reduce(bank,"USD");
//        Money result = identity(fiveDollars).plus(fiveFrancs).with(()->"USD").reduce(bank);

        assertEquals(Money.dollar(10), result);

    }

    @Test
    void testReduceSum() {
       Expression sum = sum(identity(Money.dollar(3)), identity(Money.dollar(4)));
       Money result = bank.reduce(sum, "USD");
       assertEquals(Money.dollar(7), result);
    }

    @Test
    void testReduceMoney() {
       Money result = bank.reduce(identity(Money.dollar(1)), "USD");
       assertEquals(Money.dollar(1), result);
    }

    @Test
    void testReduceMoneyDifferentCurrency(){
        Money result = bank.reduce(identity(Money.franc(2)), "USD");

        assertEquals(Money.dollar(1), result);
    }

    @Test
    void testIdentityRate(){
        assertEquals(1, new Bank().rate("USD", "USD"));
        assertEquals(1, new Bank().rate("CHF", "CHF"));

    }

    @Test
    void testSumForMixedCurrencies(){
        Expression sum = sum(identity(Money.dollar(5)), identity(Money.franc(10)));
        Money result = sum.reduce(bank, "USD");

        assertEquals(Money.dollar(10), result);

    }



}
