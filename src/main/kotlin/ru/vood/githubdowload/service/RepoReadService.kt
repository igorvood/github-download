package ru.vood.githubdowload.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.vood.githubdowload.service.dto.RepoInfo
import ru.vood.githubdowload.service.dto.UserGithubInfo

@Service
class RepoReadService(
    private val restTemplate: RestTemplate,
    private val downLoadService: DownLoadService,

    ) {
    private val log: Logger = LoggerFactory.getLogger(RepoReadService::class.java)
    fun reposRead(userGithubInfo: UserGithubInfo, s: String) {

//        val forObject = restTemplate.getForObject(userGithubInfo.repos_url, List<RepoInfo>::class.java)
        val forEntity = restTemplate.getForEntity(userGithubInfo.repos_url, Array<RepoInfo>::class.java)
        val repoInfoList = forEntity.body!!

        repoInfoList/*.take(1)*/.forEach {
            downLoadService.run(it)
        }

        log.info(repoInfoList.toString())
    }
}