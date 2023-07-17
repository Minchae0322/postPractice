package hodol.hodolpractice.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hodol.hodolpractice.response.ErrorResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    public EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }


}
