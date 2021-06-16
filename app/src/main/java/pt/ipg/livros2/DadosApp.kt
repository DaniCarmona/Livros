package pt.ipg.livros2

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        lateinit var fragmentListaLivros: ListaLivrosFragment
        var livroSelecionado: Livro? = null
    }
}