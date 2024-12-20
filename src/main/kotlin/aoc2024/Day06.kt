package aoc2024

import aoc2024.utils.Punto2D

class Day06(input: List<String>) {
    private val grid: List<CharArray> = input.map { it.toCharArray() }

    private val start: Punto2D = grid
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == '^') Punto2D(x, y) else null
            }
        }.filterNotNull().first()

    fun parte1(): Int = moverse().first.size

    fun parte2(): Int =
        moverse()
            .first
            .filterNot { it == start }
            .count { candidate ->
                grid[candidate] = '#'
                moverse().also { grid[candidate] = '.' }.second
            }

    private fun moverse(): Pair<Set<Punto2D>, Boolean> {
        val seen = mutableSetOf<Pair<Punto2D, Punto2D>>()
        var location = start
        var direction = Punto2D.NORTE

        while (grid[location] != null && (location to direction) !in seen) {
            seen += location to direction
            val next = location + direction

            if (grid[next] == '#') direction = direction.direccionGiro()
            else location = next
        }
        return seen.map { it.first }.toSet() to (grid[location] != null)
    }

    private operator fun List<CharArray>.get(at: Punto2D): Char? =
        getOrNull(at.y)?.getOrNull(at.x)

    private operator fun List<CharArray>.set(at: Punto2D, c: Char) {
        this[at.y][at.x] = c
    }
}
