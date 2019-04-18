package models;

import java.util.Date;

public class Admin extends Users{

    Date joinDate;
    int adminNumber;

    public Admin(String userName, Date jDate, int aNum) {
        super(userName);
        joinDate = jDate;
        adminNumber = aNum;
    }
}
