package com.example.modulithtest.habits.data.r2dbc

import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.Schedule
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface R2dbcHabitRepository : ReactiveCrudRepository<R2dbcHabit, UUID>

@Table("habits")
data class R2dbcHabit(val id: UUID, val name: String, val frequency: String, val repetitions: Int) {

  fun toAggregate(): Habit =
      Habit(
          Habit.Id(id),
          Habit.Name(name),
          Schedule(Schedule.Frequency.valueOf(frequency), Schedule.Repetitions(repetitions)))

  companion object {
    fun fromAggregate(habit: Habit): R2dbcHabit =
        R2dbcHabit(
            habit.id.value,
            habit.name.value,
            habit.schedule.frequency.toString(),
            habit.schedule.repetitions.value)
  }
}
