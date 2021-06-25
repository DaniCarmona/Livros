package pt.ipg.livros2

import androidx.fragment.app.Fragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var livroSelecionado: Livro? = null
    }
}