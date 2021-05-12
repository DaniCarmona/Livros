package pt.ipg.livros2

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaCategorias(db: SQLiteDatabase?) : BaseColumns{
    private val db : SQLiteDatabase? = db
    fun cria() {
        db?.execSQL(
            "CREATE TABLE categorias(_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL )"
        )

    }

}