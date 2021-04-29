package com.nero.qtquicktext.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

fun copyFileOrDirectory(srcDir: String?, dstDir: String?) {
    try {
        val src = File(srcDir)
        val dst = File(dstDir, src.name)
        if (src.isDirectory) {
            val files = src.list()
            val filesLength = files.size
            for (i in 0 until filesLength) {
                val src1 = File(src, files[i]).path
                val dst1 = dst.path
                copyFileOrDirectory(src1, dst1)
            }
        } else {
            copyFile(src, dst)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

@Throws(IOException::class)
fun copyFile(sourceFile: File?, destFile: File) {
    if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
    if (!destFile.exists()) {
        destFile.createNewFile()
    }
    var source: FileChannel? = null
    var destination: FileChannel? = null
    try {
        source = FileInputStream(sourceFile).channel
        destination = FileOutputStream(destFile).channel
        destination.transferFrom(source, 0, source.size())
    } finally {
        source?.close()
        destination?.close()
    }
}
