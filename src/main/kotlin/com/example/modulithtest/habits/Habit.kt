package com.example.modulithtest.habits

import java.util.UUID

data class Habit(val id: Id, val name: Name, val schedule: Schedule) {

  @JvmInline value class Id(val value: UUID)

  @JvmInline value class Name(val value: String)
}

data class Schedule(val frequency: Frequency, val repetitions: Repetitions) {

  enum class Frequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
  }

  @JvmInline value class Repetitions(val value: Int)
}
