package ru.vood.githubdowload

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


class TestWrtite {

    @Test
    fun sdf(){
        val file = File("D:\\temp\\github\\igorvood\\test.log")
        file.deleteOnExit()
//        val writeValueAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repoInfo)

//        file.bufferedWriter().use { out -> out.write(writeValueAsString) }
        val createNewFile = file.createNewFile()
        val writer = file.printWriter().use { out -> out.println("fileContent") }
//        file.writeText("file.sdaasdsad")
//        file.appendText("writeValueAsString")
//        file.writeText(writeValueAsString)

        println(file.exists())

    }

    @Test
    fun asdfjhkl(){

        val fileWriter = FileWriter("D:\\temp\\github\\igorvood\\test.log")
        val printWriter = PrintWriter(fileWriter)
        printWriter.print("Some String")
        printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000)
        printWriter.close()
    }
}