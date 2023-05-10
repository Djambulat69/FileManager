package com.isaev.filemanager

import java.io.File

enum class SortOption(
    val fileComparator: Comparator<File>
) {
    NEWEST_FIRST(compareBy<File>({ it.isDirectory }, { it.lastModified() }).reversed()),
    OLDEST_FIRST(compareBy({ it.isFile }, { it.lastModified() })),
    LARGEST_FIRST(compareBy<File>({ it.isDirectory }, { it.length() }).reversed()),
    SMALLEST_FIRST(compareBy({ it.isFile }, { it.length() })),
    NAME_A_Z(compareBy({ it.isFile }, { it.name })),
    NAME_Z_A(compareBy<File>({ it.isDirectory }, { it.name }).reversed())
}
