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
import kotlin.math.*

fun main() {
    val data = readInput().map { it.toCharArray().toList() }.toGrid()

    val regions = data.positions.associateWith(data::get)
        .entries
        .groupBy { it.value }
        .flatMap { (_, entries) ->
            val regions = mutableListOf<MutableList<GridPos>>()

            infix fun GridPos.isAdjacentTo(other: GridPos) =
                (this.x == other.x && (this.y.intValue - other.y.intValue).absoluteValue == 1) ||
                        (this.y == other.y && (this.x.intValue - other.x.intValue).absoluteValue == 1)

            val queue = ArrayDeque(entries)

            while (queue.isNotEmpty()) {
                val (first, _) = queue.removeFirst()
                val region = mutableListOf(first)

                do {
                    val (next, _) = queue.find { (pos, _) -> region.any { it.isAdjacentTo(pos) } } ?: break
                    queue.removeIf { (pos, _) -> next == pos }
                    region.add(next)
                } while (true)

                regions.add(region)
            }

            regions.map(MutableList<GridPos>::toList)
        }

    fun GridPos.getAdjacentPositions(): Set<GridPos> = buildSet {
        add(data.shiftLeftUnbound(this@getAdjacentPositions))
        add(data.shiftUpUnbound(this@getAdjacentPositions))
        add(data.shiftRightUnbound(this@getAdjacentPositions))
        add(data.shiftDownUnbound(this@getAdjacentPositions))
    }

    fun List<GridPos>.toPerimeter() =
        flatMap { it.getAdjacentPositions() }.filter { it !in this }

    println("Part 1: ${regions.sumOf { it.toPerimeter().size.toLong() * it.size }}")
}