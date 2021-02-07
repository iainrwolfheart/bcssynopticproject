package services;

import models.DataCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DataCardRepository;

@Service
public class DataCardService {

    @Autowired
    private DataCardRepository dataCardRepository;

    public boolean isCardRegistered(String empId) {
        return dataCardRepository.findByEmpId(empId) != null;
    }

    public DataCard getDataCard(String empId) {
        return dataCardRepository.findByEmpId(empId);
    }
}
