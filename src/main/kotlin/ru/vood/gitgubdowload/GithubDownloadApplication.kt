package ru.vood.gitgubdowload

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class GithubDownloadApplication

fun main(args: Array<String>) {
    runApplication<GithubDownloadApplication>(*args)
}
