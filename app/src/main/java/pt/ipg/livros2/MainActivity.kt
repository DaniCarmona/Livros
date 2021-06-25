package pt.ipg.livros2

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import pt.ipg.livros2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_livros
        set(value){
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu
        if(menuAtual == R.menu.menu_lista_livros) {
            atualizaMenuListaLivros(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val processado = return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Livros v.1.0", Toast.LENGTH_LONG).show()
                true
            }
            else -> when(menuAtual){
                    R.menu.menu_lista_livros->(DadosApp.fragment as ListaLivrosFragment).processaOpcaoMenu(item)
                    R.menu.menu_novo_livro->(DadosApp.fragment as NovoLivroFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_livro->(DadosApp.fragment as EditaLivroFragment).processaOpcaoMenu(item)
                    else-> false
            }
        }

        return if(processado) true else super.onOptionsItemSelected(item)

    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaLivros(permiteAlterarEliminar: Boolean){
        menu.findItem(R.id.action_alterar_livro).setVisible(permiteAlterarEliminar)
        menu.findItem(R.id.action_eliminar_livro).setVisible(permiteAlterarEliminar)
    }
}