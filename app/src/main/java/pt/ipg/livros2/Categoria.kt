package pt.ipg.livros2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria(var id: Long = -1, var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaCategorias.CAMPO_NOME, nome)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Categoria {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaCategorias.CAMPO_NOME)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)

            return Categoria(id, nome)
        }
    }
}