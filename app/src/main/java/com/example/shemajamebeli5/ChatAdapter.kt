package com.example.shemajamebeli5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shemajamebeli5.databinding.ChatItemBinding

class ChatAdapter(private val chatList: List<ChatItem>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
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
                    .load(ChatItem.image)
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

}