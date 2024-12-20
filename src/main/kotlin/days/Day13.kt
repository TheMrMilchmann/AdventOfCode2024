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
import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    data class SlotMachine(
        val dA: Pair<Long, Long>,
        val dB: Pair<Long, Long>,
        val prize: Pair<Long, Long>
    )

    val data = readInput()
        .filter(String::isNotBlank)
        .windowed(size = 3, step = 3)
        .map { lines ->
            val dA = lines[0].substringAfter("X+").trim().split(", Y+").map { it.trim().toLong() }.let { it[0] to it[1] }
            val dB = lines[1].substringAfter("X+").trim().split(", Y+").map { it.trim().toLong() }.let { it[0] to it[1] }
            val prize = lines[2].substringAfter("X=").trim().split(", Y=").map { it.trim().toLong() }.let { it[0] to it[1] }
            SlotMachine(dA, dB, prize)
        }

    fun List<SlotMachine>.solve() = fold(initial = 0L) { acc, s ->
        val b = ((s.dA.first * s.prize.second - s.dA.second * s.prize.first) / (s.dA.first * s.dB.second - s.dA.second * s.dB.first).toDouble()).toLong()
        val a = ((s.prize.first - b * s.dB.first) / s.dA.first.toDouble()).toLong()

        if (s.dA.second * a + s.dB.second * b == s.prize.second && s.dA.first * a + s.dB.first * b == s.prize.first) acc + (3 * a + b) else acc
    }

    println("Part 1: ${data.solve()}")
    println("Part 2: ${data.map { it.copy(prize = it.prize.first + 10000000000000L to it.prize.second + 10000000000000L) }.solve()}")
}