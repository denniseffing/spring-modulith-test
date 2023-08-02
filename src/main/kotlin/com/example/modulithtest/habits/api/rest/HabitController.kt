package com.example.modulithtest.habits.api.rest

import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.HabitManagement
import com.example.modulithtest.habits.Schedule
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/habits")
class HabitController(val habitManagement: HabitManagement) {

  @GetMapping
  fun getHabits(): Flux<HabitResource> = habitManagement.findAll().map(Habit::toHabitResource)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createHabit(@RequestBody habitResource: Mono<HabitResource>): Mono<Void> =
      habitResource.map(HabitResource::toHabit).flatMap { habitManagement.saveHabit(it) }.then()
}

data class HabitResource(val name: String, val frequency: FrequencyResource, val repetitions: Int) {

  fun toHabit(): Habit {
    val name = Habit.Name(name)
    val schedule =
        Schedule(Schedule.Frequency.valueOf(frequency.name), Schedule.Repetitions(repetitions))

    return Habit(name = name, schedule = schedule)
  }
}

fun Habit.toHabitResource(): HabitResource {
  return HabitResource(
      name.value, FrequencyResource.valueOf(schedule.frequency.name), schedule.repetitions.value)
}

enum class FrequencyResource {
  DAILY,
  WEEKLY,
  MONTHLY,
  YEARLY
}
