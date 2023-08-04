package com.example.modulithtest.tracking

import com.example.modulithtest.ddd.AbstractAggregateRoot
import java.util.UUID
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.ddd.annotation.Identity

@AggregateRoot
class TrackedHabit(@Identity val id: Id) : AbstractAggregateRoot() {

  @JvmInline value class Id(val value: UUID)
}
