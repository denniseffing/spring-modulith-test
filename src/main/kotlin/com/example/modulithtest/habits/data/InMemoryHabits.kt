package com.example.modulithtest.habits.data

import com.example.modulithtest.ddd.Repository
import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.Habits
import com.example.modulithtest.habits.Schedule
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Repository
class InMemoryHabits : Habits {

  private val habits: MutableList<Habit> =
      mutableListOf(
          Habit(
              name = Habit.Name("Jogging"),
              schedule = Schedule(Schedule.Frequency.DAILY, Schedule.Repetitions(5))))

  override fun findAll(): Flux<Habit> = habits.toFlux()
}
