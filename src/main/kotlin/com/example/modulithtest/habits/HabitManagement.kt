package com.example.modulithtest.habits

import com.example.modulithtest.ddd.Service
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HabitManagement(
    val habits: Habits,
    val applicationEventPublisher: ApplicationEventPublisher
) {

  @Transactional("connectionFactoryTransactionManager")
  fun saveHabit(habit: Habit): Mono<Habit> {
    return habits.save(habit).doOnNext {
      it.domainEvents.forEach { event -> applicationEventPublisher.publishEvent(event) }
    }
  }

  fun findAll(): Flux<Habit> = habits.findAll()
}
