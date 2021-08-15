package nick.template.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import nick.template.R
import nick.template.databinding.MainFragmentBinding
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nick.template.data.Item
import nick.template.ui.adapters.ItemAdapter

class MainFragment @Inject constructor(
    private val vmFactory: MainViewModel.Factory
) : Fragment(R.layout.main_fragment) {
    private val viewModel: MainViewModel by viewModels { vmFactory.create(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = MainFragmentBinding.bind(view)
        val onDelete: (Item) -> Unit = { viewModel.delete(it) }
        val adapter = ItemAdapter(onDelete)
        binding.recyclerView.adapter = adapter

        binding.add.setOnClickListener {
            viewModel.addItem()
        }

        viewModel.items
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { items -> adapter.submitList(items) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
