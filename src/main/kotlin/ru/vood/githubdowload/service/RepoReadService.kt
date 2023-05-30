package ru.vood.githubdowload.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vood.githubdowload.property.SaveFolder
import ru.vood.githubdowload.service.dto.RepoInfo
import ru.vood.githubdowload.service.dto.UserGithubInfo
import java.lang.Math.ceil
import java.lang.Math.round

@Service
class RepoReadService(
    private val restTemplate: RestTemplate,
    private val downLoadService: DownLoadService,
    private val saveFolder: SaveFolder,
) {
    private val log: Logger = LoggerFactory.getLogger(RepoReadService::class.java)
    fun reposRead(userGithubInfo: UserGithubInfo, s: String) {

        val cntPages = ceil(userGithubInfo.public_repos.toDouble() / 30L).toInt()

        val repoInfoList = (1..cntPages)
            .map { n ->
                val forEntity = restTemplate.getForEntity(
                    """${userGithubInfo.repos_url}?page=${n}""",
                    Array<RepoInfo>::class.java
                )
                forEntity
            }.flatMap { ent ->
                ent.body!!.toList()
            }

        assert(repoInfoList.size >= userGithubInfo.public_repos)

        repoInfoList.parallelStream().forEach {
            downLoadService.run(it)
        }

//        repoInfoList/*.take(1)*/.forEach {
//            downLoadService.run(it)
//        }

//        log.info(repoInfoList.toString())
    }


}