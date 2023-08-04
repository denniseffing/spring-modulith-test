package com.example.modulithtest.tracking.data

import com.example.modulithtest.tracking.TrackedHabit
import reactor.core.publisher.Mono

interface TrackedHabits {

  fun save(habit: TrackedHabit): Mono<TrackedHabit>

  fun findById(id: TrackedHabit.Id): Mono<TrackedHabit>
}
