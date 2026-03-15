package br.com.fiap.ecotrace.data.converter

import androidx.room.TypeConverter
import br.com.fiap.ecotrace.model.EmissionCategory

class EmissionCategoryConverter {

    @TypeConverter
    fun fromCategory(category: EmissionCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): EmissionCategory {
        return EmissionCategory.valueOf(value)
    }
}