package guru.springframework;

/**
 * @author Abhijit.Ghonge
 */

@FunctionalInterface
public interface Expression {

    static Expression identity(Money money) {
        return (bank, to) -> new Money(money.amount / bank.rate(money.currency, to), to);
    }


    default Expression plus(Money money) {
        return sum(this, Expression.identity(money));

    }

    default Expression times( int multiplier) {
        return (bank, to) ->
            new Money(this.reduce(bank,to).amount * multiplier, to);

    }



    static Expression sum(Expression augmend, Expression addend) {
        return (bank, to) -> {
            int amount = augmend.reduce(bank, to).amount + addend.reduce(bank, to).amount;

            return new Money(amount, to);
        };
    }


    Money reduce(Bank bank, String toCurrency);
}
