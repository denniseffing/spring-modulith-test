package com.example.modulithtest.habits

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.util.UUID

@RestController
class HabitController {

  @GetMapping("/habits")
  fun getHabits(): Flux<Habit> =
      listOf(
              Habit(
                  Habit.Id(UUID.randomUUID()),
                  Habit.Name("Jogging"),
                  Schedule(Schedule.Frequency.DAILY, Schedule.Repetitions(5))))
          .toFlux()
}
