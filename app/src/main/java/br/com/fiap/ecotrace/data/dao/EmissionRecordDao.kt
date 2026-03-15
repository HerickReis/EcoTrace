package br.com.fiap.ecotrace.data.dao

import androidx.room.*
import br.com.fiap.ecotrace.data.entity.EmissionRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmissionRecordDao {

    @Query("SELECT * FROM emission_records ORDER BY id DESC")
    fun getAllRecords(): Flow<List<EmissionRecordEntity>>

    @Insert
    suspend fun insertRecord(record: EmissionRecordEntity)

    @Delete
    suspend fun deleteRecord(record: EmissionRecordEntity)

    @Query("DELETE FROM emission_records")
    suspend fun deleteAll()
}