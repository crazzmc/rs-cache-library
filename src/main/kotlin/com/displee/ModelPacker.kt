package com.displee

import com.displee.cache.CacheLibrary
import jdk.internal.org.jline.utils.Colors.s
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class ModelPacker {
    companion object {

        val cachePath = System.getProperty("user.dir") + "/cache/"
        val cache = CacheLibrary.create(cachePath)
        val models = cache.index(7)
        val modelsPath = System.getProperty("user.dir") + "/models/"
        var initialOffset = models.archiveIds().last() + 1
        val exportsPath = System.getProperty("user.dir") + "/exports/"

        val newCachePath = System.getProperty("user.dir") + "/new_cache/" //only used if using the transfer function
        val newCache = CacheLibrary.create(newCachePath)
        val newModels = newCache.index(7)
        var startIndex = 0; //First model to rip from one cache
        var newCacheStartindex = 0; //Set offset here if you want to move from lets say 42568 but start at 60000

        @JvmStatic
        fun main(args: Array<String>) {
            models.cache()
//            pack()
//            get()
//            export()
//            replace()
//            export()
            transfer()
        }

        private fun transfer() {
            newModels.cache()
            println(
                "Moving models from old cache to new starting at $startIndex. Size before packing ${
                    newCache.index(7).archiveIds().size
                }"
            )
            for (id in models.archiveIds()) {
                val data = cache.data(7, id)
                if (data != null) {
                    if (id >= startIndex) {
                        if (newCacheStartindex > 0) {
                            newCache.put(7, newCacheStartindex++, data)
                        } else {
                            newCache.put(7, startIndex++, data)
                        }
                    }
                }
            }
            newModels.update()
            println("Models packed into new cache, new size: ${newCache.index(7).archiveIds().size}")
        }

        private fun pack() {
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
            println("Last model id ${models.archiveIds().last()}")
        }

        private fun replace() { //replaces models starting at the startIndex
            println("Models path $modelsPath")
            File(modelsPath).walk().forEach {
                if (it.toString().endsWith(".dat")) {
                    println("Packing ${it.name} to index $startIndex")
                    cache.put(7, startIndex++, Files.readAllBytes(it.toPath()))
                }
            }
            models.update()
        }

        private fun export() {
            for (id in models.archiveIds()) {
                val data = cache.data(7, id)
                if (data != null) {
                    if (id == 60000) {
                        Files.write(
                            Path.of("$exportsPath$id.dat"),
                            data
                        )
                    }
                }
            }
        }

    }

}