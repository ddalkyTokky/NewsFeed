package com.noreabang.strawberryrabbit

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@OpenAPIDefinition(servers = [Server(url = "https://localhost") ])
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableJpaAuditing
class StrawberryrabbitApplication

fun main(args: Array<String>) {
	runApplication<StrawberryrabbitApplication>(*args)
}
