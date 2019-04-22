package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    int adminNumber;

    public Admin(String userName, Date jDate, int adminNo) {
        Users u = new Users();
        u.username = userName;
        joinDate = jDate;
        adminNumber = adminNo;
    }
    public boolean Role(){
        return  adminRole;
    }

    /**
     * Save user to local file
     */
    public void store()  {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("admin/" + username + adminNumber + ".json"));
            writer.write(toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
