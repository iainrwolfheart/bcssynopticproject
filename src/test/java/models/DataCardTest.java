package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataCardTest {

    DataCard testDataCard = new DataCard("Bowsman Jackson",
            "jacks@formula1.com","07777 999 111","4444",
            1000);
    String testCardId = testDataCard.getCardId();

    DataCard testInValidPinDataCard = new DataCard("Bowsman Jackson",
            "jacks@formula1.com","07777 999 111","444A4",
            1000);

    @Test
    void shouldGenerateNewCardIdOfDefinedLength() {
        assertTrue(testCardId.length() == 16);
    }

    @Test
    void pinCanOnlyBeNumeric() {
        Boolean isValidPin = DataCard.validatePinFormat(testInValidPinDataCard.getPin());
        assertFalse(isValidPin);
    }

    @Test
    void pinCanOnlyContainFourNumbers() {
        Boolean isValidPin = DataCard.validatePinFormat(testInValidPinDataCard.getPin());
        assertFalse(isValidPin);
    }
}