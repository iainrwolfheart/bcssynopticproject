package controllers;

import constants.RouteConstants;
import models.BowsFormulaOneDataCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.DataCardRepository;
import services.DataCardService;
import services.JwtService;

@RestController
public class SessionController {

    @Autowired
    private DataCardRepository dataCardRepository;

    private JwtService jwtService = new JwtService();

    @Autowired
    private DataCardService dataCardService;

    @GetMapping(RouteConstants.SESSION_ENDPOINT + "{empId}")
    public ResponseEntity<String> startSession(@PathVariable String empId) {

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("'isRegistered': '" + dataCardService.isCardRegistered(empId) + "', " +
                "'token': '" + jwtService.generateToken(new BowsFormulaOneDataCard(empId)) + "'");
    }

    @PostMapping(value = RouteConstants.SESSION_ENDPOINT + "{empId}")
    public ResponseEntity<String> endSession(@PathVariable String empId, @RequestBody String token) {

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("'message': 'Goodbye'");
    }
}
