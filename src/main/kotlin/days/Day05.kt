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
    val (rules, updates) = readInput()
        .filter { it.isNotBlank() }
        .partition { '|' in it }
        .let { (rules, updates) ->
            rules.map { it.split('|').map { it.toInt() } } to updates.map { it.split(',').map { it.toInt() } }
        }

    fun part1(): Int =
        updates
            .filter { update -> rules.filter(update::containsAll).all { (r1, r2) -> update.indexOf(r1) < update.indexOf(r2) } }
            .sumOf { update -> update[update.size / 2] }

    fun part2(): Int =
        updates
            .filter { update -> !rules.filter(update::containsAll).all { (r1, r2) -> update.indexOf(r1) < update.indexOf(r2) } }
            .map { update ->
                update.sortedWith { l, r ->
                    val rule = rules.find { l in it && r in it }

                    when {
                        l == r -> 0
                        rule != null -> rule.indexOf(l).compareTo(rule.indexOf(r))
                        else -> 1
                    }
                }
            }
            .sumOf { update -> update[update.size / 2] }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}