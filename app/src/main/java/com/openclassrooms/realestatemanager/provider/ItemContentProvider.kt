package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.room.PropertyRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ItemContentProvider : ContentProvider() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor {
        if (context != null) {
            val userId = ContentUris.parseId(uri).toInt()
            val cursor: Cursor = PropertyRoomDatabase.getDatabase(context!!,applicationScope).propertyDao().getPropertyWithCursor(userId)
            cursor.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri {
        if (context != null && contentValues != null) {
            val id: Long = PropertyRoomDatabase.getDatabase(context!!,applicationScope).propertyDao().insertContentProvider(Property.fromContentValues(contentValues))
            if (id != 0L) {
                context!!.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        if (context != null) {
            val count: Int = PropertyRoomDatabase.getDatabase(context!!,applicationScope).propertyDao().deleteProperty(ContentUris.parseId(uri).toInt())
            context!!.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to delete row into $uri")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        if (context != null && contentValues != null) {
            val count: Int = PropertyRoomDatabase.getDatabase(context!!,applicationScope).propertyDao().updatePropertyContentProvider(Property.fromContentValues(contentValues))
            context!!.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        const val TABLE_NAME = "property_table"
        val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}