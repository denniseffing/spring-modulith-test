package com.example.modulithtest.habits

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface Habits {

  fun findAll(): Flux<Habit>
  fun save(habit: Habit): Mono<Habit>
}
