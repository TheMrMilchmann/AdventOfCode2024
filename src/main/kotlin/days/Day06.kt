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
    val data = readInput().map { it.toCharArray().toList() }.toGrid()

    data class PathNode(val pos: GridPos, val dir: Direction)

    fun Grid<Char>.findPath(): List<PathNode>? {
        var pos = positions.find { this[it] == '^' }!!
        var dir = Direction.N

        val visited = mutableSetOf<PathNode>()

        return buildList {
            add(PathNode(pos, dir).also(visited::add))

            do {
                val shift = when (dir) {
                    Direction.N -> ::shiftUp
                    Direction.E -> ::shiftRight
                    Direction.S -> ::shiftDown
                    Direction.W -> ::shiftLeft
                }

                val next = shift(pos) ?: break

                if (this@findPath[next] == '#') {
                    dir = Direction.entries[((dir.ordinal + Direction.entries.size) - 1) % Direction.entries.size]
                    continue
                }

                pos = next
                val pathNode = PathNode(pos, dir)
                if (pathNode in visited) return null
                add(PathNode(pos, dir).also(visited::add))
            } while (true)
        }
    }

    fun part2(): Int =
        data.findPath()!!
            .map(PathNode::pos)
            .distinct()
            .let { path -> path.filter { it != path.first() } }
            .count { data.map { pos, v -> if (pos == it) '#' else v }.findPath() == null }

    println("Part 1: ${data.findPath()!!.map(PathNode::pos).distinct().size}")
    println("Part 2: ${part2()}")
}