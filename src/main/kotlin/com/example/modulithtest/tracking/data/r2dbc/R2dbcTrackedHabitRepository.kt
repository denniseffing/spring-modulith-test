package com.example.modulithtest.tracking.data.r2dbc

import com.example.modulithtest.tracking.TrackedHabit
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface R2dbcTrackedHabitRepository : ReactiveCrudRepository<R2dbcTrackedHabit, UUID>

@Table("tracked_habits")
data class R2dbcTrackedHabit(@Id val id: UUID, @Version val version: Long? = null) {

  fun toAggregate(): TrackedHabit = TrackedHabit(TrackedHabit.Id(id))

  companion object {
    fun fromAggregate(habit: TrackedHabit): R2dbcTrackedHabit = R2dbcTrackedHabit(habit.id.value)
  }
}
