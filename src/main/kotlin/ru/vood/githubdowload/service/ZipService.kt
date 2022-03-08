package ru.vood.githubdowload.service

import org.springframework.stereotype.Service
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service
class ZipService {

    fun zip(repoFolder: String) {
        val file1 = File(repoFolder)
        val scanDirectory = scanDirectory(file1)
        val split = repoFolder.split("\\")
        val file = split[split.size - 1]

        val parent = file1.parent
        zipFolder(parent, "$file.zip", scanDirectory)

        if (file1.exists() && file1.isDirectory){
            file1.deleteRecursively()
        }
    }



    private fun zipFolder(
        zippedDir: String,
        zipName: String,
        scanDirectory: List<String>
    ) {
        ZipOutputStream(BufferedOutputStream(FileOutputStream("$zippedDir\\$zipName"))).use { out ->
            for (file in scanDirectory) {
                FileInputStream(file).use { fi ->
                    BufferedInputStream(fi).use { origin ->
                        val entry = ZipEntry(file.replace(zippedDir,"")/*.substring(file.lastIndexOf("/"))*/)
                        out.putNextEntry(entry)
                        origin.copyTo(out, 1024)
                    }
                }
            }
        }
    }

    private tailrec fun scanDirectory(file: File): List<String>{
        val result = mutableListOf<String>()

        if (file.isDirectory){
            val list = file.list()
            list.forEach {
                val nextFile = "${file.path}\\$it"
                val file1 = File(nextFile)
                if (file1.isFile) {
                    result.add(nextFile)
//                    println(nextFile)
                }
                else if (file1.isDirectory) {
                    result.addAll(scanDirectory(file1))
                }
            }
        } else if (file.isFile) {
            result.add(file.path)
//            println(file.path)
        }



        return result

    }
}
