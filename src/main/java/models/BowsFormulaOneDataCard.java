package models;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dataCards")
@TypeAlias("bowsFormulaOneDataCard")
public class BowsFormulaOneDataCard extends DataCard {

    private String empID;

    public BowsFormulaOneDataCard(String empID, String name, String email, String mobileNumber,
                                  String pin, int amountInPence) {
        super(name, email, mobileNumber, pin, amountInPence);
        this.empID = empID;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    @Override
    public String toString() {
        return "BowsFormulaOneDataCard{" +
                "cardId='" + super.getCardId() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", mobileNumber='" + super.getMobileNumber() + '\'' +
                ", pin=" + super.getPin() +
                ", balance=" + super.getBalance().toString() +'\'' +
                "empID='" + empID + '\'' +
                '}';
    }
}
