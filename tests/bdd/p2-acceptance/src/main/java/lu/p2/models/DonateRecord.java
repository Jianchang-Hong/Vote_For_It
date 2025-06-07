package lu.p2.models;

public class DonateRecord {

    private String charityName;
    private String themeName;
    private String donateDateTime;
    private String amount;
    private String message;

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(final String charityName) {
        this.charityName = charityName;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(final String themeName) {
        this.themeName = themeName;
    }

    public String getDonateDateTime() {
        return donateDateTime;
    }

    public void setDonateDateTime(final String donateDateTime) {
        this.donateDateTime = donateDateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
