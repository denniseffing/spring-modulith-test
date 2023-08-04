package com.example.modulithtest.ddd

import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents

abstract class AbstractAggregateRoot {

  private val _domainEvents: MutableList<Any> = mutableListOf()

  val domainEvents: List<Any>
    @DomainEvents get() = _domainEvents.toList()

  fun registerEvent(event: Any) = _domainEvents.add(event)

  @AfterDomainEventPublication fun clearEvents() = _domainEvents.clear()
}
