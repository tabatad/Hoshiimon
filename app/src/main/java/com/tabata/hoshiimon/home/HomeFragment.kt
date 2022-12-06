package com.tabata.hoshiimon.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tabata.hoshiimon.database.AppDatabase
import com.tabata.hoshiimon.database.Group
import com.tabata.hoshiimon.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel

    init {
        if (Timber.treeCount != 0) {
            Timber.plant(Timber.DebugTree())
        }
    }

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

        // グループリストの設定
        val groupListView = binding.groupListView
        groupListView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.groupDataSet.observe(viewLifecycleOwner) {
            val dataSet = homeViewModel.groupDataSet.value
            val listViewAdapter = dataSet?.let { GroupListViewAdapter(it) }
            groupListView.adapter = listViewAdapter
            listViewAdapter?.setOnItemClickListener(
                object : GroupListViewAdapter.OnItemClickListener {
                    override fun onItemClick(group: Group) {
                        homeViewModel.setCurrentGroup(group)
                    }
                }
            )
        }

        // アイテムリストの設定
        val itemListView = binding.itemListView
        itemListView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.itemDataSet.observe(viewLifecycleOwner) {
            val dataSet = homeViewModel.itemDataSet.value
            val listViewAdapter = dataSet?.let { ItemListViewAdapter(it) }
            itemListView.adapter = listViewAdapter
        }

        homeViewModel.currentGroup.observe(viewLifecycleOwner) {
            homeViewModel.getLowerGroup(it)
            homeViewModel.getItemsByGroupId(it)

            binding.groupName.text = it.groupName
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return binding.root
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!homeViewModel.isRoot()) {
                homeViewModel.getHigherGroup()
            } else {
                AlertDialog.Builder(context)
                    .setMessage("アプリを終了しますか")
                    .setPositiveButton("Yes") { _, _, ->
                        requireActivity().finish()
                    }
                    .setNegativeButton("No") { _, _ -> }
                    .show()
            }
        }
    }
}