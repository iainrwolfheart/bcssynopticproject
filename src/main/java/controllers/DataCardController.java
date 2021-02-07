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

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(RouteConstants.DATACARD_ENDPOINT)
    public ResponseEntity<String> registerDataCard(@RequestHeader("Authorization") String token,
                                                   @RequestBody BowsFormulaOneDataCard newRegistrationDetails) {
        System.out.println(newRegistrationDetails.toString());
        try {
            if (!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header("Content-Type", "application/json")
                        .body("'Error': 'Session has timed out.'");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "application/json")
                    .body("'Error': 'Failed to authenticate token'");
        }

            if (!DataCard.validatePinFormat(newRegistrationDetails.getPin())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body("'error': 'Incorrect PIN formatting'");
            }

            newRegistrationDetails.setCardId();
            newRegistrationDetails = encryptionService.encryptNewRegistration(newRegistrationDetails);

        if (dataCardRepository.findByEmpId(newRegistrationDetails.getEmpId()) != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .header("Content-Type", "application/json")
                    .body("'message': 'User with that Employee Id already exists'");
        }
        else {
            dataCardRepository.save(newRegistrationDetails);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtService.generateToken(newRegistrationDetails))
                    .body("'balance': '"
                    + newRegistrationDetails.getBalance().getAmountInPence() + "'");
        }
    }

    @PostMapping(RouteConstants.DATACARD_ENDPOINT + "{empId}")
    public ResponseEntity<String> pinEntry(@RequestHeader("Authorization") String token,
                                           @RequestBody Map<String, Object> payload, @PathVariable String empId) {

            if (!jwtService.validateToken(token)) {
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
                        .header("Authorization",jwtService.generateToken(retrievedDetails))
                        .body("'name': '" + retrievedDetails.getName() + "', " +
                        "'balanceInPence': '"
                        + retrievedDetails.getBalance().getAmountInPence());
            }
    }
}
