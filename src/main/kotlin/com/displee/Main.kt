package com.displee

import com.displee.cache.CacheLibrary
import com.displee.cache.index.archive.Archive

class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            println("DIR ${System.getProperty("user.dir")}")
            val from = System.getProperty("user.dir") + "/new-cache/"
            val target = System.getProperty("user.dir") + "/cache/"
            val src = CacheLibrary(from)
            val targ = CacheLibrary(target)
            val idx = 12 //cs2s


            println("archive ids in source: ${src.index(idx).archiveIds().toTypedArray().contentToString()}")
            println("archive ids in target: ${targ.index(idx).archiveIds().toTypedArray().contentToString()}")

            var highestIdSrc = 0
            src.index(idx).archiveIds().forEach {
                if (it > highestIdSrc)
                    highestIdSrc = it
            }
            println("highest archive id src: $highestIdSrc")

            var highestIdTarg = 0
            targ.index(idx).archiveIds().forEach {
                if (it > highestIdTarg)
                    highestIdTarg = it
            }
            println("highest archive id targ: $highestIdTarg")

            src.index(idx).cache()
            targ.index(idx).cache()
            for (arcId in 3140..11318) {
                val srcArc = src.index(idx).archive(arcId)
                if (srcArc == null) {
                    println("archive $arcId is empty")
                    continue
                }
                var targArc: Archive? = targ.index(idx).archive(arcId)
                if (targArc != null && srcArc.crc == targArc.crc) {
                    println("archive $arcId crc match so not packing ${srcArc.crc}")
                    continue
                }
                if (arcId in 10000..10221) {
                    println("Skipping $arcId")
                    continue
                }
                targ.index(idx).add(archive = srcArc, xtea = srcArc.xtea(), overwrite = true)
            }
            targ.index(idx).update()
        }

    }

}