package com.example.modulithtest.habits.data

import com.example.modulithtest.habits.TestFixtures
import com.example.modulithtest.habits.data.r2dbc.R2dbcHabits
import io.kotest.matchers.collections.shouldContain
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import reactor.kotlin.test.test

@DataR2dbcTest
@Import(R2dbcHabits::class)
class R2dbcHabitsIntegrationTest {

  @Autowired private lateinit var subject: R2dbcHabits

  @Test
  fun `save() should save habit and return saved habit`() {
    val savedHabit = subject.save(TestFixtures.JoggingHabit)
    val allHabits = subject.findAll().buffer()

    savedHabit.test().expectNext(TestFixtures.JoggingHabit).verifyComplete()
    allHabits.test().assertNext { it shouldContain TestFixtures.JoggingHabit }
  }
}
