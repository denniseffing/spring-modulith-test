package com.example.modulithtest.habits

import com.example.modulithtest.ddd.Service
import reactor.core.publisher.Flux

@Service
class HabitManagement(val habits: Habits) {

  fun findAll(): Flux<Habit> = habits.findAll()
}
