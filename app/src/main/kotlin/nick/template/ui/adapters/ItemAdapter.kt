package nick.template.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nick.template.data.Item
import nick.template.databinding.ItemBinding

class ItemAdapter(
    private val onDelete: (Item) -> Unit
) : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return LayoutInflater.from(parent.context)
            .let { inflater -> ItemBinding.inflate(inflater, parent, false) }
            .let { binding -> ItemViewHolder(binding, onDelete) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

class ItemViewHolder(
    private val binding: ItemBinding,
    private val onDelete: (Item) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        binding.message.text = item.message
        binding.delete.setOnClickListener {
            onDelete(item)
        }
    }
}
