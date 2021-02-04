package repositories;

import models.BowsFormulaOneDataCard;
import models.DataCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCardRepository<T extends DataCard> extends MongoRepository<T, String> {

    T findByCardId(String cardId);

    BowsFormulaOneDataCard findByEmpId(String empId);

}
