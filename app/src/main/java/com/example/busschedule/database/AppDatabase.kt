/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

/**
 * Defines a database and specifies data tables that will be used.
 * Version is incremented as new tables/columns are added/removed/changed.
 * You can optionally use this class for one-time setup, such as pre-populating a database.
 */
@Database(entities = arrayOf(Schedule::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    /**The database class allows other classes easy access to the DAO classes. Add an abstract function that returns a ScheduleDao.*/
    abstract fun scheduleDao(): ScheduleDao

    /**When using an AppDatabase class, you want to ensure that only one instance of the database exists
     * to prevent race conditions or other potential issues. The instance is stored in the companion object,
     * and you'll also need a method that either returns the existing instance, or creates the database for the first time.
     * This is defined in the companion object. Add the following companion object just below the scheduleDao() function.*/
    companion object{

        /**add a property called INSTANCE of type AppDatabase. This value is initially set to null, so the type is marked with a ?. This is also marked with a @Volatile annotation. While the details about when to use a volatile property are a bit advanced, simply you'll want to use it for your AppDatabase instance to avoid potential bugs.*/
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**In the implementation for getDatabase(), you use the Elvis operator to either return the existing instance of the database (if it already exists) or create the database for the first time if needed. In this app, since the data is prepopulated. You also call createFromAsset() to load the existing data. The bus_schedule.db file can be found in the assets.database package in your project.*/
        fun getDatabase(context:Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context,
                    AppDatabase::class.java,"app_database")
                    .createFromAsset("database/bus_schedule.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
