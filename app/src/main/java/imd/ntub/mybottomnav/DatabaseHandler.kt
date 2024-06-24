package imd.ntub.mybottomnav

import android.content.ContentValues
import android.content.Context
import android.database.Cursor



class DatabaseHandler(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val db = dbHelper.writableDatabase

    fun addContact(contact: Contact) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, contact.name)
            put(DatabaseHelper.COLUMN_TEL, contact.tel)
        }
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val cursor: Cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val tel = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEL))
                contacts.add(Contact(id, name, tel))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return contacts
    }

    fun getContact(id: Int): Contact? {
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_NAME, null,
            "${DatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
            val tel = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEL))
            cursor.close()
            return Contact(id, name, tel)
        }
        cursor.close()
        return null
    }

    fun updateContact(contact: Contact): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, contact.name)
            put(DatabaseHelper.COLUMN_TEL, contact.tel)
        }
        return db.update(DatabaseHelper.TABLE_NAME, values, "${DatabaseHelper.COLUMN_ID}=?", arrayOf(contact.id.toString()))
    }

    fun deleteContact(id: String) {
        db.delete(DatabaseHelper.TABLE_NAME, "${DatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()))
    }
}
