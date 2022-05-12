package model;

/** This class creates the Contact object. */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;


    public Contact() {
    }

    /** This is a constructor that is used to create a Contact object.
     * @param contactID
     * @param contactName
     * @param contactEmail */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /** Returns the contact ID from the Contact object.
     * @return contact ID*/
    public int getContactID() {
        return contactID;
    }

    /** Sets the input contact ID to the Contact object.
     * @param contactID */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Returns the contact name from the Contact object.
     * @return contact Name*/
    public String getContactName() {
        return contactName;
    }

    /** Sets the input contact name to the Contact object.
     * @param contactName */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Returns the contact email from the Contact object.
     * @return contact email*/
    public String getContactEmail() {
        return contactEmail;
    }

    /** Sets the input contact email to the Contact object.
     * @param contactEmail */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString(){
        return(contactName);
    }

}
