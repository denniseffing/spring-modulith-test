package com.example.modulithtest.habits

import com.example.modulithtest.ddd.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HabitManagement(val habits: Habits) {

  fun saveHabit(habit: Habit): Mono<Habit> {
    return habits.save(habit)
  }

  fun findAll(): Flux<Habit> = habits.findAll()
}
