package com.example.modulithtest.habits

import com.example.modulithtest.ddd.AbstractAggregateRoot
import java.util.UUID
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.ddd.types.Identifier

@AggregateRoot
class Habit(@Identity val id: Id = Id(UUID.randomUUID()), val name: Name, val schedule: Schedule) :
    AbstractAggregateRoot() {

  override fun equals(other: Any?): Boolean = if (other is Habit) id == other.id else false

  override fun hashCode(): Int = id.hashCode()

  @JvmInline value class Id(val value: UUID) : Identifier

  @JvmInline @ValueObject value class Name(val value: String)
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
