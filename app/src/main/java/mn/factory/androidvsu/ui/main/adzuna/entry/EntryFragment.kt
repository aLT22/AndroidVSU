package mn.factory.androidvsu.ui.main.adzuna.entry


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_entry.*
import mn.factory.androidvsu.R
import mn.factory.androidvsu.ui.main.MainActivity

class EntryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_jobs -> {
                    (activity as MainActivity).showFragment(R.id.action_entryFragment_to_jobListFragment)
                    true
                }
                R.id.action_cars -> {
                    (activity as MainActivity).showFragment(R.id.action_entryFragment_to_carListFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }*/
    }

}
