package controllers;

import constants.RouteConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.DataCardRepository;

import java.util.Map;

@RestController
@CrossOrigin(RouteConstants.ORIGIN)
public class DataCardController {

    @Autowired
    private DataCardRepository dataCardRepository;



    @RequestMapping(value = RouteConstants.DATACARD_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<String> registerDataCard(@RequestBody Map<String, Object> payload) {
        System.out.println(payload.keySet());


        return ResponseEntity.status(HttpStatus.OK).body("Message received");
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> testGet() {

        return ResponseEntity.status(HttpStatus.OK).body("Message received in GET");
    }
}
