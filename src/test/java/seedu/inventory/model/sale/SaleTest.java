package seedu.inventory.model.sale;

import org.junit.Test;
import seedu.inventory.testutil.Assert;

public class SaleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Sale(null, null, null));
    }
}
