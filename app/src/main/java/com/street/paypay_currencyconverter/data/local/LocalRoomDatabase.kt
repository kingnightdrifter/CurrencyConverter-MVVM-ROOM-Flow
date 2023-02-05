package com.street.paypay_currencyconverter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.street.paypay_currencyconverter.data.local.dao.CurrencyDao
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import com.street.paypay_currencyconverter.utils.Constants.DATABASE_NAME

@Database(
    entities = [CurrencyRatesEntity::class, CurrencyNamesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrencyDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: LocalRoomDatabase? = null

        fun getInstance(context: Context): LocalRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LocalRoomDatabase {
            return Room.databaseBuilder(context, LocalRoomDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}