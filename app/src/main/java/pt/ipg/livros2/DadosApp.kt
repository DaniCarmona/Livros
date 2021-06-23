package pt.ipg.livros2

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        var fragmentListaLivros: ListaLivrosFragment? = null
        var fragmentNovoLivro: NovoLivroFragment? = null
        var livroSelecionado: Livro? = null
    }
}