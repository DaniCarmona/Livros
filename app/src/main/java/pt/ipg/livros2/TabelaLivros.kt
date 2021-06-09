package pt.ipg.livros2

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.FontsContract

class TabelaLivros (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun cria() = db?.execSQL(
            "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_AUTOR TEXT NOT NULL, $CAMPO_ID_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA})"
    )

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
            columns: Array<String>,
            selection: String?,
            selectionArgs: Array<String>?,
            groupBy: String?,
            having: String?,
            orderBy: String?
    ): Cursor? {
        val ultimaColuna =columns.size - 1
        var posCampoExternoNomeCategoria = -1 //-1 indica que não foi pedido
        for(i in 0..ultimaColuna){
            if(columns[i]== CAMPO_EXTERNO_NOME_CATEGORIA) {
                posCampoExternoNomeCategoria = i
                break
            }
        }

        if(posCampoExternoNomeCategoria==-1) { //não existem camppos de outra tabela
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""

        for(i in 0..ultimaColuna){
            val nomeColuna: String

            if(i==posCampoExternoNomeCategoria){
                nomeColuna="${TabelaCategorias.NOME_TABELA}.${TabelaCategorias.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_CATEGORIA"
            }else{
                nomeColuna= "$NOME_TABELA.${columns[i]}"
            }

            if(i>0) colunas += ","
            colunas += nomeColuna
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.NOME_TABELA}.${BaseColumns._ID} =$NOME_TABELA.$CAMPO_ID_CATEGORIA"

        var sqlAdicional = ""
        if(selection != null) sqlAdicional += "WHERE $selection"
        if(groupBy != null){
            sqlAdicional += "GROUP BY $groupBy"
            if(having != null) sqlAdicional += "HAVING $having"
        }

        if(orderBy != null){
            //sqlAdicional += "ORDER BY $orderBy"
        }

        val sql = "SELECT $colunas FROM $tabelas $sqlAdicional"
        return db.rawQuery(sql, selectionArgs)
    }

    companion object{
        const val NOME_TABELA = "livros"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_AUTOR = "autor"
        const val CAMPO_ID_CATEGORIA = "id_categoria"
        const val CAMPO_EXTERNO_NOME_CATEGORIA = "nome_categoria"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_TITULO, CAMPO_AUTOR, CAMPO_ID_CATEGORIA, CAMPO_EXTERNO_NOME_CATEGORIA)
        val TODOS_CAMPOS_TABELA =arrayOf(BaseColumns._ID, CAMPO_TITULO, CAMPO_AUTOR, CAMPO_ID_CATEGORIA)

        //categoria         livros
        //     1   -------    N
    }

}