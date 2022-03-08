package ru.vood.githubdowload.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.vood.githubdowload.property.ScanUsers
import ru.vood.githubdowload.service.dto.UserGithubInfo


@Service
class UserReadService(
    private val restTemplate: RestTemplate,
    private val scanUsers: ScanUsers,
    private val repoReadService:RepoReadService
) {

    private val log: Logger = LoggerFactory.getLogger(UserReadService::class.java)
    fun readProfile() {
        scanUsers.users.forEach {
            val forEntity = restTemplate.getForEntity<UserGithubInfo>("https://api.github.com/users/$it")
            val headers = forEntity.headers
//            log.info(headers.toString())
            val userGithubInfo = forEntity.body!!
            log.info(userGithubInfo.toString())
            repoReadService.reposRead(userGithubInfo, it)
        }

    }
}