package com.example.shemajamebeli5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shemajamebeli5.databinding.FragmentChatBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private var fullChatList: List<ChatItem> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatAdapter()
        }

        setupSearchBar()
        viewModel.loadConversations()
        observeChatList()//testing
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                filterChats(query)
            }
        })
    }

    private fun filterChats(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullChatList
        } else {
            fullChatList.filter { chatItem ->
                chatItem.owner.contains(query, ignoreCase = true)
            }
        }
        (binding.recyclerView.adapter as ChatAdapter).updateChats(filteredList)
    }

    private fun observeChatList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataFlow.collectLatest { chatList ->
                fullChatList = chatList
                filterChats(binding.searchBar.text.toString().trim())
            }
        }
    }
}