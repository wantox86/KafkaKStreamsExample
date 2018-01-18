package id.netzme.kafkastreamkstream.model;

public class TopupTest {
    private String topupID;
    private String topupName;

    public String getTopupID() {
        return topupID;
    }

    public void setTopupID(String topupID) {
        this.topupID = topupID;
    }

    public String getTopupName() {
        return topupName;
    }

    public void setTopupName(String topupName) {
        this.topupName = topupName;
    }

    public int getTopupAmount() {
        return topupAmount;
    }

    public void setTopupAmount(int topupAmount) {
        this.topupAmount = topupAmount;
    }

    private int topupAmount;

}
