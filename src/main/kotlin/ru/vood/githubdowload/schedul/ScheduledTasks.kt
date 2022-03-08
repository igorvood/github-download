package ru.vood.githubdowload.schedul

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.vood.githubdowload.service.RepoReadService
import java.text.SimpleDateFormat
import java.util.*


@Component
class ScheduledTasks(private val read: RepoReadService) {
    private val log: Logger = LoggerFactory.getLogger(ScheduledTasks::class.java)

    private val dateFormat = SimpleDateFormat("HH:mm:ss")


    @Scheduled(fixedRate = 3600000)
    fun reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(Date()))
        read.readProfile()

    }
}