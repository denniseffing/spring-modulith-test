package com.example.modulithtest

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false) class TestModulithTestApplication

fun main(args: Array<String>) {
  fromApplication<ModulithTestApplication>().with(TestModulithTestApplication::class).run(*args)
}
