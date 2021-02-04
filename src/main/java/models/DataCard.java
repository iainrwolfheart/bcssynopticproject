package models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import services.CardIdService;

import java.util.List;
import java.util.Random;

@Document(collection = "dataCards")
public class DataCard {

    @Id
    private String cardId;
    private String name;
    private String email;
    private String mobileNumber;
    private String pin;
    private Balance balance;

    public DataCard(String name, String email, String mobileNumber, String pin, int amountInPence) {
        this.cardId = generateCardId();
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
        this.balance = new Balance(amountInPence);
    }

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

    public Balance getBalance() {
        return balance;
    }

    public String generateCardId() {

        List<Character> charList = CardIdService.generateAlphanumericList();
        int cardIdDefinedLength = 16;
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
                ", balance=" + balance.toString() +
                '}';
    }
}
