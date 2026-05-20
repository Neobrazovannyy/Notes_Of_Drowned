package com.example.notesofdrowned.database.dbhelperarchmini

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Locale

class DBHelperArchMini(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME="archive_mini.sqlite"
        private const val DATABASE_VERSION = 1

        const val TABLE_CHILDREN = "color_bookmarker"
        const val COLUMN_CHILDREN_ID = "id"
        const val COLUMN_CHILDREN_NAME = "name_bookmarker"
        const val COLUMN_CHILDREN_COLOR = "color"

        const val TABLE_PARENT = "archive_mini"
        const val COLUMN_PARENT_ID = "id"
        const val COLUMN_PARENT_TITLE = "title"
        const val COLUMN_PARENT_DESCRIPTION = "description"
        const val COLUMN_PARENT_FOREIGN_KEY = "color_bookmarker_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS $TABLE_CHILDREN(
                $COLUMN_CHILDREN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CHILDREN_NAME TEXT,
                $COLUMN_CHILDREN_COLOR TEXT NOT NULL UNIQUE
            )
        """)

        db.execSQL("""
            INSERT INTO "$TABLE_CHILDREN" ($COLUMN_CHILDREN_NAME, $COLUMN_CHILDREN_COLOR) 
            VALUES ('default', '464646')
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS $TABLE_PARENT(
                $COLUMN_PARENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PARENT_TITLE TEXT NOT NULL UNIQUE,
                $COLUMN_PARENT_DESCRIPTION TEXT,
                $COLUMN_PARENT_FOREIGN_KEY INTEGER,
                FOREIGN KEY ($COLUMN_PARENT_FOREIGN_KEY)  REFERENCES $TABLE_CHILDREN (id)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Удаляем старую и создаём новую (для разработки)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHILDREN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARENT")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHILDREN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARENT")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
        db.setLocale(Locale("ru"))
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
    }

}