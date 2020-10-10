package com.bird.mm.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class QmcDecoder {
    private var x = -1
    private var y = 8
    private var dx = 1
    private var index = -1

    private val seedMap = arrayOf(
        intArrayOf(0x4a, 0xd6, 0xca, 0x90, 0x67, 0xf7, 0x52),
        intArrayOf(0x5e, 0x95, 0x23, 0x9f, 0x13, 0x11, 0x7e),
        intArrayOf(0x47, 0x74, 0x3d, 0x90, 0xaa, 0x3f, 0x51),
        intArrayOf(0xc6, 0x09, 0xd5, 0x9f, 0xfa, 0x66, 0xf9),
        intArrayOf(0xf3, 0xd6, 0xa1, 0x90, 0xa0, 0xf7, 0xf0),
        intArrayOf(0x1d, 0x95, 0xde, 0x9f, 0x84, 0x11, 0xf4),
        intArrayOf(0x0e, 0x74, 0xbb, 0x90, 0xbc, 0x3f, 0x92),
        intArrayOf(0x00, 0x09, 0x5b, 0x9f, 0x62, 0x66, 0xa1)
    )

    fun nextMask(): Int {
        var ret: Int
        index++
        if (x < 0) {
            dx = 1
            y = (8 - y) % 8
            ret = 0xc3
        } else if (x > 6) {
            dx = -1
            y = 7 - y
            ret = 0xd8
        } else {
            ret = seedMap[y][x]
        }
        x += dx
        return if (index == 0x8000 || index > 0x8000 && (index + 1) % 0x8000 == 0) {
            nextMask()
        } else ret
    }

    fun covert(file:File) : Boolean{
        val fis = FileInputStream(file)
        val size = fis.available()
        val buffer = ByteArray(size)
        fis.read(buffer)
        for (i in 0 until size) {
            buffer[i] = nextMask().xor(buffer[i].toInt()).toByte()
        }
        val fos = FileOutputStream(File(file.path.replace(".qmcflac",".flac")))
        fos.write(buffer)
        fos.flush()
        fos.close()
        fis.close()
        return true
    }

}