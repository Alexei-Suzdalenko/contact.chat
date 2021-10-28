package contact.chat.ui.main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import contact.chat.databinding.FragmentConversationBinding
import contact.chat.databinding.FragmentProfileBinding
import contact.chat.databinding.FragmentSearchBinding
import contact.chat.util.components.ChatActivity.ConversationFragment
import contact.chat.util.components.ChatActivity.ProfileFragment
import contact.chat.util.components.ChatActivity.SearchFragment

class PlaceholderFragment : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    private var conversationBinding: FragmentConversationBinding? = null
    private var searchBuinding: FragmentSearchBinding? = null
    private var profileBuinding: FragmentProfileBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply { setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1

        when( index ){
            2 -> {
                searchBuinding = FragmentSearchBinding.inflate(inflater, container, false)
                SearchFragment(searchBuinding!!, requireActivity().applicationContext).initSearchFragment()
                return searchBuinding!!.root
            }
            3 -> {
                profileBuinding = FragmentProfileBinding.inflate(inflater, container, false)
                ProfileFragment(profileBuinding!!, requireContext().applicationContext).initProfileFragment()
                return profileBuinding!!.root
            }
            else -> {
                conversationBinding = FragmentConversationBinding.inflate(inflater, container, false)
                ConversationFragment(conversationBinding!!, requireActivity().applicationContext).initConversationFragment()
                return conversationBinding!!.root
            }
        }

      //  pageViewModel.text.observeForever { it -> Toast.makeText(requireContext(), "===> $it", Toast.LENGTH_LONG).show() }

    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment { return PlaceholderFragment().apply { arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, sectionNumber) } } }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        conversationBinding = null; searchBuinding = null; profileBuinding = null
    }
}