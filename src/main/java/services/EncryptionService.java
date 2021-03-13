package services;

import models.DataCard;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService<T extends DataCard> {

    StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

    public String encryptUserEntry(String entry) {

        return encryptor.encryptPassword(entry);
    }

    public boolean isCorrectUserEntry(String userEntry, String storedValue) {

        return encryptor.checkPassword(userEntry, storedValue);
    }

    public T encryptNewRegistration(T datacard) {
        datacard.setPin(encryptUserEntry(datacard.getPin()));
        return datacard;
    }

}