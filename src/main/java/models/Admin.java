package models;

import java.util.Date;


/**
 * Short one line description.
 * @author
 * @version
 *
 */
public class Admin extends Users{

    Date joinDate;
    boolean adminRole = true;

    public Admin(String userName, Date jDate) {
        Users u = new Users();
        u.username = userName;
        joinDate = jDate;
    }

    public boolean Role(){
        return  adminRole;
    }

}
