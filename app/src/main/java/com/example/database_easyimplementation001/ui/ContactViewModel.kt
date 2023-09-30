package com.example.database_easyimplementation001.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database_easyimplementation001.data.local.Contact
import com.example.database_easyimplementation001.data.local.ContactDao
import com.example.database_easyimplementation001.data.local.ContactEvent
import com.example.database_easyimplementation001.data.local.ContactState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val dao: ContactDao
) : ViewModel() {

    private val _state = MutableStateFlow(ContactState())
    val state: StateFlow<ContactState> = _state

    init {
        viewModelScope.launch {
            dao.getContacts().collect { contacts ->
                _state.update { it.copy(contacts = contacts) }
            }
        }
    }

    fun onEvent(event: ContactEvent) {
        when (event) {
            ContactEvent.HideDialog -> _state.update { it.copy(isDialogVisible = false) }
            ContactEvent.SaveContact -> saveNewContact()
            ContactEvent.ShowDialog -> _state.update { it.copy(isDialogVisible = true) }
            is ContactEvent.DeleteContact -> viewModelScope.launch { dao.deleteContact(event.contact) }
            is ContactEvent.SetContactNumber -> _state.update { it.copy(phoneNumber = event.number) }
            is ContactEvent.SetFirstName -> _state.update { it.copy(firstName = event.firstName) }
            is ContactEvent.SetLastName -> _state.update { it.copy(lastName = event.lastName) }
        }
    }

    private fun saveNewContact() {
        val firstName = _state.value.firstName
        val lastName = _state.value.lastName
        val phoneNumber = _state.value.phoneNumber
        if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
            return
        }
        val contact = Contact(
            firstName = firstName, lastName = lastName, number = phoneNumber
        )
        viewModelScope.launch {
            dao.upsertContact(contact)
        }
        _state.update {
            it.copy(
                firstName = "", lastName = "", phoneNumber = "", isDialogVisible = false
            )
        }
    }
}

