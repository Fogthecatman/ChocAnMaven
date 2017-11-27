package model;

/**
 * Created by Jacob on 11/9/17.
 */
public class User {

    private String permissionLevel;
    private int userID;

    public User() {
        permissionLevel = "";
        userID = 0;
    }

    public User(String permissionLevel) {
        this.permissionLevel = permissionLevel;
        userID = 0;
    }

    public User(String permissionLevel, int userID) {
        this.permissionLevel = permissionLevel;
        this.userID = userID;
    }


    public String getPermissionLevel() {
        return permissionLevel;
    }

    public int getUserID() {
        //@TODO Are we storing userID's in DB without leading zeroes?
        return userID;
    }

}
