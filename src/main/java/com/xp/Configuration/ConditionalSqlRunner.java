package com.xp.Configuration;

import com.xp.Repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class ConditionalSqlRunner implements CommandLineRunner {

    private final MovieRepository movieRepo;
    private final DataSource dataSource;

    public ConditionalSqlRunner(MovieRepository movieRepo, DataSource dataSource) {
        this.movieRepo = movieRepo;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean needsInit = false;
        try {
            if (movieRepo.count() > 0) {
                return;
            }
        } catch (Exception e) {
            needsInit = true;
        }

        if (!needsInit) {
            return;
        }

        Resource resource = new ClassPathResource("init-data.sql");
        if (!resource.exists()) return;

        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        String[] statements = sql.split("(?m);\\s*$");

        try (Connection conn = DataSourceUtils.getConnection(dataSource);
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);

            for (String raw : statements) {
                String s = raw.trim();
                if (s.isEmpty()) continue;
                stmt.execute(s);
            }

            conn.commit();
        } catch (Exception ex) {
            throw ex;
        }
    }
}