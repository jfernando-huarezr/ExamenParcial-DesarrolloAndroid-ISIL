package com.huarezreyes.eparcial.datos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatosAlumnos(context: Context
): SQLiteOpenHelper(context, "isil.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE alumnos(" +
                "idalumno INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha DEFAULT CURRENT_TIMESTAMP,"  +
                "nombres TEXT, " +
                "apellidos TEXT, " +
                "edad INTEGER, " +
                "correo TEXT," +
                "carrera TEXT)")
    }

    fun registrarAlumno(datosAlumnos: DatosAlumnos, nombres: String, apellidos: String, edad: Int, correo: String, carrera: String ): Long {
        val db = datosAlumnos.writableDatabase
        //db.execSQL("INSERT INTO ...")
        val contentValues = ContentValues().apply {
            put("nombres", nombres)
            put("apellidos", apellidos)
            put("edad", edad)
            put("correo", correo)
            put("carrera", carrera)
        }

        val autonumerico = db.insert("alumnos", null, contentValues)
        return autonumerico
    }

    fun alumnosSelect(datosAlumnos: DatosAlumnos) : Cursor {
        val db = datosAlumnos.readableDatabase
        val sql = "SELECT * from alumnos ORDER BY idalumno DESC"
        return db.rawQuery(sql, null)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}