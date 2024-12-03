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
    val data = readInput().joinToString(separator = "\n")
        .let { str -> "do(n't)?\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex().findAll(str) }
        .map {
            when (it.groupValues[0]) {
                "do()" -> Instruction.Do
                "don't()" -> Instruction.Dont
                else -> Instruction.Mul(it.groupValues[2].toInt(), it.groupValues[3].toInt())
            }
        }

    data class Acc(val `do`: Boolean, val value: Int)

    println("Part 1: ${data.sumOf { instr -> if (instr is Instruction.Mul) instr.a * instr.b else 0 }}")
    println("Part 2: ${data.fold(initial = Acc(true, 0)) { acc, it -> when (it) {
        Instruction.Do -> acc.copy(`do` = true)
        Instruction.Dont -> acc.copy(`do` = false)
        is Instruction.Mul -> if (acc.`do`) acc.copy(value = acc.value + it.a * it.b) else acc
    }}.value}")
}

private sealed class Instruction {
    data class Mul(val a: Int, val b: Int) : Instruction()
    data object Do : Instruction()
    data object Dont : Instruction()
}