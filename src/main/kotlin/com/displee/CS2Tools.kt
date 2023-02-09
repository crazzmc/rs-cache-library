package com.displee

import com.displee.cache.CacheLibrary
import org.apache.tools.ant.util.StringUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class CS2Tools {
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
            val cs2s = cache.index(12);
            val modelsPath = System.getProperty("user.dir") + "/exports/"
            cs2s.cache()
            println("Models path $modelsPath")
            File(modelsPath).walk().forEach {
                if (it.toString().endsWith(".dat")) {
                    val nameBefore = it.name.substringBefore(".")
                    var toInt = nameBefore.toInt()
                    println("Packing ${it.name} to slot $toInt")
                    cache.put(12, toInt, Files.readAllBytes(it.toPath()))
                }
            }
            cs2s.update()
        }

        private fun get() {
            val models = cache.index(12)
            models.cache()
            println("Last model id ${models.archiveIds().last()}")
        }

        private fun replace() {
            val startId = 53915
            val models = cache.index(12)
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

            val modelsPath = System.getProperty("user.dir") + "/exports/"

            val cs2s = cache.index(12)
            cs2s.cache()

            for (id in cs2s.archiveIds()) {
                val data = cache.data(12, id)
                if (data != null && id > 9999) {
                    Files.write(
                        Path.of(modelsPath + "$id" + ".dat"),
                        data
                    );
                }
            }
        }
    }

}

//38909

//38937