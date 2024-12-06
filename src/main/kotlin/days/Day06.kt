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

import utils.Direction
import utils.GridPos
import utils.readInput
import utils.toGrid

fun main() {
    val data = readInput().map { it.toCharArray().toList() }.toGrid()

    fun findPath(): List<GridPos> {
        var pos = data.positions.find { data[it] == '^' }!!
        var dir = Direction.N

        return buildList {
            add(pos)

            do {
                val shift = when (dir) {
                    Direction.N -> data::shiftUp
                    Direction.E -> data::shiftRight
                    Direction.S -> data::shiftDown
                    Direction.W -> data::shiftLeft
                }

                val next = shift(pos) ?: break

                if (data[next] == '#') {
                    dir = Direction.entries[((dir.ordinal + Direction.entries.size) - 1) % Direction.entries.size]
                    continue
                }

                pos = next
                add(pos)
            } while (true)
        }
    }

    println("Part 1: ${findPath().distinct().size}")
}