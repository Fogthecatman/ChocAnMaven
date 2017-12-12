package model;

/**
 * Created by Jacob on 11/9/17.
 */
public class User {

    private String permissionLevel;
    private int userID;
    private String name;

    private static User u;

    public User() {
        permissionLevel = "";
        userID = 0;
        name = "";
    }


    public static User getInstance() {
        if(u == null) {
            u = new User();
        }
        return u;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setID(int id) {
        this.userID = id;
    }

    public void setPermissionLevel(String permissionLevel) { this.permissionLevel = permissionLevel; }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public int getUserID() {
        //@TODO Are we storing userID's in DB without leading zeroes?
        return userID;
    }

}
