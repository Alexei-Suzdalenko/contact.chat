package contact.messager.ui.main
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import contact.messager.databinding.FragmentConversationBinding
import contact.messager.databinding.FragmentProfileBinding
import contact.messager.databinding.FragmentSearchBinding
import contact.messager.zConverationSearchProfileFragments.ConversationFragment
import contact.messager.zConverationSearchProfileFragments.ProfileFragment
import contact.messager.zConverationSearchProfileFragments.SearchFragment
import contact.messager.util.repository.SaveNewUserRepository.saveImageProfileUser
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
      //  val index = 3
        when( index ){
            2 -> { searchBuinding = FragmentSearchBinding.inflate(inflater, container, false)
                    SearchFragment(searchBuinding!!, requireActivity().applicationContext).initSearchFragment()
                    return searchBuinding!!.root
            }
            3 -> { profileBuinding = FragmentProfileBinding.inflate(inflater, container, false)
                    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            saveImageProfileUser(result.data!!.data, requireContext(), profileBuinding!!)
                        }
                    }
                     ProfileFragment(profileBuinding!!, requireContext().applicationContext, requireActivity(), resultLauncher ).initProfileFragment()
                     return profileBuinding!!.root
            }
            else -> { conversationBinding = FragmentConversationBinding.inflate(inflater, container, false)
                         ConversationFragment(conversationBinding!!, requireActivity().applicationContext).initConversationFragment()
                         return conversationBinding!!.root
            }
        } ///  pageViewModel.text.observeForever { it -> Toast.makeText(requireContext(), "===> $it", Toast.LENGTH_LONG).show() }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment { return PlaceholderFragment().apply { arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, sectionNumber) } } }
        var typeUserImagePlaceholderFragment = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        conversationBinding = null; searchBuinding = null; profileBuinding = null
    }



}