package com.myProj.sample.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.myProj.sample.R
import com.myProj.sample.model.DogBreed
import com.myProj.sample.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogsListAdapter = DogsListAdapter(arrayListOf())
    private val dogsListed = arrayListOf<DogBreed>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }
        // pull to refresh
        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listError.visibility = View.GONE
            networkError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(this, Observer {dogs ->
            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogsListed.addAll(dogs)
                dogsListAdapter.updateDogList(dogs)
            }
        })
        viewModel.dogsLoadError.observe(this, Observer {isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.networkError.observe(this, Observer {isError ->
            isError?.let {
                networkError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    networkError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchMenu = menu.findItem(R.id.action_search)
        val searchView = searchMenu.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<DogBreed>()
        for(dog in dogsListed) {
            dog.dogBreed?.let {
                if(it.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(dog)
                }
            }
        }
        if(filteredList.isEmpty()) {
            Toast.makeText(context, "No data found..", Toast.LENGTH_SHORT).show()
        } else {
            dogsListAdapter.updateDogList(filteredList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionSettings -> {
                view?.let {
                    Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToSettingsFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        buttonDetails.setOnClickListener {
//            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
//            // pass argument to the navigation
//            action.dogUUID = 5
//            Navigation.findNavController(it).navigate(action)
//        }
//    }
}