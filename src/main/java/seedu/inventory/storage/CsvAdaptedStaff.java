package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Csv-friendly adapted version of the Sale.
 */
public class CsvAdaptedStaff {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE = "Staff's field is missing!";

    private String username;
    private String password;
    private String name;
    private String role;


    /**
     * Constructs an {@code CsvAdaptedStaff} with the given staff details.
     */
    public CsvAdaptedStaff(String username, String password, String name, Staff.Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role.name();
    }


    /**
     * Converts a given Staff into this class for Csv use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStaff
     */
    public CsvAdaptedStaff(Staff source) {
        this.username = source.getUsername().username;
        this.password = source.getPassword().password;
        this.name = source.getStaffName().fullName;
        this.role = source.getRole().name();
    }

    /**
     * Converts this Csv-friendly adapted staff object into the model's Staff object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff
     */
    public Staff toModelType() throws IllegalValueException {
        if (this.username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(this.username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (this.password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(this.password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StaffName.class.getSimpleName()));
        }
        if (!StaffName.isValidName(this.name)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final StaffName modelStaffName = new StaffName(name);

        if (this.role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Staff.Role.class.getSimpleName()));
        }

        final Staff.Role modelRole = Staff.Role.valueOf(role);

        return new Staff(modelUsername, modelPassword, modelStaffName, modelRole);
    }


//    /**
//     * Combine a Csv-friendly adapted sale into a list of string representing the content.
//     *
//     * @param sale A Csv-friendly sale
//     * @return content A list of string representing the content.
//     */
//    public static List<String> getContentFromSale(CsvAdaptedStaff sale) {
//        List<String> content = new ArrayList<>();
//        content.add(sale.saleId);
//        content.add(sale.saleSku);
//        content.add(sale.saleQuantity);
//        content.add(sale.saleDate);
//        return content;
//    }
//
//    /**
//     * Split a list of string representing the content of sale into the Csv-friendly adapted sale
//     *
//     * @param content A list of string representing the content of sale
//     * @return The Csv-friendly adapted sale containing the content of the list.
//     */
//    public static CsvAdaptedStaff splitContentToSale(List<String> content) throws IllegalValueException {
//        if (content.size() < 4) {
//            throw new IllegalValueException(MISSING_FIELD_MESSAGE);
//        }
//        String saleId = content.get(0);
//        String saleSku = content.get(1);
//        String saleQuantity = content.get(2);
//        String saleDate = content.get(3);
//        return new CsvAdaptedStaff(saleId, saleSku, saleQuantity, saleDate);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof CsvAdaptedStaff)) {
//            return false;
//        }
//
//        CsvAdaptedStaff otherSale = (CsvAdaptedStaff) other;
//        return Objects.equals(saleId, otherSale.saleId)
//                && Objects.equals(saleDate, otherSale.saleDate)
//                && Objects.equals(saleQuantity, otherSale.saleQuantity)
//                && Objects.equals(saleSku, otherSale.saleSku);
//    }
}
