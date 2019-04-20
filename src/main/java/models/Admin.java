package models;

import java.util.Date;

public class Admin extends Users{

    Date joinDate;
    boolean adminRole = true;

    public Admin(String userName, Date jDate) {
        Users u = new Users();
        userName= u.username;
        joinDate = jDate;
    }

    public boolean Role(){
        return  adminRole;
    }

}
