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
    val data = readInput().map { it.map { it } }.toGrid()
    val antennas = data.positions
        .mapNotNull { pos ->
            when (val a = data[pos]) {
                '.' -> null
                else -> a to pos
            }
        }
        .groupBy(
            keySelector = { (a, _) -> a },
            valueTransform = { (_, pos) -> pos }
        )

    fun findAntinodes(): List<GridPos> = buildList {
        for ((_, positions) in antennas) {
            for (p0 in positions) {
                for (p1 in positions) {
                    if (p0 == p1) continue

                    val dX = p1.x - p0.x.intValue
                    val dY = p1.y - p0.y.intValue

                    val pos = GridPos(p1.x + dX.intValue, p1.y + dY.intValue)
                    if (pos in data) add(pos)
                }
            }
        }
    }

    println("Part 1: ${findAntinodes().distinct().size}")
}