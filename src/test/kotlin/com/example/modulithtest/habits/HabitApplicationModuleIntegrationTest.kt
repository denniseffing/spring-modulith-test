package com.example.modulithtest.habits

import com.example.modulithtest.habits.TestFixtures.JoggingHabit
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.test.context.TestPropertySource

@ApplicationModuleTest
@TestPropertySource(properties = ["spring.modulith.events.jdbc.schema-initialization.enabled=true"])
class HabitApplicationModuleIntegrationTest {

  @Autowired private lateinit var habitManagement: HabitManagement

  @Test
  fun `should emit HabitCreated event when a habit is saved`(scenario: Scenario) {
    scenario
        .stimulate { -> habitManagement.saveHabit(JoggingHabit).block() }
        .andWaitForEventOfType(Habit.HabitCreated::class.java)
        .toArrive()
  }
}
