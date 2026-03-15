package br.com.fiap.ecotrace.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emission_records")
data class EmissionRecordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val category: String,
    val type: String,
    val quantity: Float,
    val coKg: Float
)