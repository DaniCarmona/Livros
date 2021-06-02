package pt.ipg.livros2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
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
