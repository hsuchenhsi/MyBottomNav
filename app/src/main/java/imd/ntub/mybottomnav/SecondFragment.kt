package imd.ntub.mybottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        // 找到 EditText 和 Button
        edtName = view.findViewById(R.id.edtName)
        edtPhone = view.findViewById(R.id.edtPhone)
        btnSave = view.findViewById(R.id.btnChanges)

        // 設置保存按鈕的點擊事件
        btnSave.setOnClickListener {
            saveContact() // 執行保存聯絡人的方法
        }

        return view
    }

    private fun saveContact() {
        // 獲取用戶輸入的名稱和電話號碼
        val name = edtName.text.toString().trim()
        val phone = edtPhone.text.toString().trim()

        // 檢查用戶是否輸入了有效的資料
        if (name.isEmpty() || phone.isEmpty()) {
            showToast("請輸入姓名和電話號碼")
            return
        }

        // 將 name 和 phone 存儲到 SQLite 資料庫中
        val dbHelper = DatabaseHelper(requireContext())
        val inserted = dbHelper.insertContact(name, phone)

        if (inserted) {
            // 提示用戶聯絡人已新增
            showToast("聯絡人已新增")
            // 清空輸入框
            edtName.setText("")
            edtPhone.setText("")
            // 收回鍵盤
            hideKeyboard()
        } else {
            // 提示用戶新增聯絡人失敗
            showToast("新增聯絡人失敗")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(edtPhone.windowToken, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}
