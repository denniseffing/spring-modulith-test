package com.example.modulithtest

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ModularityTests {

  var modules: ApplicationModules = ApplicationModules.of(ModulithTestApplication::class.java)

  @Test
  fun `verify modular structure`() {
    modules.verify()
  }

  @Test
  fun `create module documentation`() {
    Documenter(modules).writeDocumentation()
  }
}
