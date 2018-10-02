package seedu.address.testutil;

import seedu.address.model.staff.Staff;

public class TypicalStaffs {

    public static final Staff ZUL = new StaffBuilder().withName("Muhammad Zulqarnain")
            .withUsername("zulq9")
            .withPassword("zulq9999")
            .withRole(Staff.Role.admin).build();

    public static final Staff ESMOND = new StaffBuilder().withName("Esmond Tan")
            .withUsername("esmondTan")
            .withPassword("esmondtt")
            .withRole(Staff.Role.admin).build();

    public static final Staff TENGXIONG = new StaffBuilder().withName("Yao TengXiong")
            .withUsername("yaotx")
            .withPassword("pcyaotxcfanboi")
            .withRole(Staff.Role.manager).build();

    public static final Staff DARREN = new StaffBuilder().withName("Darren Ong")
            .withUsername("darren96")
            .withPassword("darrenSinglenus")
            .withRole(Staff.Role.manager).build();

    public static final Staff WANGCHAO = new StaffBuilder().withName("Wang Chao")
            .withUsername("fzdy1914")
            .withPassword("fengzhidaoying")
            .withRole(Staff.Role.user).build();

    private TypicalStaffs() {}
}
