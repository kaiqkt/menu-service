package com.anon.menu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.anon.menu"])
class Application : SpringBootServletInitializer() {
	override fun configure(
		builder: SpringApplicationBuilder
	): SpringApplicationBuilder {
		return builder.sources(Application::class.java)
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(args = args)
}
