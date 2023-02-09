package com.displee

import com.displee.cache.CacheLibrary
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.isRegularFile
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.math.exp

class ModelPacker {
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
            val models = cache.index(7);
            val modelsPath = System.getProperty("user.dir") + "/models/"
            models.cache()
            var initialOffset = models.archiveIds().last() + 1;
            println("Models path $modelsPath")
            File(modelsPath).walk().forEach {
                if (it.toString().endsWith(".dat")) {
                    println("Packing ${it.name} to index $initialOffset")
                    cache.put(7, initialOffset++, Files.readAllBytes(it.toPath()))
                }
            }
            models.update()
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

            val modelsPath = System.getProperty("user.dir") + "/exports/"

            val models = cache.index(7)
            models.cache()

            for (id in models.archiveIds()) {
                val data = cache.data(7, id)
                if (data != null) {
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