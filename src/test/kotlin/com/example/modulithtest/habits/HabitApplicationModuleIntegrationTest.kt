package com.example.modulithtest.habits

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario

@ApplicationModuleTest
class HabitApplicationModuleIntegrationTest {

  @Autowired private lateinit var habitManagement: HabitManagement

  @Test
  fun `should emit HabitCreated event when a new habit is saved`(scenario: Scenario) {
    val newHabit =
        Habit(Habit.Name("Cooking"), Schedule(Schedule.Frequency.DAILY, Schedule.Repetitions(2)))
    scenario
        .stimulate { -> habitManagement.saveHabit(newHabit).block() }
        .andWaitForEventOfType(Habit.HabitCreated::class.java)
        .toArriveAndVerify { event -> event.id shouldBe newHabit.id.value }
  }
}
