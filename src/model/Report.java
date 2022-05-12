package model;

/** This class creates the Report object. */
public class Report {
    private String repMonth;
    private String repType;
    private String repAmount;

    public Report(){
    }

    /** This is a constructor that is used to create a Report object.
     * @param month
     * @param type
     * @param amount */
    public Report (String month, String type, String amount) {
        this.repMonth = month;
        this.repType = type;
        this.repAmount = amount;
    }

    /** Sets the input month to the Report object.
     * @param repMonth*/
    public void setRepMonth(String repMonth) {
        this.repMonth = repMonth;
    }

    /** Returns the month of appointment from the Report object.
     * @return report month*/
    public String getRepMonth() {
        return repMonth;
    }

    /** Sets the input type to the Report object.
     * @param repType*/
    public void setRepType(String repType) {
        this.repType = repType;
    }

    /** Returns the type of appointment from the Report object.
     * @return report month*/
    public String getRepType() {
        return repType;
    }

    /** Sets the input amount to the Report object.
     * @param repAmount*/
    public void setRepAmount(String repAmount) {
        this.repAmount = repAmount;
    }

    /** Returns the month from the Report object.
     * @return amount of appointments found */
    public String getRepAmount() {
        return repAmount;
    }
}
