package pt.ipg.livros2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.livros2.databinding.FragmentNovoLivroBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoLivroFragment : Fragment() {

    private var _binding: FragmentNovoLivroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNovoLivroBinding.inflate(inflater, container, false)

        DadosApp.fragmentNovoLivro= this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_livro

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun guarda(){

    }

    fun cancelar(){
        findNavController().navigate(R.id.action_novolivro_to_listalivros)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_livro -> {
                guarda()
                return true
            }
            R.id.action_cancelar_novo_livro -> {
                cancelar()
                return true
            }
            else->return false
        }

    }
}