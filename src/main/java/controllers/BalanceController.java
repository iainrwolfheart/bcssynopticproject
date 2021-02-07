package controllers;

import constants.RouteConstants;
import models.DataCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repositories.DataCardRepository;

import java.util.Map;

@RestController
public class BalanceController {

    @Autowired
    private DataCardRepository dataCardRepository;

    @PutMapping(RouteConstants.BALANCE_ENDPOINT + "{empId}")
    public ResponseEntity<String> updateCardBalance(@PathVariable String empId,
                                                    @RequestBody Map<String, String> payload) {
        DataCard retrievedDetails;

        try {
            retrievedDetails = dataCardRepository.findByEmpId(empId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "application/json")
                    .body("'error': 'Employee ID does not exist in the " +
                    "database. Please register your card.'");
        }

        try {
            if (!retrievedDetails.getBalance().updateAmountInPence(Integer.parseInt(payload.get("amountInPence")))) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .header("Content-Type", "application/json")
                        .body("'error': 'Unable to process request, not enough funds available.'");
            } else {
                dataCardRepository.save(retrievedDetails);

                return ResponseEntity.status(HttpStatus.OK).body("'name': '" + retrievedDetails.getName() + "', " +
                        "'balanceInPence': '"
                        + retrievedDetails.getBalance().getAmountInPence() + "', " +
                        "'token': '" + "placeholder token string" + "'");
            }
        } catch (NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("'error': 'Invalid request keys.'");
        }
    }
}
