package com.example.notesofdrowned.database.writedbarchmini

import android.content.ContentValues
import android.util.Log
import com.example.notesofdrowned.database.dbhelperarchmini.DBHelperArchMini

class WorkDBArchMini(private val dbHelper: DBHelperArchMini) {
    data class TableArchiveMini(
        val id: Long,
        val title: String,
        val description: String,
        val colorBookmarkerId: Long
    )
    data class TableArchiveMiniWithColor(
        val id: Long,
        val title: String,
        val description: String,
        val colorBookmarker: String
    )
    data class TableColorBookmarker(
        val id: Long,
        val nameColor: String,
        val color: String
    )

    /*================ Insert tables ================*/

    fun insertNote(columnTitle: String, columnDescription: String, bookmarkerId: Long): Long{
        val db=dbHelper.writableDatabase

        val queryInsert = ContentValues().apply{
            put(DBHelperArchMini.COLUMN_PARENT_TITLE, columnTitle)
            put(DBHelperArchMini.COLUMN_PARENT_DESCRIPTION, columnDescription)
            put(DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY, bookmarkerId)
        }

        val id=db.insert(DBHelperArchMini.TABLE_PARENT, null, queryInsert)

        db.close()
        return id
    }

    fun insertNoteWithColor(columnTitle: String, columnDescription: String, colorBookmarker: String): Long{
        /*---------- READ ----------*/
        val dbRead=dbHelper.readableDatabase

        val cursor=dbRead.rawQuery("""
            SELECT
                child.${DBHelperArchMini.COLUMN_CHILDREN_ID}
            FROM ${DBHelperArchMini.TABLE_CHILDREN} AS child
            WHERE child.${DBHelperArchMini.COLUMN_CHILDREN_COLOR.uppercase()} = "${colorBookmarker.uppercase()}"
            ORDER BY child.${DBHelperArchMini.COLUMN_CHILDREN_ID} DESC
        """.trimIndent(), null)

        var bookmarkerId: Long = 1
        if(cursor.moveToFirst()){
            bookmarkerId = try{
                cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_ID))
            }
            catch (e: IllegalArgumentException){
                1
            }
        }

        dbRead.close()

        /*---------- WRITE ----------*/
        val dbWrite=dbHelper.writableDatabase

        val queryInsert = ContentValues().apply{
            put(DBHelperArchMini.COLUMN_PARENT_TITLE, columnTitle)
            put(DBHelperArchMini.COLUMN_PARENT_DESCRIPTION, columnDescription)
            put(DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY, bookmarkerId)
        }

        val id=dbWrite.insert(DBHelperArchMini.TABLE_PARENT, null, queryInsert)

        dbWrite.close()
        return id
    }

    fun insertBookmarker(nameBookmarker: String, colorBookmarker: String): Long{
        val db=dbHelper.writableDatabase

        val queryInsert = ContentValues().apply{
            put(DBHelperArchMini.COLUMN_CHILDREN_NAME, nameBookmarker)
            put(DBHelperArchMini.COLUMN_CHILDREN_COLOR, colorBookmarker.uppercase())
        }

        var id = try{
            db.insertOrThrow(DBHelperArchMini.TABLE_CHILDREN, null, queryInsert)
        } catch (e: Exception){
            1
        }

        db.close()
        return id
    }

    /*================ Gat All Data ================*/

    fun getAllNode(): List<TableArchiveMini>{
        val db = dbHelper.readableDatabase

        val cursor=db.query(
            DBHelperArchMini.TABLE_PARENT,
            arrayOf(
                DBHelperArchMini.COLUMN_PARENT_ID,
                DBHelperArchMini.COLUMN_PARENT_TITLE,
                DBHelperArchMini.COLUMN_PARENT_DESCRIPTION,
                DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY
            ),
            null,
            null,
            null,
            null,
            "${DBHelperArchMini.COLUMN_PARENT_ID} DESC"
        )

        var listArchiveMini = mutableListOf<TableArchiveMini>()
        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_DESCRIPTION))
            val colorBookmarkerId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY))
            listArchiveMini.add(TableArchiveMini(id, title, description, colorBookmarkerId))
        }

        cursor.close()
        db.close()

        return listArchiveMini
    }

    fun getAllNodeWithColor(): List<TableArchiveMiniWithColor>{
        val db = dbHelper.readableDatabase

        val cursor=db.rawQuery("""
            SELECT
                parent.${DBHelperArchMini.COLUMN_PARENT_ID},
                parent.${DBHelperArchMini.COLUMN_PARENT_TITLE},
                parent.${DBHelperArchMini.COLUMN_PARENT_DESCRIPTION},
                child.${DBHelperArchMini.COLUMN_CHILDREN_COLOR}
            FROM ${DBHelperArchMini.TABLE_PARENT} AS parent
            LEFT JOIN ${DBHelperArchMini.TABLE_CHILDREN} AS child
                ON parent.${DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY} = child.${DBHelperArchMini.COLUMN_CHILDREN_ID}
            ORDER BY parent.${DBHelperArchMini.COLUMN_PARENT_ID} DESC
        """.trimIndent(), null)

        var listArchiveMini = mutableListOf<TableArchiveMiniWithColor>()
        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_PARENT_DESCRIPTION))
            val colorBookmarker = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_COLOR)) ?: ""
            listArchiveMini.add(TableArchiveMiniWithColor(id, title, description, colorBookmarker))
        }

        cursor.close()
        db.close()

        return listArchiveMini
    }

    fun getAllColor(): List<TableColorBookmarker>{
        val db = dbHelper.readableDatabase

        val cursor=db.query(
            DBHelperArchMini.TABLE_CHILDREN,
            arrayOf(
                DBHelperArchMini.COLUMN_CHILDREN_ID,
                DBHelperArchMini.COLUMN_CHILDREN_NAME,
                DBHelperArchMini.COLUMN_CHILDREN_COLOR
            ),
            null,
            null,
            null,
            null,
            "${DBHelperArchMini.COLUMN_CHILDREN_ID} DESC"
        )

        var listColorBookmarker = mutableListOf<TableColorBookmarker>()
        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_ID))
            val nameColor = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_NAME))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_COLOR))
            listColorBookmarker.add(TableColorBookmarker(id, nameColor, color))
        }

        cursor.close()
        db.close()

        return listColorBookmarker
    }

    /*================ Gat Data By "ID" ================*/

    fun getColorBookmarkerById(idBookmarker: Long): String?{
        val db = dbHelper.readableDatabase

        val cursor=db.query(
            DBHelperArchMini.TABLE_CHILDREN,
            arrayOf(DBHelperArchMini.COLUMN_CHILDREN_COLOR),
            "id = ?",
            arrayOf("$idBookmarker"),
            null,
            null,
            "${DBHelperArchMini.COLUMN_PARENT_ID} DESC"
        )

        var colorBookmarker: String? = null
        if(cursor.moveToFirst()){
            colorBookmarker=cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_COLOR))
        }
        cursor.close()
        db.close()

        return colorBookmarker
    }

    fun getIdBookmarkerByColor(colorBookmarker: String): Long{
        val db = dbHelper.readableDatabase

        val cursor=db.query(
            DBHelperArchMini.TABLE_CHILDREN,
            arrayOf(DBHelperArchMini.COLUMN_CHILDREN_ID),
            "${DBHelperArchMini.COLUMN_CHILDREN_COLOR} = ?",
            arrayOf(colorBookmarker),
            null,
            null,
            "${DBHelperArchMini.COLUMN_PARENT_ID} DESC"
        )

        var idBookmarker: Long = 0
        if(cursor.moveToFirst()){
            idBookmarker=cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_ID))
        }
        cursor.close()
        db.close()

        return idBookmarker
    }

    /*================ Remove data ================*/

    fun updateBookmarkerByColor(newName: String, newColor: String, colorBookmarker: String): Unit{
        val dbWrite=dbHelper.writableDatabase

        Log.d("dbMu", "updateBookmarkerByColor: $newName::$newColor <= $colorBookmarker")

        val contentValues = ContentValues().apply {
            put(DBHelperArchMini.COLUMN_CHILDREN_NAME, newName)
            put(DBHelperArchMini.COLUMN_CHILDREN_COLOR, newColor.uppercase())
        }

        dbWrite.update(
            DBHelperArchMini.TABLE_CHILDREN,
            contentValues,
            "${DBHelperArchMini.COLUMN_CHILDREN_COLOR} = ?",
            arrayOf(colorBookmarker.uppercase()),
        )

        dbWrite.close()
    }

    fun updateNoteById(idNote: Long, titleNote: String, descriptionNote: String, idBookmarker: Long): Unit{
        val dbWrite=dbHelper.writableDatabase

        Log.d("dbMu", "updateNoteById: $idNote:$titleNote:$descriptionNote:$idBookmarker")

        val contentValues = ContentValues().apply {
            put(DBHelperArchMini.COLUMN_PARENT_TITLE, titleNote)
            put(DBHelperArchMini.COLUMN_PARENT_DESCRIPTION, descriptionNote)
            put(DBHelperArchMini.COLUMN_PARENT_FOREIGN_KEY, idBookmarker)
        }

        dbWrite.update(
            DBHelperArchMini.TABLE_PARENT,
            contentValues,
            "${DBHelperArchMini.COLUMN_PARENT_ID} = ?",
            arrayOf(idNote.toString()),
        )

        dbWrite.close()
    }

    /*================ Remove data ================*/

    fun delColorBookmarkerByColor(colorBookmarker: String): Unit{
        val dbWrite=dbHelper.writableDatabase

        Log.d("dbMu", "DEL: $colorBookmarker")

        dbWrite.delete(
            DBHelperArchMini.TABLE_CHILDREN,
            "${DBHelperArchMini.COLUMN_CHILDREN_COLOR} = ?",
            arrayOf(colorBookmarker.uppercase()),
        )

        dbWrite.close()
    }

    fun delNoteById(idNote: Long): Unit{
        val dbWrite=dbHelper.writableDatabase

        Log.d("dbMu", "delNoteById: $idNote")

        dbWrite.delete(
            DBHelperArchMini.TABLE_PARENT,
            "${DBHelperArchMini.COLUMN_PARENT_ID} = ?",
            arrayOf(idNote.toString()),
        )

        dbWrite.close()
    }

}