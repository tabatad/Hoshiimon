package com.tabata.hoshiimon.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tabata.hoshiimon.database.AppDatabase
import com.tabata.hoshiimon.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    lateinit var binding: FragmentItemBinding
    lateinit var itemViewModel: ItemViewModel

    private val args: ItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).dao
        val viewModelFactory = ItemViewModelFactory(dao, application)
        itemViewModel = ViewModelProvider(
            this, viewModelFactory
        )[ItemViewModel::class.java]
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        itemViewModel.getItemByItemId(args.itemId)

        itemViewModel.item.observe(viewLifecycleOwner) {
            binding.itemName.text = it.itemName
            binding.itemPrice.text = it.price.toString()
        }

        return binding.root
    }
}