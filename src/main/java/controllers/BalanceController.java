package controllers;

import constants.RouteConstants;
import models.BowsFormulaOneDataCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.DataCardRepository;
import services.JwtService;

@RestController
public class BalanceController {

    @Autowired
    private DataCardRepository dataCardRepository;

    @Autowired
    private JwtService jwtService;

    @PutMapping(RouteConstants.BALANCE_ENDPOINT + "{empId}/{amountInPence}")
    public ResponseEntity<String> updateCardBalance(@PathVariable String empId, @PathVariable int amountInPence,
                                                    @RequestHeader("Authorization") String token) {
        BowsFormulaOneDataCard retrievedDetails;

        retrievedDetails = dataCardRepository.findByEmpId(empId);

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

        if (!retrievedDetails.getBalance().updateAmountInPence(amountInPence)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .header("Content-Type", "application/json")
                    .body("'error': 'Unable to process request, not enough funds available.'");
        }
        else {
            dataCardRepository.save(retrievedDetails);

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtService.generateToken(retrievedDetails))
                    .body("'name': '" + retrievedDetails.getName() + "', " +
                    "'balanceInPence': '" + retrievedDetails.getBalance().getAmountInPence() + "'");
        }
    }
}
