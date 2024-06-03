package com.lucasaugustocastro.ApiLibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ApiLibraryApplication

fun main(args: Array<String>) {
	runApplication<ApiLibraryApplication>(*args)
}
