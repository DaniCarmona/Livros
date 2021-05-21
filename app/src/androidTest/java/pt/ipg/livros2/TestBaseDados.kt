package pt.ipg.livros2

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdLivrosOpenHelper() = BdLivrosOpenHelper(getAppContext())
    private fun getTabelaCategorias(db: SQLiteDatabase) = TabelaCategorias(db)
    private fun getTabelaLivros(db: SQLiteDatabase) = TabelaLivros(db)
    private fun insereCategoria(tabelaCategorias: TabelaCategorias, categoria: Categoria): Long {
        val id = tabelaCategorias.insert(categoria.toContentValues())
        assertNotEquals(-1, id)
        return id
    }
    private fun getCategoriaBD(
        tabelaCategorias: TabelaCategorias, id: Long    ): Categoria {
        val cursor = tabelaCategorias.query(
            TabelaCategorias.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Categoria.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdLivrosOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBdLivrosOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }


    @Test
    fun consegueInserirCategorias(){
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = getTabelaCategorias(db)
        val categoria = Categoria(nome="Drama")

        categoria.id = insereCategoria(getTabelaCategorias(db), Categoria(nome="Drama"))
        val categoriaBD = getCategoriaBD(tabelaCategorias, categoria.id)
        assertEquals(categoria, categoriaBD)
        db.close()
    }

    @Test
    fun consegueAlterarCategorias(){
        val db = getBdLivrosOpenHelper().writableDatabase

        val tabelaCategorias = getTabelaCategorias(db)

        val categoria = Categoria(nome="Sci")

        categoria.id = insereCategoria(tabelaCategorias, categoria)
        categoria.nome="Sci-Fi"
        val registosAlterados = tabelaCategorias.update(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarCategorias(){
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = getTabelaCategorias(db)
        val categoria = Categoria(nome="Teste")


        categoria.id = insereCategoria(tabelaCategorias, categoria)
        
        val registosEliminados =tabelaCategorias.delete(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCategorias(){
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = getTabelaCategorias(db)
        val categoria = Categoria(nome="Aventura")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        val categoriaBD = getCategoriaBD(tabelaCategorias, categoria.id)
        assertEquals(categoria, categoriaBD)


        db.close()
    }




}