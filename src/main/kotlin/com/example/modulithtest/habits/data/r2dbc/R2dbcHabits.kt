package com.example.modulithtest.habits.data.r2dbc

import com.example.modulithtest.ddd.Repository
import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.Habits
import org.springframework.context.ApplicationEventPublisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class R2dbcHabits(
    private val repository: R2dbcHabitRepository,
    private val events: ApplicationEventPublisher
) : Habits {
  override fun findAll(): Flux<Habit> = repository.findAll().map(R2dbcHabit::toAggregate)

  override fun save(habit: Habit): Mono<Habit> {
    return repository
        .save(R2dbcHabit.fromAggregate(habit))
        .map { habit }
        .doOnNext { it.domainEvents.forEach { event -> events.publishEvent(event) } }
  }
}
