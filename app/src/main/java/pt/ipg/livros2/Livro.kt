package pt.ipg.livros2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Livro (var id: Long = -1, var titulo: String, var autor: String, var idCategoria: Long, var nomeCategoria: String?) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaLivros.CAMPO_TITULO, titulo)
            put(TabelaLivros.CAMPO_AUTOR, autor)
            put(TabelaLivros.CAMPO_ID_CATEGORIA, idCategoria)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Livro {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colTitulo = cursor.getColumnIndex(TabelaLivros.CAMPO_TITULO)
            val colAutor = cursor.getColumnIndex(TabelaLivros.CAMPO_AUTOR)
            val colIdCateg = cursor.getColumnIndex(TabelaLivros.CAMPO_ID_CATEGORIA)
            val colNomeCategoria = cursor.getColumnIndex(TabelaLivros.CAMPO_EXTERNO_NOME_CATEGORIA)

            val id = cursor.getLong(colId)
            val titulo = cursor.getString(colTitulo)
            val autor = cursor.getString(colAutor)
            val idCateg = cursor.getLong(colIdCateg)
             val nomeCategoria = if(colNomeCategoria != -1) cursor.getString(colNomeCategoria) else null

            return Livro(id, titulo, autor, idCateg, nomeCategoria)
        }
    }
}