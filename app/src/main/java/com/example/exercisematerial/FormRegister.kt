package com.example.exercisematerial

import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.copy

data class User(val firstName: String = "", val lastName: String = "")
class ViewUser : ViewModel() {
    var userDate by mutableStateOf(User())

    fun updateFirstName(firstName: String) {
        userDate = userDate.copy(
            firstName = firstName
        )
    }

    fun updateLastName(lastName: String) {
        userDate = userDate.copy(
            lastName = lastName
        )
    }

    fun save() {
     userDate = User()
    }

    fun reset() {
        userDate = userDate.copy(
            firstName = "",
            lastName = ""
        )
    }
}

@Composable
fun RegisterForm() {
    val view: ViewUser = viewModel()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enroll Form",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextField(
                value = view.userDate.firstName,
                onValueChange = { view.updateFirstName(it) },
                label = { Text("First Name") },
                placeholder = { Text("First Name") },
                modifier = Modifier.width(170.dp)
            )
            TextField(
                value = view.userDate.lastName,
                onValueChange = { view.updateLastName(it) },
                label = { Text("Last Name") },
                placeholder = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun CheckBox() {
    val option = listOf("Male", "Female")
    var selectedOption by remember { mutableStateOf(option[0]) }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Gender",
            fontSize = 16.sp
        )
        Row {
            option.forEach { option ->

                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Spacer(modifier = Modifier.size(16.dp))
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(text = option)
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Schools() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("RUPP", "HRD", "AUPP")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

    Box(modifier = Modifier.padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }
    }
}
@Composable
fun FullScreenDialog(user: User, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss, shape = ShapeDefaults.Large) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Black )
                    }
                }
                Text(
                    text = "User Information",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "First Name: ${user.firstName}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Last Name: ${user.lastName}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }
        }
    }
}
@Composable
fun DetailScreen() {
    val save = remember {
        mutableStateOf(false)
    }
    val user : ViewUser = viewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
        onClick = { user.reset() },

        ) {
        Text(text = "Reset")
    }
        Button(
            onClick = { save.value = true },
        ) {
            Text(text = "Save")
        }
        if (save.value) {
            FullScreenDialog(
                user = user.userDate,
                onDismiss = {
                    save.value  = false
                }
            )
        }

    }
}
