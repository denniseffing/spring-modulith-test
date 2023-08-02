package com.example.modulithtest.habits

import java.util.UUID

object TestFixtures {

  val JoggingHabit =
      Habit(
          id = Habit.Id(UUID.randomUUID()),
          name = Habit.Name("Jogging"),
          schedule = Schedule(Schedule.Frequency.DAILY, Schedule.Repetitions(1)))
}
