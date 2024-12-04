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
import utils.toGrid

fun main() {
    val data = readInput().map { it.toCharArray().toList() }.toGrid()

    fun part1() =
        data.positions
            .filter { data[it] == 'X' }
            .sumOf {
                var c = 0

                if (data.containsAll(it, data::shiftUp, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftLeft, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftDown, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftRight, listOf('X', 'M', 'A', 'S'))) c++

                if (data.containsAll(it, data::shiftUpLeft, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftUpRight, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftDownLeft, listOf('X', 'M', 'A', 'S'))) c++
                if (data.containsAll(it, data::shiftDownRight, listOf('X', 'M', 'A', 'S'))) c++

                c
            }

    fun part2() =
        data.positions
            .filter { data[it] == 'M' || data[it] == 'S' }
            .count {
                val topRight = it.copy(x = it.x + 2)
                if (topRight !in data) return@count false

                (data.containsAll(it, data::shiftDownRight, listOf('M', 'A', 'S')) || data.containsAll(it, data::shiftDownRight, listOf('S', 'A', 'M')))
                    && (data.containsAll(topRight, data::shiftDownLeft, listOf('M', 'A', 'S')) || data.containsAll(topRight, data::shiftDownLeft, listOf('S', 'A', 'M')))
            }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}