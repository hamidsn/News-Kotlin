package au.com.news.coding.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class SourceDto(
    val status: String,
    val sources: List<Source>
)

@Entity(tableName = "sources")
data class Source(
    @PrimaryKey()
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String,
    val selected: Boolean = false
)

