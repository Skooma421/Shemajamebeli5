package com.example.shemajamebeli5

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatItem(
    val id: Int,
    @Json(name = "image") val image: String?,
    @Json(name = "owner") val owner: String,
    @Json(name = "last_message") val lastMessage: String,
    @Json(name = "last_active") val lastActive: String,
    @Json(name = "unread_messages") val unreadMessages: Int,
    @Json(name = "is_typing") val isTyping: Boolean,
    @Json(name = "laste_message_type") val lastMessageType: String
) {
    val profileIconResId: Int
        get() = when {
            image.isNullOrBlank() -> R.drawable.ic_launcher_foreground
            else -> R.drawable.img_1
        }
    val personName: String get() = owner
    val time: String get() = lastActive
    val unreadCount: Int get() = unreadMessages
}