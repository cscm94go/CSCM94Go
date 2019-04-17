package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("users")

public class Users extends Model {

    public void setUserName(String userName){
        set("username", userName);
    }
    public String getUserName() {
        return getString("username");
    }
    public void image(String image) {
        get("").toString();
        set().toString(); }
}
