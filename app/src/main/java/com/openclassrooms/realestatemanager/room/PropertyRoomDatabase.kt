package com.openclassrooms.realestatemanager.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.models.Agent
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Property::class, Agent::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PropertyRoomDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun agentDao(): AgentDao

    private class PropertyDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val propertyDao = database.propertyDao()

                    // Add sample words.
                    var property = Property(0,"13/12/2021","Loft","School,Parc",1200000,"",
                            320,"5","1","2","A white loft with a lot of luminosity. Two bedrooms and a lot of rooms. Kitchen integrated.",false,null,"Paris",
                            "9 rue Jean Daudin","France","5A","75000", arrayListOf(),"", "Bob KIRTH")
                    propertyDao.insert(property)
                    property = Property(1,"10/12/2021","House","Parc",1800000,"",
                            310,"8","2","3","Villu is a magnificent log clubhouse with a massive terrace and impressingly grand vestibule.",false,null,"Paris",
                            "20 Rue Oudinot","France","","75000",arrayListOf(),"", "Jean FRUIT")
                    propertyDao.insert(property)
                    property = Property(2,"","House","Parc,Shop",2400000,"",
                            310,"8","2","3","This is a new description",true,"05/12/2021","Paris",
                            "22 Rue François Bonvin","France","","75000",arrayListOf(),"", "Paul LIME")
                    propertyDao.insert(property)

                    val agentDao = database.agentDao()

                    var agent = Agent("Paul LIME")
                    agentDao.insert(agent)
                    agent = Agent("Jean FRUIT")
                    agentDao.insert(agent)
                    agent = Agent("Bob KIRTH")
                    agentDao.insert(agent)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PropertyRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): PropertyRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PropertyRoomDatabase::class.java,
                        "property_database"
                )
                        .addCallback(PropertyDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance

            }
        }
    }

}