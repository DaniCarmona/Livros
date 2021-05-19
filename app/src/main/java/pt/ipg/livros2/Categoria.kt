package pt.ipg.livros2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Categoria (var id: Long = -1, var nome: String) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOME, nome)

        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Categoria{
            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNome = cursor.getColumnIndex(TabelaCategorias.CAMPO_NOME)

            val id = cursor.getLong(posCampoId)
            val nome = cursor.getString(posCampoNome)

            return Categoria(id, nome)
        }
    }
}