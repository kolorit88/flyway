package org.example.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["infrastructure.adapter.persistence.jpa.repository"])
@ComponentScan(basePackages = [
    "infrastructure.adapter.controller",
    "application.service",
    "infrastructure.adapter.persistence",
    "shared.utils.mapper",
    "infrastructure.exception"
])
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
