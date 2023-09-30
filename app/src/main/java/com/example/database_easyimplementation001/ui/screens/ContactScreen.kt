@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.database_easyimplementation001.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.database_easyimplementation001.data.local.Contact
import com.example.database_easyimplementation001.data.local.ContactEvent
import com.example.database_easyimplementation001.data.local.ContactState

@Composable
fun ContactItem(contact: Contact, onEvent: (ContactEvent) -> Unit) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            Text(
                text = "${contact.firstName} ${contact.lastName}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = contact.number,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        IconButton(onClick = { onEvent(ContactEvent.DeleteContact(contact)) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete contact"
            )
        }
    }

}

@Composable
fun ContactScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "My Contacts",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ContactEvent.ShowDialog) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add contact"
                )
            }
        }
    ) { padding ->
        if (state.isDialogVisible) {
            AddContactDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.contacts) {
                Log.d("TAG", "ContactScreen: ${it.firstName}")
                ContactItem(contact = it, onEvent = onEvent)
            }
        }
    }
}


@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier.padding(16.dp),
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        confirmButton = {
            FilledIconButton(onClick = { onEvent(ContactEvent.SaveContact) }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "add contact"
                )

            }
        },
        title = {
            Text(
                text = "Add Contact",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = {
                        onEvent(ContactEvent.SetFirstName(it))
                    },
                    placeholder = { Text(text = "First Name") }
                )
                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(ContactEvent.SetLastName(it))
                    },
                    placeholder = { Text(text = "Last Name") }
                )
                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetContactNumber(it))
                    },
                    placeholder = { Text(text = "Phone Number") }
                )
            }
        },
    )
}