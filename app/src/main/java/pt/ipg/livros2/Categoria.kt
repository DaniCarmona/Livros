package pt.ipg.livros2

import android.content.ContentValues

class Categoria (var id: Long = -1, var nome: String) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOME, nome)

        return valores
    }
}