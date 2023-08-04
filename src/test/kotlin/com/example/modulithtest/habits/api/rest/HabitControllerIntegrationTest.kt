@file:Suppress("ReactiveStreamsUnusedPublisher")

package com.example.modulithtest.habits.api.rest

import com.example.modulithtest.habits.HabitManagement
import com.example.modulithtest.habits.TestFixtures.JoggingHabit
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [HabitController::class])
@AutoConfigureWebTestClient
class HabitControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

  @MockkBean private lateinit var habitManagement: HabitManagement

  @Test
  fun `should return habits`() {
    every { habitManagement.findAll() } returns Flux.just(JoggingHabit)

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
    every { habitManagement.saveHabit(any()) } returns Mono.just(JoggingHabit)

    webTestClient
        .post()
        .uri("/habits")
        .bodyValue(HabitResource("Jogging", FrequencyResource.DAILY, 5))
        .exchange()
        .expectStatus()
        .isCreated()

    verify { habitManagement.saveHabit(any()) }
  }
}
