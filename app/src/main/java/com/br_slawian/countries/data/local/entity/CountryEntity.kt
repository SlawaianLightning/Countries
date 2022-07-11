package com.br_slawian.countries.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.br_slawian.countries.domain.model.Country
import com.br_slawian.countries.domain.model.CountryItem

@Entity(
    tableName = "CountryEntity"
)
data class CountryEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "continent") val continent: String,
    @ColumnInfo(name = "emoji") val emoji: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "capital") val capital: String,
    @ColumnInfo(name = "language") val language: String,
    @PrimaryKey @ColumnInfo(name = "id") val id: Int? = null,
) {
    fun toCountry() = Country(
        name,
        phone,
        code,
        continent,
        emoji,
        currency,
        capital,
        language
    )

    fun toCountryItem() = CountryItem(
        name,
        emoji
    )
}
