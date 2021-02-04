package services;

import java.util.ArrayList;
import java.util.List;

public class CardIdService {

    public static List<Character> generateAlphanumericList() {

        ArrayList<Character> alphanumericCharacters = new ArrayList<>();

        for (char c = 48; c <= 122; c++) {
            if (Character.isDigit(c) || Character.isLetter(c)) {
                alphanumericCharacters.add(c);
            }
        }

        return alphanumericCharacters;
    }

}
