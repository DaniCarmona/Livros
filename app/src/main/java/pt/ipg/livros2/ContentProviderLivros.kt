package pt.ipg.livros2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderLivros : ContentProvider(){
    private var bdLivrosOpenHelper : BdLivrosOpenHelper? = null

    override fun onCreate(): Boolean {
        bdLivrosOpenHelper = BdLivrosOpenHelper(context)


        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdLivrosOpenHelper!!.readableDatabase

        return when(getUriMatcher().match(uri)){
            URI_LIVROS -> TabelaLivros(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>,
                null,
                null,
                sortOrder
                )
            URI_LIVRO_ESPECIFICO -> TabelaLivros(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!), // id
                null,
                null,
                null
            )
            URI_CATEGORIAS -> TabelaCategorias(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>,
                null,
                null,
                sortOrder
            )
            URI_CATEGORIA_ESPECIFICO ->TabelaCategorias(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!), // id
                null,
                null,
                null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when(getUriMatcher().match(uri)){
            URI_LIVROS -> "$MULTIPLOS_ITEMS/$LIVROS"
            URI_LIVRO_ESPECIFICO -> "$UNICO_ITEM/$LIVROS"
            URI_CATEGORIAS -> "$MULTIPLOS_ITEMS/$CATEGORIAS"
            URI_CATEGORIA_ESPECIFICO -> "$UNICO_ITEM/$CATEGORIAS"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdLivrosOpenHelper!!.writableDatabase

        val id = when(getUriMatcher().match(uri)){
            URI_LIVROS -> TabelaLivros(bd).insert(values!!)
            URI_CATEGORIAS -> TabelaCategorias(bd).insert(values!!)
            else -> -1
        }

        if (id == -1L) return null
        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdLivrosOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_LIVRO_ESPECIFICO -> TabelaLivros(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!) // id
            )
            URI_CATEGORIA_ESPECIFICO ->TabelaCategorias(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdLivrosOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_LIVRO_ESPECIFICO -> TabelaLivros(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!) // id
            )
            URI_CATEGORIA_ESPECIFICO ->TabelaCategorias(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    companion object{
        private const val AUTHORITY = "pt.ipg.livros2"
        private const val LIVROS = "livros"
        private const val CATEGORIAS = "categorias"

        private const val URI_LIVROS = 100
        private const val URI_LIVRO_ESPECIFICO = 101
        private const val URI_CATEGORIAS = 200
        private const val URI_CATEGORIA_ESPECIFICO = 201

        private const val MULTIPLOS_ITEMS = "vnd,android.cursor.dir"
        private const val UNICO_ITEM = "vnd,android.cursor.item"

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, LIVROS, 100)
            uriMatcher.addURI(AUTHORITY, "$LIVROS/#", 101)
            uriMatcher.addURI(AUTHORITY, CATEGORIAS, 200)
            uriMatcher.addURI(AUTHORITY, "$CATEGORIAS/#", 201)

            // .../livros (cursor) 100
            // .../livros/6 (item) 101
            // .../categorias (cursor) 200
            // .../categorias/3 (item) 201
            // .../filmes/ (não existe) 0


            return uriMatcher
        }
    }

}
