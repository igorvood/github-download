package ru.vood.githubdowload.service

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vood.githubdowload.service.Const.infoFileName
import ru.vood.githubdowload.service.Const.rootFolder
import ru.vood.githubdowload.service.dto.RepoInfo
import java.io.*


@Service
class DownLoadService(/*private val  saveFolder: SaveFolder*/
private val zipService: ZipService
) {

    private val log: Logger = LoggerFactory.getLogger(DownLoadService::class.java)

    val mapper = jacksonObjectMapper().registerModule(KotlinModule())

    fun run(repoInfo: RepoInfo) {
        val userName = repoInfo.full_name.split("/")[0]
        val infoFolder = "${rootFolder}\\$userName\\${repoInfo.name}\\"
        val infoFile = infoFolder + infoFileName
        val repoFolder = "$infoFolder${repoInfo.name}"

        val fl: Boolean = downLoadNeed(infoFile,repoInfo)
        if (fl /*|| true*/) {
            log.info("$userName/${repoInfo.name}-> Clone, see in folder $repoFolder")
            clone(repoInfo, repoFolder)
            writeInfo(infoFile, repoInfo)
            log.info("$userName/${repoInfo.name}-> Zip, see in folder $repoFolder")
            zipService.zip(repoFolder)

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
        if (file.exists()){
            file.delete()
        }

        val writeValueAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repoInfo)



        val fileWriter = FileWriter(infoFile)
        val printWriter = PrintWriter(fileWriter)
        printWriter.print(writeValueAsString)
        printWriter.close()

/*
//        file.bufferedWriter().use { out -> out.write(writeValueAsString) }
        file.appendText(writeValueAsString)
//        file.writeText(writeValueAsString)
*/
    }

    private fun downLoadNeed(infoFile: String, repoInfoCurrent: RepoInfo): Boolean {
        val file = File(infoFile)
        val b = if (file.exists()) {
            val readText = file.readText()
            val repoInfoOld = mapper.readValue(readText, RepoInfo::class.java)
            !repoInfoOld.updated_at.equals(repoInfoCurrent.updated_at)
        } else true
        return b
//        return !file.exists()
    }

}
