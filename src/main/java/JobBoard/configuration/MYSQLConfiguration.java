package JobBoard.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mysql.properties")
public class MYSQLConfiguration {
}
