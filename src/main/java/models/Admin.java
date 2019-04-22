package models;

import java.util.Date;


/**
 * This class represents a user with administrator privileges.
 * @author Hector
 * @version 1.1
 */
public class Admin extends Users{
    /**
     * This attribute denotes that the user is administrator.
     */
    private static final boolean isAdminRole = true ;
    /**
     *This method is the constructor.
     * @param user
     */
    public Admin(Users user) {
        super(user.toJson());
    }
    /**
     * This method checks the admintrator's privileges.
     * @param admin The administrator.
     * @return true if the the user is administrator.
     */
    public boolean Role(Admin admin ){
        return  admin.isAdminRole;
    }
}
