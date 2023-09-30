package com.example.database_easyimplementation001.data.local


data class ContactState (
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isDialogVisible: Boolean = false,

)