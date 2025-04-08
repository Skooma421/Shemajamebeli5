package com.example.shemajamebeli5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shemajamebeli5.databinding.FragmentChatBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStreamReader

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatList = parseJsonFromAssets(requireContext())

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatAdapter(chatList)
        }
    }

    private fun parseJsonFromAssets(context: Context): List<ChatItem> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val type = Types.newParameterizedType(List::class.java, ChatItem::class.java)
        val adapter = moshi.adapter<List<ChatItem>>(type)

        val inputStream = context.assets.open("conversations.json")
        val reader = InputStreamReader(inputStream)
        return adapter.fromJson(reader)!!.also {
            reader.close()
            inputStream.close()
        }
    }
}