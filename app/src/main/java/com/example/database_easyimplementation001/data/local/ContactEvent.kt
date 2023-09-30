package com.example.database_easyimplementation001.data.local

sealed interface ContactEvent{
    object SaveContact: ContactEvent
    object ShowDialog: ContactEvent
    object HideDialog: ContactEvent
    data class SetFirstName(val firstName: String): ContactEvent
    data class SetLastName(val lastName: String): ContactEvent
    data class SetContactNumber(val number: String): ContactEvent
    data class DeleteContact(val contact: Contact): ContactEvent
}