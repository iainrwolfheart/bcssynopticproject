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
import services.JwtService;

import java.util.Map;

@RestController
@CrossOrigin(RouteConstants.ORIGIN)
public class DataCardController<T extends DataCard> {

    @Autowired
    private DataCardRepository dataCardRepository;

    private EncryptionService encryptionService = new EncryptionService();

    private JwtService jwtService = new JwtService();

    @PostMapping(RouteConstants.DATACARD_ENDPOINT)
    public ResponseEntity<String> registerDataCard(@RequestBody Map<String, Object> payload) {
        BowsFormulaOneDataCard newRegistrationDetails;

        try {
            if (!jwtService.validateToken(payload.get("token").toString())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header("Content-Type", "application/json")
                        .body("'Error': 'Session has timed out.'");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "application/json")
                    .body("'Error': 'Failed to authenticate token'");
        }

        try {
            if (!DataCard.validatePinFormat(payload.get("PIN").toString())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body("'error': 'Incorrect PIN formatting'");
            }

            String encryptedPin = encryptionService.encryptUserEntry(payload.get("PIN").toString());

            newRegistrationDetails = new BowsFormulaOneDataCard(payload.get("empId").toString(),
                    payload.get("name").toString(), payload.get("email").toString(), payload.get("mobileNumber").toString(),
                    encryptedPin, (int) payload.get("balance"));
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("'error': 'Invalid request keys.'");
        }

        if (dataCardRepository.findByEmpId(newRegistrationDetails.getEmpId()) != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .header("Content-Type", "application/json")
                    .body("'message': 'User with that Employee Id already exists'");
        }
        else {
            dataCardRepository.save(newRegistrationDetails);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body("'balance': '"
                    + newRegistrationDetails.getBalance().getAmountInPence() + "', " +
                    "'token': '" + jwtService.generateToken(newRegistrationDetails) + "'");
        }
    }

    @PostMapping(RouteConstants.DATACARD_ENDPOINT + "{empId}")
    public ResponseEntity<String> pinEntry(@RequestBody Map<String, Object> payload, @PathVariable String empId) {

        try {
            if (!jwtService.validateToken(payload.get("token").toString(), dataCardRepository.findByEmpId(empId))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header("Content-Type", "application/json")
                        .body("'Error': 'Session has timed out.'");
            }

            BowsFormulaOneDataCard retrievedDetails = dataCardRepository.findByEmpId(empId);
            if (!encryptionService.isCorrectUserEntry(payload.get("PIN").toString(), retrievedDetails.getPin())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                        header("Content-Type", "application/json")
                        .body("'Error': 'Incorrect PIN entry.'");
            }
            else {
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body("'name': '" + retrievedDetails.getName() + "', " +
                        "'balanceInPence': '"
                        + retrievedDetails.getBalance().getAmountInPence() + "', " +
                        "'token': '" + jwtService.generateToken(retrievedDetails) + "'");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "application/json")
                    .body("'Error': 'Session has timed out.'");
        }
    }
}
