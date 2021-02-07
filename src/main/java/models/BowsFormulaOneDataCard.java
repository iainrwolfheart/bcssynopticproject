package models;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dataCards")
@TypeAlias("bowsFormulaOneDataCard")
public class BowsFormulaOneDataCard extends DataCard {

    private String empId;

    public BowsFormulaOneDataCard(String empId, String name, String email, String mobileNumber,
                                  String pin, int amountInPence) {
        super(name, email, mobileNumber, pin, amountInPence);
        this.empId = empId;
    }
    public BowsFormulaOneDataCard(String cardId, String empId, String name, String email, String mobileNumber,
                                  String pin, Balance balance) {
        super(cardId, name, email, mobileNumber, pin, balance);
        this.empId = empId;
    }

    public BowsFormulaOneDataCard(String empId) {
        this.empId = empId;
    }

    public BowsFormulaOneDataCard() {}

    public String getEmpId() {
        return empId;
    }

    @Override
    public String toString() {
        return "BowsFormulaOneDataCard{" +
                "cardId='" + super.getCardId() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", mobileNumber='" + super.getMobileNumber() + '\'' +
                ", pin='" + super.getPin() + '\'' +
                ", balance='" + super.getBalance().toString() +'\'' +
                "empId='" + empId + '\'' +
                '}';
    }
}
