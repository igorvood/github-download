package ru.vood.githubdowload.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.vood.githubdowload.service.dto.UserGithubInfo


@Service
class RepoReadService(private val restTemplate: RestTemplate) {

    private val log: Logger = LoggerFactory.getLogger(RepoReadService::class.java)
    fun readProfile() {
        val forEntity = restTemplate.getForEntity<UserGithubInfo>("https://api.github.com/users/igorvood")
        log.info(forEntity.headers.toString())
        log.info(forEntity.body.toString())

//        https://api.github.com/users/defunkt
    }
}