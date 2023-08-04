package com.example.modulithtest.habits

import com.example.modulithtest.ddd.AbstractAggregateRoot
import java.util.UUID
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.ddd.types.Identifier
import org.jmolecules.event.annotation.DomainEvent

@AggregateRoot
class Habit(@Identity val id: Id, val name: Name, val schedule: Schedule) :
    AbstractAggregateRoot() {

  constructor(name: Name, schedule: Schedule) : this(Id(UUID.randomUUID()), name, schedule) {
    registerEvent(HabitCreated(id.value))
  }

  override fun equals(other: Any?): Boolean = if (other is Habit) id == other.id else false

  override fun hashCode(): Int = id.hashCode()

  override fun toString(): String {
    return "Habit(id=$id, name=$name, schedule=$schedule)"
  }

  @JvmInline value class Id(val value: UUID) : Identifier

  @JvmInline @ValueObject value class Name(val value: String)

  @DomainEvent data class HabitCreated(val id: UUID)
}

@ValueObject
data class Schedule(val frequency: Frequency, val repetitions: Repetitions) {

  @ValueObject
  enum class Frequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
  }

  @JvmInline @ValueObject value class Repetitions(val value: Int)
}
