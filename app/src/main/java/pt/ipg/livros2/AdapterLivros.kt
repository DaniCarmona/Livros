package pt.ipg.livros2

import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView

class AdapterLivros(var cursor : Cursor? = null) : RecyclerView.Adapter<AdapterLivros.ViewHolderLivro>  {
    class ViewHolderLivro(itemView : View ) : RecyclerView.ViewHolder{

    }
}