package com.tabata.hoshiimon.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tabata.hoshiimon.R
import com.tabata.hoshiimon.database.AppDatabase
import com.tabata.hoshiimon.database.Group
import com.tabata.hoshiimon.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).dao
        val viewModelFactory = HomeViewModelFactory(dao, application)
        homeViewModel = ViewModelProvider(
            this, viewModelFactory
        )[HomeViewModel::class.java]
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.groupName.text = getString(R.string.home)

        homeViewModel.getDefaultGroup()

        val groupListView = binding.groupListView
        groupListView.layoutManager = LinearLayoutManager(requireContext())

        val itemListView = binding.itemListView
        itemListView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.groupDataSet.observe(viewLifecycleOwner) {
            val dataSet = homeViewModel.groupDataSet.value
            val listViewAdapter = dataSet?.let { GroupListViewAdapter(it) }
            groupListView.adapter = listViewAdapter
            listViewAdapter?.setOnItemClickListener(
                object : GroupListViewAdapter.OnItemClickListener {
                    override fun onItemClick(group: Group) {
                        homeViewModel.getHigherGroup(group)
                        homeViewModel.getItemsByGroupId(group)
                    }
                }
            )
        }

        homeViewModel.itemDataSet.observe(viewLifecycleOwner) {
            val dataSet = homeViewModel.itemDataSet.value
            val listViewAdapter = dataSet?.let { ItemListViewAdapter(it) }
            itemListView.adapter = listViewAdapter
        }

        return binding.root
    }
}