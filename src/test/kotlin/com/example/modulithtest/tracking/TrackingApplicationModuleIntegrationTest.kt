package com.example.modulithtest.tracking

import com.example.modulithtest.habits.Habit
import com.example.modulithtest.tracking.data.TrackedHabits
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import reactor.kotlin.test.test

@ApplicationModuleTest
class TrackingApplicationModuleIntegrationTest(val trackedHabits: TrackedHabits) {

  @Test
  fun `should save tracked habit when HabitCreated event was received`(scenario: Scenario) {
    val id = UUID.randomUUID()
    scenario
        .publish(Habit.HabitCreated(id))
        .andWaitForStateChange { trackedHabits.findById(TrackedHabit.Id(id)) }
        .andVerify { trackedHabit ->
          trackedHabit.test().assertNext { it.id.value shouldBe id }.verifyComplete()
        }
  }
}
