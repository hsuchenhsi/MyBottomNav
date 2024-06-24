package imd.ntub.mybottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class FirstFragment : Fragment() {
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseHandler = DatabaseHandler(requireContext())

        // Add some sample contacts to the database
        addSampleContacts()

        // Load HomeFragment when activity starts
        loadFragment(HomeFragment())
    }

    private fun addSampleContacts() {
        if (databaseHandler.getAllContacts().isEmpty()) {
            databaseHandler.addContact(Contact(1,"Stuart", "0967298374"))
            databaseHandler.addContact(Contact(2,"Bob", "0934267840"))
            databaseHandler.addContact(Contact(3,"Kevin", "0989273468"))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Create a FragmentManager
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        // Replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.ConLayout, fragment)
        fragmentTransaction.commit() // save the changes
    }
}
