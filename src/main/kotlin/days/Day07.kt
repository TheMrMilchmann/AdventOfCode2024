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
    val data = readInput()
        .map {
            val (res, vals) = it.split(':')
            res.toLong() to vals.trim().split("\\s+".toRegex()).map(String::toLong)
        }

    fun isValid(res: Long, vals: List<Long>, p2: Boolean): Boolean {
        val x = vals.last()
        return when {
            vals.size == 1 -> res == x
            (res % x == 0L) && isValid(res / x, vals.dropLast(1), p2) -> true
            (res >= x) && isValid(res - x, vals.dropLast(1), p2) -> true
            p2 -> {
                val resString = res.toString()
                val xString = x.toString()

                resString.length > xString.length && resString.endsWith(xString) && isValid(resString.dropLast(xString.length).toLong(), vals.dropLast(1), true)
            }
            else -> false
        }
    }

    println("Part 1: ${data.filter { (res, vals) -> isValid(res, vals, p2 = false) }.sumOf { (res, _) -> res }}")
    println("Part 2: ${data.filter { (res, vals) -> isValid(res, vals, p2 = true) }.sumOf { (res, _) -> res }}")
}