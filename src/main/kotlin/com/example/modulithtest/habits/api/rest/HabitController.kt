package com.example.modulithtest.habits.api.rest

import com.example.modulithtest.habits.Habit
import com.example.modulithtest.habits.HabitManagement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class HabitController(val habitManagement: HabitManagement) {

  @GetMapping("/habits") fun getHabits(): Flux<Habit> = habitManagement.findAll()
}
