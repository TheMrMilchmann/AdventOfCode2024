/*
 * Copyright (c) 2024 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package days

import utils.readInput

fun main() {
    val data = readInput().single()
        .map(Char::digitToInt)

    data class File(val size: Int, val offset: Int)
    val bIndex = mutableMapOf<Int, File>()
    val fs = buildList {
        var idx = 0
        var isFile = true

        for (d in data) {
            if (isFile) {
                bIndex[idx] = File(d, size)
                for (i in 0 until d) {
                    add(idx)
                }

                idx++
                isFile = false
            } else {
                for (i in 0 until d) add(null)
                isFile = true
            }
        }
    }

    val index = bIndex.toMutableMap()

    fun part1(): Long {
        val compact = buildList(capacity = fs.size) {
            var tIdx = index.keys.max()
            var tO = index[tIdx]!!.size

            for (i in fs.indices) {
                if (i >= index[tIdx]!!.offset + tO) break

                when (val b = fs[i]) {
                    null -> {
                        if (tO == 0) {
                            tIdx--
                            tO = index[tIdx]!!.size
                        }

                        add(fs[index[tIdx]!!.offset + --tO])
                    }
                    else -> add(b)
                }
            }
        }

        return compact.withIndex().sumOf { (idx, v) -> if (v == null) 0 else idx * v.toLong() }
    }

    fun part2(): Long {
        val compact = buildList(capacity = fs.size) {
            val q = index.toList().sortedBy { (idx, _) -> idx }.reversed().toMutableList()

            var hIdx = 0
            var i = 0
            while (i in fs.indices && q.isNotEmpty()) {
                val b = fs[i]
                when {
                    b == null -> {
                        var blockSize = fs.drop(i).takeWhile { it == null }.count()
                        while (blockSize > 0) {
                            val n = q.withIndex().firstOrNull { (_, v) -> v.second.size <= blockSize }
                            if (n == null) {
                                i += blockSize
                                for (j in 0 until blockSize) add(null)
                                break
                            }

                            val (nI, next) = n
                            q.removeAt(nI)

                            val (_, file) = next

                            for (j in 0 until file.size) {
                                add(fs[file.offset + j])
                            }

                            blockSize -= file.size
                            i += file.size
                        }
                    }
                    else -> {
                        val f = index[hIdx] ?: break
                        if (!q.removeIf { (idx, _) -> idx == hIdx }) {
                            val blockSize = f.size
                            i += blockSize
                            for (j in 0 until blockSize) {
                                add(null)
                            }

                            hIdx++
                            continue
                        }

                        for (j in 0 until f.size) {
                            add(fs[f.offset + j])
                        }

                        hIdx++
                        i += f.size
                    }
                }
            }
        }

        return compact.withIndex().sumOf { (idx, v) -> if (v == null) 0 else idx * v.toLong() }
    }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}