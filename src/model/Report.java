package model;

/** This class creates the Report object.
 *  @author Aleksandr Ogilba*/
public class Report {
    private String repMonth;
    private String repType;
    private String repAmount;

    /** This is an Empty Constructor */
    public Report(){
    }

    /** This is a constructor that is used to create a Report object.
     * @param month Month of Appointment in Report
     * @param type Type of Appointment in Report
     * @param amount Amount of times Appointment occurs in month with type */
    public Report (String month, String type, String amount) {
        this.repMonth = month;
        this.repType = type;
        this.repAmount = amount;
    }

    /** Sets the input month to the Report object.
     * @param repMonth Month of Appointment in Report */
    public void setRepMonth(String repMonth) {
        this.repMonth = repMonth;
    }

    /** Returns the month of appointment from the Report object.
     * @return report month*/
    public String getRepMonth() {
        return repMonth;
    }

    /** Sets the input type to the Report object.
     * @param repType Type of Appointment in Report */
    public void setRepType(String repType) {
        this.repType = repType;
    }

    /** Returns the type of appointment from the Report object.
     * @return report month*/
    public String getRepType() {
        return repType;
    }

    /** Sets the input amount to the Report object.
     * @param repAmount Amount of times Appointment occurs in month with type */
    public void setRepAmount(String repAmount) {
        this.repAmount = repAmount;
    }

    /** Returns the month from the Report object.
     * @return amount of appointments found */
    public String getRepAmount() {
        return repAmount;
    }
}
