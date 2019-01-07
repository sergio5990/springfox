package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import springfox.documentation.swagger2.annotations.EnableSwaggerWebFlux
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import java.time.LocalDate

@EnableSwaggerWebFlux
@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
  runApplication<DemoApplication>(*args)
}

@Configuration
class PersonRouter {
  @Bean
  fun route(): RouterFunction<ServerResponse> {
    val storage = arrayListOf<String?>()
    return RouterFunctions.route(
        GET("/api/all"),
        HandlerFunction {
          ok().contentType(APPLICATION_JSON).body(storage.toMono())
        }
    ).andRoute(
        GET("/api/all"),
        HandlerFunction {
          val name = it.formData().block()?.get("name")?.get(0)
          storage.add(name)
          ok().contentType(APPLICATION_JSON).body("ok".toMono())
        }
    )
  }
}

@RestController
@RequestMapping("/api")
class Rest {
  val storage = arrayListOf<String>()

  @GetMapping("/all1")
  fun gelAll(): Mono<List<String>> {
    return storage.toMono()
  }

  @PostMapping("/add")
  fun add(@RequestParam name: String): Mono<Boolean> {
    return storage.add(name).toMono()
  }
  @PostMapping("/body")
  fun add(@RequestBody body: Body): Mono<Body> {
    return body.toMono()
  }

  @DeleteMapping("/delete")
  fun delete(@RequestParam name: String): Mono<Boolean> {
    return storage.remove(name).toMono()
  }
}

data class Body(val date: LocalDate, val name: String)