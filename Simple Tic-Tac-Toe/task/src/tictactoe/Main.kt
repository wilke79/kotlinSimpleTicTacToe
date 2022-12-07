package tictactoe

import java.lang.NumberFormatException

fun printField(signs: MutableList<String>) {
    println("---------")
    for (i in signs.indices) {
        if (signs[i] == "_") {
            signs[i] = " "
        }
        print(
            when (i % 3) {
                0 -> "| ${signs[i]}"
                1 -> " ${signs[i]} "
                else -> "${signs[i]} |\n"
            }
        )
    }
    println("---------")
}

fun main() {
    val input = "_________"
    val signs = input.split("").slice(1..9).toMutableList()
    printField(signs)
    var player = ""
    game@ while (true) {
        player = if (player != "X") "X" else "O"
        input@ while (true) {
            try {
                val (y, x) = readln().split(" ").map { it.toInt() }
                if (!(x in 1..3 && y in 1..3)) {
                    println("Coordinates should be from 1 to 3!")
                } else if (signs[(y - 1) * 3 + x - 1] != " ") {
                    println("This cell is occupied! Choose another one!")
                } else {
                    signs[(y - 1) * 3 + x - 1] = player
                    break@input
                }
            } catch (e: NumberFormatException) {
                println("You should enter numbers!")
            }
        }
        printField(signs)
        val indicesX = signs.mapIndexedNotNull { index, elem ->
            index.takeIf { elem == "X" }
        }
        val indicesO = signs.mapIndexedNotNull { index, elem ->
            index.takeIf { elem == "O" }
        }
        val indicesEmpty = signs.mapIndexedNotNull { index, elem ->
            index.takeIf { elem == " " }
        }
        val rows = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        var rowX = false
        for (row in rows) {
            if (indicesX.containsAll(row)) {
                rowX = true
                break
            }
        }
        var rowO = false
        for (row in rows) {
            if (indicesO.containsAll(row)) {
                rowO = true
                break
            }
        }
        when {
            rowX -> {
                println("X wins")
                break@game
            }
            rowO -> {
                println("O wins")
                break@game
            }
            indicesEmpty.isEmpty() -> {
                println("Draw")
                break@game
            }
        }
    }
}