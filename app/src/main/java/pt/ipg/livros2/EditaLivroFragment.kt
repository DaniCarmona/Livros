package pt.ipg.livros2

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [EditaLivroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaLivroFragment : Fragment(),  LoaderManager.LoaderCallbacks<Cursor>  {

    private lateinit var editTextTitulo: EditText
    private lateinit var editTextAutor: EditText
    private lateinit var spinnerCategoria: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment= this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_livro


        return inflater.inflate(R.layout.fragment_edita_livro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTitulo = view.findViewById<EditText>(R.id.editTextTitulo)
        editTextAutor = view.findViewById<EditText>(R.id.editTextAutor)
        spinnerCategoria = view.findViewById<Spinner>(R.id.spinnerCategorias)


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_CATEGORIAS, null, this)

    }

    fun navegaListaLivros(){
        findNavController().navigate(R.id.action_novolivro_to_listalivros)
    }

    fun guarda(){
        val titulo = editTextTitulo.text.toString()
        if(titulo.isEmpty()){
            editTextTitulo.setError("Preencha o título")
            return
        }

        val autor = editTextAutor.text.toString()
        if(autor.isEmpty()){
            editTextAutor.setError("Preencha o título")
            return
        }

        val categoria = spinnerCategoria.getSelectedItemId()

    }

    fun cancelar(){
        navegaListaLivros()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_livro -> {
                guarda()
                return true
            }
            R.id.action_cancelar_edita_livro -> {
                cancelar()
                return true
            }
            else->return false
        }

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderLivros.ENDERECO_CATEGORIAS,
            TabelaCategorias.TODOS_CAMPOS,
            null, null,
            TabelaCategorias.CAMPO_NOME
        )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerCategoria(data)
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    private fun atualizaSpinnerCategoria(data: Cursor?) {
        spinnerCategoria.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaCategorias.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_CATEGORIAS = 0
    }
}