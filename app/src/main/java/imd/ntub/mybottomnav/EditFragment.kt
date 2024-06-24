package imd.ntub.mybottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import imd.ntub.mybottomnav.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var databaseHandler: DatabaseHandler
    private var contactId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseHandler = DatabaseHandler(requireContext())

        // 获取传递的联系人ID
        contactId = arguments?.getInt("contactId") ?: 0

        // 加载联系人数据
        loadContactData()

        binding.btnChanges.setOnClickListener {
            saveChanges()
        }

        return view
    }

    private fun loadContactData() {
        val contact = databaseHandler.getContact(contactId)
        if (contact != null) {
            binding.edtName.setText(contact.name)
            binding.edtPhone.setText(contact.tel)
        }
    }

    private fun saveChanges() {
        val name = binding.edtName.text.toString().trim()
        val tel = binding.edtPhone.text.toString().trim()

        if (name.isNotEmpty() && tel.isNotEmpty()) {
            val contact = Contact(id = contactId, name = name, tel = tel)
            databaseHandler.updateContact(contact)
            // 返回到HomeFragment
            parentFragmentManager.popBackStack()
        } else {
            // 提示用户输入姓名和电话号码
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(contactId: Int) = EditFragment().apply {
            arguments = Bundle().apply {
                putInt("contactId", contactId)
            }
        }
    }
}
