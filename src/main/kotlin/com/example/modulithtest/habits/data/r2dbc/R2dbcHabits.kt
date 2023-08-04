package com.example.modulithtest.habits.data.r2dbc

import com.example.modulithtest.ddd.Repository
import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.Habits
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.reactive.TransactionalEventPublisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Repository
class R2dbcHabits(
    private val repository: R2dbcHabitRepository,
    private val events: ApplicationEventPublisher
) : Habits {
  override fun findAll(): Flux<Habit> = repository.findAll().map(R2dbcHabit::toAggregate)

  override fun save(habit: Habit): Mono<Habit> {
    val transactionalEvents = TransactionalEventPublisher(events)
    val publishedEvents =
        Flux.concat(habit.domainEvents.map { transactionalEvents.publishEvent(it) })

    return repository
        .save(R2dbcHabit.fromAggregate(habit))
        .flatMapMany { publishedEvents }
        .then(habit.toMono())
  }
}
