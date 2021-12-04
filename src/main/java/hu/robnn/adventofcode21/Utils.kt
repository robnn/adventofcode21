package hu.robnn.adventofcode21

fun transpose(mat: List<List<String>>): List<List<String>> {
    val transposed = MutableList(mat.size, init = { MutableList(mat[it].size) { "" } })
    for (i in mat.indices) {
        for (j in mat[i].indices)
            transposed[j][i] = mat[i][j]
        }
    return transposed
}