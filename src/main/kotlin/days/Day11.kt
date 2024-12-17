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

import utils.*

fun main() {
    val data = readInput().single().split("\\s+".toRegex()).map(String::toLong)

    fun blink(value: Long): List<Long> = when {
        value == 0L -> listOf(1L)
        value.toString().count() % 2 == 0 -> value.toString().let { listOf(it.substring(0, it.length / 2).toLong(), it.substring(it.length / 2).toLong()) }
        else -> listOf(value * 2024L)
    }

    fun part1(): Int {
        data class Key(val v: Long, val iterations: Int)
        val cache = mutableMapOf<Key, Int>()

        fun solve(value: Long, iterations: Int): Int {
            if (iterations == 0) return 1
            cache[Key(value, iterations)]?.let { return it }
            return blink(value).sumOf { solve(it, iterations - 1) }.also { cache[Key(value, iterations)] = it }
        }

        return data.sumOf { solve(it, iterations = 25) }
    }

    println("Part 1: ${part1()}")
}