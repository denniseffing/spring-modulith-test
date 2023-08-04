package com.example.modulithtest.habits

import com.example.modulithtest.ddd.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HabitManagement(val habits: Habits) {

  @Transactional("connectionFactoryTransactionManager")
  fun saveHabit(habit: Habit): Mono<Habit> {
    return habits.save(habit)
  }

  fun findAll(): Flux<Habit> = habits.findAll()
}
