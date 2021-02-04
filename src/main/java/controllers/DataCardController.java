package controllers;

import constants.RouteConstants;
import models.BowsFormulaOneDataCard;
import models.DataCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.DataCardRepository;
import services.EncryptionService;

import java.util.Map;

@RestController
@CrossOrigin(RouteConstants.ORIGIN)
public class DataCardController {

    @Autowired
    private DataCardRepository dataCardRepository;

    private EncryptionService encryptionService = new EncryptionService();

    @PostMapping(RouteConstants.DATACARD_ENDPOINT)
    public ResponseEntity<String> registerDataCard(@RequestBody Map<String, Object> payload) {
        System.out.println(payload.values().toString());

        String encryptedPin = encryptionService.encryptUserEntry(payload.get("PIN").toString());

        BowsFormulaOneDataCard newRegistrationDetails = new BowsFormulaOneDataCard(payload.get("empId").toString(),
                payload.get("name").toString(), payload.get("email").toString(), payload.get("mobileNumber").toString(),
                encryptedPin, (int) payload.get("balance"));

        if (dataCardRepository.findByEmpId(newRegistrationDetails.getEmpId()) != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("User with that email already exists");
        }
        else {
            dataCardRepository.save(newRegistrationDetails);

            return ResponseEntity.status(HttpStatus.OK).body("balance: "
                    + newRegistrationDetails.getBalance().getAmountInPence() + ", " +
                    "token: " + "placeholder token string");
        }
    }

    @PostMapping(RouteConstants.DATACARD_ENDPOINT + "/{empId}")
    public ResponseEntity<String> pinEntry(@RequestBody Map<String, Object> payload, @PathVariable String empId) {

        DataCard retrievedDetails;

        System.out.println(dataCardRepository.findByEmpId(empId));

        try {
            retrievedDetails = dataCardRepository.findByEmpId(empId);
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee ID does not exist in the " +
                    "database. Please register your card.");
        }

        if (!encryptionService.isCorrectUserEntry(payload.get("PIN").toString(), retrievedDetails.getPin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect PIN entry.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("name: " + retrievedDetails.getName() + ", " +
                    "balanceInPence: "
                    + retrievedDetails.getBalance().getAmountInPence() + ", " +
                    "token: " + "placeholder token string");
        }
    }
}
