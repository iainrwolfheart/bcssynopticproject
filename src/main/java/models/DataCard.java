package models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import services.CardIdService;

import java.util.List;
import java.util.Random;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataCard.class, name = "standard"),
        @JsonSubTypes.Type(value = BowsFormulaOneDataCard.class, name = "bows")})
public class DataCard {

    @Id
    private String cardId;
    private String name;
    private String email;
    private String mobileNumber;
    private String pin;
    private int balanceInPence;

    // Registering BowsF1...
    public DataCard(String name, String email, String mobileNumber, String pin, int balanceInPence) {
        this.cardId = generateCardId(16);
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
        this.balanceInPence = balanceInPence;
        System.out.println("CONSTRUCTOR 0");
    }
    // Registering BowsF1 w/o an initial balance
    public DataCard(String name, String email, String mobileNumber, String pin) {
        this.cardId = generateCardId(16);
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
        this.balanceInPence = 0;
        System.out.println("CONSTRUCTOR 1");
    }

//    Logging in BowsF1
    public DataCard(String pin) {
        this.pin = pin;
        System.out.println("CONSTRUCTOR 5");

    }

    public DataCard() {}

    public String getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalanceInPence() {
        return balanceInPence;
    }

    public String generateCardId(int cardIdDefinedLength) {

        List<Character> charList = CardIdService.generateAlphanumericList();
        StringBuilder cardIdBuilder = new StringBuilder();

        for (int i = 0; i < cardIdDefinedLength; i++) {
            cardIdBuilder.append(charList.get(new Random().nextInt(charList.size())));
        }

        return cardIdBuilder.toString();
    }

    @Override
    public String toString() {
        return "DataCard{" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", pin=" + pin +
                ", balance=" + balanceInPence +
                '}';
    }

    public static boolean validatePinFormat(String pin) {
        return pin.matches("[0-9]+") && pin.length() == 4;
    }

    public boolean updateBalanceInPence(int balanceInPence) {
        if (balanceInPence + this.balanceInPence < 0) return false;

        else {
            this.balanceInPence += balanceInPence;
            return true;
        }
    }

    public void setCardId() {
        this.cardId = generateCardId(16);
    };
}
