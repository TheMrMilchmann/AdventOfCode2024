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
import kotlin.math.absoluteValue

fun main() {
    val (left, right) = readInput()
        .flatMap { it.trim().split("\\s+".toRegex()) }
        .map(String::toInt)
        .mapIndexed(::Pair)
        .partition { (index, _) -> index % 2 == 0 }
        .let { (first, second) -> first.map { it.second } to second.map { it.second } }

    println("Part 1: ${left.sorted().zip(right.sorted()).sumOf { (l, r) -> (l - r).absoluteValue }}")
    println("Part 2: ${left.sumOf { l -> l * right.count { r -> r == l } }}")
}