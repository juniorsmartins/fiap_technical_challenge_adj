package cucumber.config;

import br.com.fiap.tech.challenge_user.ChallengeUserApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/acceptanceTest/resources/features", "classpath:features"},
        glue = {"cucumber.config",
                "cucumber.steps"},
        plugin = {
                "pretty",
                "html:src/acceptanceTest/cucumber-reports/cucumber.html", // Relatório HTML
                "json:src/acceptanceTest/cucumber-reports/cucumber.json" // Relatório JSON
        },
        monochrome = true,
        snippets = CucumberOptions.SnippetType.UNDERSCORE,
        dryRun = false // Defina como true para verificar se todos os steps estão implementados sem executar os testes
)
@CucumberContextConfiguration
@SpringBootTest(
        classes = ChallengeUserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EmbeddedKafka(partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
public class CucumberRunnerTest {

        @Autowired
        private EmbeddedKafkaBroker embeddedKafkaBroker;
}

