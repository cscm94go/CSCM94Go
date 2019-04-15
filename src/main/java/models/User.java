package models;

import java.sql.Struct;

public class User {
    String username;
    String firstName;
    String lastName;
    int Credit;
    int wins;
    int losses;
    float winlossPercentage;
    String profileImage;
    Struct currentLoginTime;
    Struct lastLoginTime;
}
