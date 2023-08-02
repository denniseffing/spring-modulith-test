package com.example.modulithtest.habits.api.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@ApplicationModuleTest
@AutoConfigureWebTestClient
class HabitControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

  @Test
  fun `should return habits`() {
    webTestClient
        .get()
        .uri("/habits")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody<List<HabitResource>>()
        .value { assertThat(it).hasSize(1) }
  }

  @Test
  fun `should create habits`() {
    webTestClient
        .post()
        .uri("/habits")
        .bodyValue(HabitResource("Jogging", FrequencyResource.DAILY, 5))
        .exchange()
        .expectStatus()
        .isCreated()
  }
}
