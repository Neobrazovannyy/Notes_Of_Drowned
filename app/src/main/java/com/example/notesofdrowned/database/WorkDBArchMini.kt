package com.example.notesofdrowned.database.writedbarchmini

import android.content.ContentValues
import com.example.notesofdrowned.database.dbhelperarchmini.DBHelperArchMini

class WorkDBArchMini(private val dbHelper: DBHelperArchMini) {
    data class TableArchiveMini(
        val id: Long,
        val title: String,
        val description: String,
        val colorBookmarkerId: Long
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

    fun insertBookmarker(nameBookmarker: String, colorBookmarker: String): Long{
        val db=dbHelper.writableDatabase

        val queryInsert = ContentValues().apply{
            put(DBHelperArchMini.COLUMN_CHILDREN_NAME, nameBookmarker)
            put(DBHelperArchMini.COLUMN_CHILDREN_COLOR, colorBookmarker)
        }

        val id=db.insert(DBHelperArchMini.TABLE_CHILDREN, null, queryInsert)

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

    fun getColorBookmarker(idBookmarker: Long): String?{
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
            colorBookmarker=cursor.getString(cursor.getColumnIndexOrThrow(DBHelperArchMini.COLUMN_CHILDREN_ID))
        }
        cursor.close()
        db.close()

        return colorBookmarker
    }

}