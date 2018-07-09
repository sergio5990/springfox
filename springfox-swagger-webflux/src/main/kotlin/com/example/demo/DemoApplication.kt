package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class DemoApplication {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@RestController
@RequestMapping("/api")
class Rest {
    val storage = arrayListOf<String>()

    @GetMapping("/all")
    fun gelAll(): List<String> {
        return storage
    }

    @PostMapping("/add")
    fun add(@RequestParam name: String): Boolean {
        return storage.add(name)
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam name: String): Boolean {
        return storage.remove(name)
    }
}
