package ru.vood.githubdowload.service

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vood.githubdowload.service.dto.RepoInfo
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader


@Service
class DownLoadService(/*private val  saveFolder: SaveFolder*/) {

    private val rootFolder = "D:\\temp\\github"
    private val log: Logger = LoggerFactory.getLogger(RepoReadService::class.java)
    private val infoFileName = "RepoInfo.json"
    val mapper = jacksonObjectMapper().registerModule(KotlinModule())

    fun run(repoInfo: RepoInfo) {
        val userName = repoInfo.full_name.split("/")[0]
        val infoFolder = "${rootFolder}\\$userName\\${repoInfo.name}\\"
        val infoFile = infoFolder + infoFileName
        val repoFolder = "$infoFolder${repoInfo.name}"

        val fl: Boolean = downLoadNeed(infoFile)
        if (fl) {
//            clone(repoInfo, repoFolder)
            writeInfo(infoFile, repoInfo)


            log.info("$userName/${repoInfo.name}-> Done see in folder $repoFolder")
        } else {
            log.info("$userName/${repoInfo.name}-> Skiped see in folder $repoFolder")
        }
    }

    private fun clone(repoInfo: RepoInfo, repoFolder: String) {
        try {

            val s = "git clone ${repoInfo.clone_url} $repoFolder"

            val p = Runtime.getRuntime().exec(s)
            p.waitFor()
            val reader = BufferedReader(
                InputStreamReader(p.inputStream)
            )
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                log.info("${repoInfo.name} -> $line")
            }
        } catch (e1: IOException) {
            e1.printStackTrace()
        } catch (e2: InterruptedException) {
            e2.printStackTrace()
        }
    }

    private fun writeInfo(infoFile: String, repoInfo: RepoInfo) {
        val file = File(infoFile)
        file.deleteOnExit()
        val writeValueAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repoInfo)

//        file.bufferedWriter().use { out -> out.write(writeValueAsString) }
        file.appendText(writeValueAsString)
//        file.writeText(writeValueAsString)
    }

    private fun downLoadNeed(infoFile: String): Boolean {
        val file = File(infoFile)
        return !file.exists()
    }

}
