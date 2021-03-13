package models;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "dataCards")
@TypeAlias("bowsFormulaOneDataCard")
public class BowsFormulaOneDataCard extends DataCard {

    private String empId;

    // Registering without CardId
    public BowsFormulaOneDataCard(String empId, String name, String email, String mobileNumber,
                                  String pin, int amountInPence) {
        super(name, email, mobileNumber, pin, amountInPence);
        this.empId = empId;
        System.out.println("CONSTRUCTOR 2");
    }
//    Registering w/o initial balance
    public BowsFormulaOneDataCard(String empId, String name, String email, String mobileNumber,
                                  String pin) {
        super(name, email, mobileNumber, pin);
        this.empId = empId;
        System.out.println("CONSTRUCTOR 3");
    }
    // Login w/EmpId and PIN
    public BowsFormulaOneDataCard(String empId, String pin) {
        super(pin);
        this.empId = empId;
    }
//    Start Session
    public BowsFormulaOneDataCard(String empId) {
        this.empId = empId;
        System.out.println("CONSTRUCTOR 4");
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
                ", balance='" + super.getBalanceInPence() +'\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
