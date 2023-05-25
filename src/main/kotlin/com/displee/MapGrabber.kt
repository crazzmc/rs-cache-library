package com.displee

import com.displee.cache.CacheLibrary
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class MapGrabber {
    companion object {

        val cachePath = System.getProperty("user.dir") + "/cache/"
        val cache = CacheLibrary.create(cachePath)

        @JvmStatic
        fun main(args: Array<String>) {
            pack()
//            get()
//            export()
//            replace()
//            val pack = false
//            if (pack) pack() else get()
//            export()
        }

        private fun pack() {
            val maps = cache.index(5);
            val mapsPath = System.getProperty("user.dir") + "/maps/"
            maps.cache()
            println("Models path $mapsPath")
            File(mapsPath).walk().forEach {
                if (it.toString().endsWith(".dat")) {
                    val nameBefore = it.name.substringBefore(".")
                    var toInt = nameBefore.toInt()
                    println("Packing ${it.name} to slot $toInt")
                    cache.put(5, toInt, Files.readAllBytes(it.toPath()))
                }
            }
            maps.update()
        }

        private fun get() {
            val models = cache.index(12)
            models.cache()
            println("Last model id ${models.archiveIds().last()}")
        }

        private fun replace() {
            val startId = 53915
            val models = cache.index(5)
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

            val mapsPath = System.getProperty("user.dir") + "/maps/"

            val maps = cache.index(5)
            maps.cache()

            for (id in maps.archiveIds()) {
                val data = cache.data(5, id)
                if (data != null) {
                    Files.write(Path.of(mapsPath + "$id" + ".dat"), data)
                }
            }
        }
    }

}

//38909

//38937