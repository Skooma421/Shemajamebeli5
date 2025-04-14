package com.example.shemajamebeli5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shemajamebeli5.databinding.ChatItemBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private var chatList: List<ChatItem> = emptyList()

    class ChatViewHolder(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = chatList[position]

        with(holder.binding) {
            if (!chatItem.image.isNullOrEmpty()) {
                Glide.with(root.context)
                    .load(chatItem.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(chatPhotoIcon)
            } else {
                chatPhotoIcon.setImageResource(R.drawable.ic_launcher_foreground)
            }
            chatPersonName.text = chatItem.personName
            textMessages.text = chatItem.lastMessage
            textTime.text = chatItem.time
            textAmount.text = chatItem.unreadCount.toString()
            textAmount.visibility = if (chatItem.unreadCount > 0) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = chatList.size

    fun updateChats(newChatList: List<ChatItem>) {
        val diffCallback = ChatDiffCallback(chatList, newChatList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        chatList = newChatList
        diffResult.dispatchUpdatesTo(this)
    }

    private class ChatDiffCallback(
        private val oldList: List<ChatItem>,
        private val newList: List<ChatItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}