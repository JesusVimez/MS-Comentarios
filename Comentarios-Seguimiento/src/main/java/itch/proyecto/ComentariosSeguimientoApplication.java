package itch.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "itch.proyecto",                // Tu microservicio actual
    "com.auth.client_sdk",          // Tu SDK
    "com.ayuntamiento.security_lib" // Tu librería de seguridad
})
@EnableDiscoveryClient
@EnableFeignClients
public class ComentariosSeguimientoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComentariosSeguimientoApplication.class, args);
    }
}