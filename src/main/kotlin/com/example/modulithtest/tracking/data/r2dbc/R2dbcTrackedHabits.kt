package com.example.modulithtest.tracking.data.r2dbc

import com.example.modulithtest.ddd.Repository
import com.example.modulithtest.tracking.TrackedHabit
import com.example.modulithtest.tracking.data.TrackedHabits
import reactor.core.publisher.Mono

@Repository
class R2dbcTrackedHabits(val repository: R2dbcTrackedHabitRepository) : TrackedHabits {
  override fun save(habit: TrackedHabit): Mono<TrackedHabit> =
      repository.save(R2dbcTrackedHabit.fromAggregate(habit)).map { habit }

  override fun findById(id: TrackedHabit.Id): Mono<TrackedHabit> =
      repository.findById(id.value).map(R2dbcTrackedHabit::toAggregate)
}
