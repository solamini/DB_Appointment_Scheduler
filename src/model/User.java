package model;

/** This class creates the User object.
 * @author Aleksandr Ogilba */
public class User {
    private int userId;
    private String userName;
    private String password;

    /** This is an Empty Constructor */
    public User(){
    }

/** This is a constructor that is used to create a User object.
 * @param userId User ID
 * @param userName Username
 * @param password Password */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** Returns the user ID from the User object.
     * @return user ID*/
    public int getUserId(){
        return userId;
    }

    /** Sets the input user ID to the User object.
     * @param userId User ID */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /** Returns the username from the User object.
     * @return username */
    public String getUserName() {
        return userName;
    }

    /** Sets the input username to the User object.
     * @param userName Username */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /** Returns the user password from the User object.
     * @return user password */
    public String getPassword() {
        return password;
    }

    /** Sets the input user's password to the User object.
     * @param password Password */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return(String.valueOf(userId));
    }

}
