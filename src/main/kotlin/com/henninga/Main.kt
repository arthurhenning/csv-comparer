package com.henninga

import com.google.common.collect.Maps
import org.apache.commons.csv.CSVFormat
import java.io.File
import java.io.FileReader
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Insert path to first CSV file")
    val csvPath1 = readLine()
    println("Insert path to second CSV file")
    val csvPath2 = readLine()

    println("Insert a row identifier (column name, case sensitive)")
    val columnIdentifier = readLine()

    File("output.txt").bufferedWriter(Charsets.UTF_8, 99999999).use { out ->
        val durationMillis = measureTimeMillis {
            val document1 = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(FileReader(csvPath1))
            val document2 = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(FileReader(csvPath2))
            val rows1 = document1.records
            val rows2 = document2.records

            if (document1.headerMap.size != document2.headerMap.size) {
                println("Header size is different")
                println("CSV 1: " + document1.headerMap.size)
                println("CSV 2: " + document2.headerMap.size)
            } else if (!document1.headerMap.keys.containsAll(document2.headerMap.keys)) {
                println("Headers are different")
                println(document1.headerMap.keys.removeAll(document2.headerMap.keys))
            } else if (rows1.size != rows2.size) {
                println("Row count is different")
                println("CSV 1: " + rows1.size)
                print("CSV 2: " + rows2.size)
            } else {
                var rowMatched = 0
                var rowMatchedWithoutColumns = 0
                var rowCount = 0
                rows1.forEach { row1 ->
                    rows2.forEach { row2 ->
                        if (row1.get(columnIdentifier).equals(row2.get(columnIdentifier))) {
                            if (row1.toMap() == row2.toMap()) {
                                rowMatched++
                            } else {
                                out.append("Row for " + columnIdentifier + "=" + row1.get(columnIdentifier) + " matched, but columns ")
                                out.appendln(Maps.difference(row1.toMap(), row2.toMap()).toString())
                                rowMatchedWithoutColumns++
                            }
                        }
                    }
                    rowCount++
                }
                println("------------------------------")
                println("Rows parsed: " + rowCount)
                println("Rows matched: " + rowMatched)
                out.appendln("Row identifier matched, but columns differ: " + rowMatchedWithoutColumns)
            }
        }
        println("Duration: $durationMillis ms")
        println("See output.txt file for details. Press any key and enter to exit the application")
        readLine()
    }
}
