package services;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class EncryptionService {

    StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

    public String encryptUserEntry(String entry) {

        return encryptor.encryptPassword(entry);
    }

    public boolean isCorrectUserEntry(String userEntry, String storedValue) {

        return encryptor.checkPassword(userEntry, storedValue);
    }

}