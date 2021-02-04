import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages= {"controllers", "repositories"})
@EnableMongoRepositories("repositories")
public class BcsSynopticProject {

    public static void main(String[] args) {

        SpringApplication.run(BcsSynopticProject.class, args);

    }
}
