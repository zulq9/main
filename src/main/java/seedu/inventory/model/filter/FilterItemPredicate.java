package seedu.inventory.model.filter;

import java.util.function.Predicate;

import seedu.inventory.model.item.Item;

/**
 * Tests that a {@code Item}'s {@code quantity} or {@code price} matches any of the keywords given.
 */
public class FilterItemPredicate implements Predicate<Item> {
    //private final List<String> keywords;
    private Double price;
    private Character priceCondition;
    private Integer quantity;
    private Character quantityCondition;

    public FilterItemPredicate(FilterPrice price) {
        this.priceCondition = this.getFilterCondition(price.value);
        this.price = Double.parseDouble(this.removeFirst(price.value));
        this.quantityCondition = null;
        this.quantity = null;
    }

    public FilterItemPredicate(FilterQuantity quantity) {
        this.quantityCondition = this.getFilterCondition(quantity.value);
        this.quantity = Integer.parseInt(this.removeFirst(quantity.value));
        this.priceCondition = null;
        this.price = null;
    }

    public FilterItemPredicate(FilterPrice price, FilterQuantity quantity) {
        this.priceCondition = this.getFilterCondition(price.value);
        this.price = Double.parseDouble(this.removeFirst(price.value));
        this.quantityCondition = this.getFilterCondition(quantity.value);
        this.quantity = Integer.parseInt(this.removeFirst(quantity.value));
    }

    private char getFilterCondition(String s) {
        return s.charAt(0);
    }

    private String removeFirst(String s) {
        return s.substring(1);
    }

    @Override
    public boolean test(Item item) {
        if (this.priceCondition != null && this.quantityCondition != null) {
            if (this.priceCondition == '>' && this.quantityCondition == '>') {
                return (Double.parseDouble(item.getPrice().value) >= this.price
                        && Integer.parseInt(item.getQuantity().value) >= this.quantity);
            } else if (this.priceCondition == '>' && this.quantityCondition == '<') {
                return (Double.parseDouble(item.getPrice().value) >= this.price
                        && Integer.parseInt(item.getQuantity().value) <= this.quantity);
            } else if (this.priceCondition == '<' && this.quantityCondition == '<') {
                return (Double.parseDouble(item.getPrice().value) <= this.price
                        && Integer.parseInt(item.getQuantity().value) <= this.quantity);
            } else {
                return (Double.parseDouble(item.getPrice().value) <= this.price
                        && Integer.parseInt(item.getQuantity().value) >= this.quantity);
            }
        } else if (this.priceCondition != null) {
            if (this.priceCondition == '>') {
                return Double.parseDouble(item.getPrice().value) >= this.price;
            } else {
                return Double.parseDouble(item.getPrice().value) <= this.price;
            }
        } else {
            if (this.quantityCondition == '>') {
                return Integer.parseInt(item.getQuantity().value) >= this.quantity;
            } else {
                return Integer.parseInt(item.getQuantity().value) <= this.quantity;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterItemPredicate); // instanceof handles nulls
    }

}
