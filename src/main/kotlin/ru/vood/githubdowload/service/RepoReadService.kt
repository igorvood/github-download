package ru.vood.githubdowload.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.vood.githubdowload.service.dto.RepoInfo
import ru.vood.githubdowload.service.dto.UserGithubInfo

@Service
class RepoReadService(private val restTemplate: RestTemplate) {
    private val log: Logger = LoggerFactory.getLogger(RepoReadService::class.java)
    fun reposRead(userGithubInfo: UserGithubInfo, s: String) {
        val forEntity = restTemplate.getForEntity<List<RepoInfo>>(userGithubInfo.repos_url)
        val repoInfoList = forEntity.body
        log.info(repoInfoList.toString())
    }
}