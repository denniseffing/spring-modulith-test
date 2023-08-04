package com.example.modulithtest.tracking

import com.example.modulithtest.ddd.Service
import com.example.modulithtest.habits.Habit
import com.example.modulithtest.tracking.TrackedHabit.Id
import com.example.modulithtest.tracking.data.TrackedHabits
import org.springframework.modulith.ApplicationModuleListener

@Service
class HabitListener(val trackedHabits: TrackedHabits) {

  @ApplicationModuleListener
  fun on(event: Habit.HabitCreated) {
    trackedHabits.save(TrackedHabit(Id(event.id))).then().block()
  }
}
