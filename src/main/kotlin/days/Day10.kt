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

import utils.GridPos
import utils.readInput
import utils.toGrid

fun main() {
    val data = readInput().map { it.toCharArray().map(Char::digitToInt) }.toGrid()
    val reachableEndsByPos = HashMap<GridPos, List<GridPos>>(data.size)

    val (ends, others) = data.positions.associateWith { data[it] }
        .entries
        .sortedByDescending { it.value }
        .partition { it.value == 9 }

    for (end in ends) {
        reachableEndsByPos[end.key] = listOf(end.key)
    }

    var p1 = 0
    var p2 = 0

    for ((pos, v) in others) {
        reachableEndsByPos[pos] =
            (data.shiftLeft(pos)?.takeIf { data[it] == v + 1 }?.let(reachableEndsByPos::get) ?: emptySet()) +
                    (data.shiftUp(pos)?.takeIf { data[it] == v + 1 }?.let(reachableEndsByPos::get) ?: emptySet()) +
                    (data.shiftRight(pos)?.takeIf { data[it] == v + 1 }?.let(reachableEndsByPos::get) ?: emptySet()) +
                    (data.shiftDown(pos)?.takeIf { data[it] == v + 1 }?.let(reachableEndsByPos::get) ?: emptySet())

        if (v == 0) {
            p1 += reachableEndsByPos[pos]!!.distinct().count()
            p2 += reachableEndsByPos[pos]!!.count()
        }
    }

    println("Part 1: $p1")
    println("Part 2: $p2")
}