package com.example.modulithtest.ddd

import org.jmolecules.event.types.DomainEvent

abstract class AbstractAggregateRoot {

  private val _domainEvents: MutableList<DomainEvent> = mutableListOf()
  val domainEvents: List<DomainEvent>
    get() = _domainEvents.toList()

  fun registerEvent(event: DomainEvent) = _domainEvents.add(event)

  fun clearEvents() = _domainEvents.clear()
}
