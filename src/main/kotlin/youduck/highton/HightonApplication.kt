package youduck.highton

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HightonApplication

fun main(args: Array<String>) {
	runApplication<HightonApplication>(*args)
}
