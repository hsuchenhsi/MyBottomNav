package imd.ntub.mybottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeFragment : Fragment(), MyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        databaseHandler = DatabaseHandler(requireContext())

        // 设置RecyclerView的布局管理器和适配器
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(ArrayList(), this) // 初始化空的数据列表
        recyclerView.adapter = adapter

        loadContactsFromDatabase()

        return view
    }

    fun reloadData() {
        loadContactsFromDatabase()
    }


    private fun loadContactsFromDatabase() {
        val contactList = databaseHandler.getAllContacts()
        adapter.updateData(contactList)
    }

    override fun onItemClick(contact: Contact) {
        val fragment = EditFragment.newInstance(contact.id)
        parentFragmentManager.beginTransaction()
            .replace(R.id.ConLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
