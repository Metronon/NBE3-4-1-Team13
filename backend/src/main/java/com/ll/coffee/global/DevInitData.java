package com.ll.coffee.global;

import com.ll.coffee.standard.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@RequiredArgsConstructor
public class DevInitData {
    @Autowired
    @Lazy
    private DevInitData self;

    @Bean
    public ApplicationRunner devInitDataApplicationRunner() {
        return args -> {
            Ut.file.downloadByHttp("http://localhost:8080/v3/api-docs", ".");

            String cmd = "yes | npx --package typescript --package openapi-typescript openapi-typescript api-docs.json -o ../frontend/src/lib/backend/apiV1/schema.d.ts\n";
            try {
                ProcessBuilder builder = new ProcessBuilder("C:\\Program Files\\Git\\bin\\bash.exe", "-c", cmd);
                builder.redirectErrorStream(true);
                Process process = builder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                int exitCode = process.waitFor();
                System.out.println("Command exited with code: " + exitCode);

                File schemaFile = new File("../frontend/src/lib/backend/apiV1/schema.d.ts");
                if (schemaFile.exists()) {
                    System.out.println("Schema file was created successfully.");
                } else {
                    System.out.println("Schema file was not created.");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
