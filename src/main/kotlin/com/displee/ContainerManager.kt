package com.displee

import com.displee.cache.CacheLibrary
import com.displee.util.PathTools
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ContainerManager {
    companion object {

        val cache = CacheLibrary.create(PathTools().cacheLocation)
        val containers = cache.index(2).archive(5)

        @JvmStatic
        fun main(args: Array<String>) {
            pack()
//            for (i in 0..7000)
//                containers!!.remove(i)
//            cache.update()
//            get()
//            export()
//            replace()
//            val pack = false
//            if (pack) pack() else get()
//            export()
        }

        private fun pack() {
            cache.index(2).cache()
            val containersPath = System.getProperty("user.dir") + "/containers/"


            for (index in 0..6100) {
                val file = File(containersPath + "/$index.dat")
                if (file.exists()) {
                    containers!!.add(index, Files.readAllBytes(file.toPath()), -1, false)
                    println("Packing ${file.name.substringBefore(".dat")}")
                }


//                File(containersPath).walk(direction = FileWalkDirection.TOP_DOWN).forEach {
//                    if (it.toString().endsWith(".dat")) {
//                        val id = it.name.substringBefore(".dat").toInt()
//                        if (id == index) {
//                            println("Packing ${it.name.substringBefore(".dat")}")
////                    containers.add(it, true)
//                            containers!!.add(id, Files.readAllBytes(it.toPath()), -1, false)
//                        }
//
//                    }
////                    containers!!.add(it.name.substringBefore(".dat"), Files.readAllBytes(it.toPath()))
////                    containers!!.add(it.name.substringBefore(".dat"), Files.readAllBytes(it.toPath()), false)
////                    containers!!.add(Files.readAllBytes(it.toPath()))
////                    cache.put(7, initialOffset++, Files.readAllBytes(it.toPath()))
//                }
            }
            cache.update()
//            cache.put(2, 5, containers.info)

//            models.update()
        }

        private fun get() {
            val models = cache.index(7)
            models.cache()
            println("Last model id ${models.archiveIds().last()}")
        }

        private fun replace() {
            val startId = 53915
            val models = cache.index(7)
            val modelsPath = System.getProperty("user.dir") + "/models/"
            models.cache()
            var initialOffset = startId
            println("Models path $modelsPath")
            File(modelsPath).walk().forEach {
                if (it.toString().endsWith(".dat")) {
                    println("Packing ${it.name} to index $initialOffset")
                    cache.put(7, initialOffset++, Files.readAllBytes(it.toPath()))
                }
            }
            models.update()
        }

        private fun export() {

            val modelsPath = System.getProperty("user.dir") + "/containers/"

            val cacheIndex2 = cache.index(2)
            cacheIndex2.cache()
            val models = cacheIndex2.archive(5)!!

            for (i in 6000..6250) {
                File("$modelsPath/5007.dat").copyTo(File("$modelsPath/$i.dat"), false, DEFAULT_BUFFER_SIZE)
            }

            println("Model info before ${models.files().size}")
            println("DFILE WTF ${cacheIndex2.archive(5)!!.files().get(813).hashName}")
//            for (container in 6000..6500) {
//                cacheIndex2.archive(5).add
//                cacheIndex2.archive(5)!!.add(container, models.files().get(813)., false)
//            }
//            for (file in cacheIndex2.archive(5)!!.files()) {
//                if (file.data != null) {
//                    Files.write(
//                        Path.of(modelsPath + "${file.id}" + ".dat"),
//                        file.data!!
//                    );
//                }
//            }
//            cache.put(2, 5, cacheIndex2.info)
            cacheIndex2.update()
            println("Model info after ${cache.index(2).archive(5)!!.files().size}")
        }
    }

}

//38909

//38937