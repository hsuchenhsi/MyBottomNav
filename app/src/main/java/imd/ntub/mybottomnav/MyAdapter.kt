package imd.ntub.mybottomnav

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var data: ArrayList<Contact>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTel: ImageView = view.findViewById(R.id.imgTel)
        val imgBin: ImageView = view.findViewById(R.id.imgBin)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtTel: TextView = view.findViewById(R.id.txtTel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_first, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = data[position]
        holder.txtName.text = contact.name
        holder.txtTel.text = contact.tel

        holder.itemView.setOnClickListener {
            listener.onItemClick(contact)
        }

        holder.imgTel.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + contact.tel)
            it.context.startActivity(intent)
        }

        holder.imgBin.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, position)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("刪除")
            .setMessage("確定要刪除 ${data[position].name} 嗎？")
            .setPositiveButton("確定") { dialog, which ->
                val contact = data[position]
                DatabaseHandler(context).deleteContact(contact.name)
                data.removeAt(position)
                notifyItemRemoved(position)
                dialog.dismiss()
            }
            .setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun updateData(newData: List<Contact>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
