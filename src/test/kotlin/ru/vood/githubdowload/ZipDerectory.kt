package ru.vood.githubdowload

import org.junit.jupiter.api.Test
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class ZipDerectory {

    val zippedDirConsct = "D:\\temp\\github\\igorvood\\AdmIDE"
    val filename = "D:\\temp\\github\\igorvood\\AdmIDE\\AdmIDE"
    val zipName = "test.zip"

    @Test
    fun asd(){


        val scanDirectory = scanDirectory(File(filename))


        zipFolder(zippedDirConsct, zipName, scanDirectory)


//        File(filename).list().forEach { println(it) }

    /*    try {
            ZipOutputStream(FileOutputStream("D:\\temp\\github\\igorvood\\AdmIDE\\output.zip")).use { zout ->
                FileInputStream(filename).use { fis ->
                    val entry1 = ZipEntry("notes.txt")
                    zout.putNextEntry(entry1)
                    // считываем содержимое файла в массив byte
                    val buffer = ByteArray(fis.available())
                    fis.read(buffer)
                    // добавляем содержимое к архиву
                    zout.write(buffer)
                    // закрываем текущую запись для новой записи
                    zout.closeEntry()
                }
            }
        } catch (ex: Exception) {
            println(ex.message)
        }*/
//        D:\temp\github\igorvood\AdmIDE\AdmIDE
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

    tailrec fun scanDirectory(file: File): List<String>{
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