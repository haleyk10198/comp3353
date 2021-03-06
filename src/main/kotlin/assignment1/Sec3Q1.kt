package assignment1

import java.io.File

fun main(args: Array<String>) {

    check(args.size == 1) { "Usage: args = [FILENAME]" }

    val threshold = 0.7
    val k = 100
    val counter = SequenceCounter<MaskedDnaCharacter>(k) { "GC".contains(it.toString().toUpperCase()) }
    val scaledThreshold = threshold * k

    val fileName = args[0]
    val file = File(fileName)
    val fileContent = file.bufferedReader().readLines().drop(1)

    val resultCount = fileContent.asSequence().sumBy { line ->
        line.sumBy { if (counter.readBit(MaskedDnaCharacter(it)) > scaledThreshold) 1 else 0 }
    }

    val fileLength = fileContent.sumBy { it.filter { ch -> "ACGTacgt".contains(ch) }.length }

    println("Do your sanity check: resultCount = $resultCount, fileLength = $fileLength")

    File(fileName).resolveSibling("Sec3Q1output.txt").writeText(resultCount.toString())

}