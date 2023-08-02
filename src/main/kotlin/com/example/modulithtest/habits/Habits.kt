package com.example.modulithtest.habits

import reactor.core.publisher.Flux

interface Habits {

  fun findAll(): Flux<Habit>
}
