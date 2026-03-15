package br.com.fiap.ecotrace.data.database

import android.content.Context
import androidx.room.*
import br.com.fiap.ecotrace.data.dao.EmissionRecordDao
import br.com.fiap.ecotrace.data.entity.EmissionRecordEntity
import br.com.fiap.ecotrace.data.converter.EmissionCategoryConverter
import br.com.fiap.ecotrace.data.dao.UserDao
import br.com.fiap.ecotrace.data.entity.UserEntity


@Database(
    entities = [EmissionRecordEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(EmissionCategoryConverter::class)
abstract class EcoTraceDatabase : RoomDatabase() {
    abstract fun emissionRecordDao(): EmissionRecordDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: EcoTraceDatabase? = null

        fun getDatabase(context: Context): EcoTraceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EcoTraceDatabase::class.java,
                    "ecotrace_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}