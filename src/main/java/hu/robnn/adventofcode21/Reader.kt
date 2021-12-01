@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package hu.robnn.adventofcode21

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class Reader {

    fun readLines(path: String): List<String> {
        return String(Files.readAllBytes(Paths.get(ClassLoader.getSystemClassLoader().getResource(path).toURI()))).split("\n")
    }
}