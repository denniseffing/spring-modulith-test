package com.example.modulithtest.habits.data

import com.example.modulithtest.habits.TestFixtures.JoggingHabit
import io.kotest.matchers.collections.shouldContain
import org.junit.jupiter.api.Test
import reactor.kotlin.test.test

class InMemoryHabitsTest {

  val subject = InMemoryHabits()

  @Test
  fun `save() should save habit and return saved habit`() {
    val savedHabit = subject.save(JoggingHabit)
    val allHabits = subject.findAll().buffer()

    savedHabit.test().expectNext(JoggingHabit).verifyComplete()
    allHabits.test().assertNext { it shouldContain JoggingHabit }
  }
}
