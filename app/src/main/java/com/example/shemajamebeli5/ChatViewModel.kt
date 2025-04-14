package com.example.shemajamebeli5

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _dataFlow = MutableStateFlow<List<ChatItem>>(emptyList())
    val dataFlow: StateFlow<List<ChatItem>> = _dataFlow.asStateFlow()

    fun loadConversations() {
        viewModelScope.launch {
            Log.d("ChatViewModel", "Attempting to parse JSON")
            val parsedDataList = parseConversationsJson(conversations)
            Log.d("ChatViewModel", "Parsed ${parsedDataList.size} items")
            _dataFlow.value = parsedDataList
        }
    }

    private val conversations = """
        [
            {
                "id": 1,
                "image": "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
                "owner": "გრიშა ონიანი",
                "last_message": "თავის ტერიტორიას ბომბავდა",
                "last_active": "4:20 PM",
                "unread_messages": 3,
                "is_typing": false,
                "laste_message_type": "text"
            },
            {
                "id": 2,
                "image": null,
                "owner": "ჯემალ კაკაურიძე",
                "last_message": "შემოგევლე",
                "last_active": "3:00 AM",
                "unread_messages": 0,
                "is_typing": true,
                "laste_message_type": "voice"
            },
            {
                "id": 3,
                "image": "https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg",
                "owner": "გურამ ჯინორია",
                "last_message": "ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა",
                "last_active": "1:00",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "file"
            },
            {
                "id": 4,
                "image": "",
                "owner": "კაკო წენგუაშვილი",
                "last_message": "ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი",
                "last_active": "1:00 PM",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "text"
            }
        ]
    """.trimIndent()

    private fun parseConversationsJson(jsonString: String): List<ChatItem> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val listType = Types.newParameterizedType(List::class.java, ChatItem::class.java)
        val adapter = moshi.adapter<List<ChatItem>>(listType)

        return try {
            Log.d("ChatViewModel", "JSON input: $jsonString")
            adapter.fromJson(jsonString) ?: emptyList<ChatItem>().also {
                Log.e("ChatViewModel", "Parsed JSON is null")
            }
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Failed to parse JSON: ${e.message}", e)
            emptyList<ChatItem>()
        }
    }
}