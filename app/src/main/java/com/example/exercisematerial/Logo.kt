package com.example.exercisematerial

import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Logo() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(id = R.string.app_name),
        modifier = Modifier
            .width(40.dp)
            .padding(5.dp)
    )
}

data class ChosenFlag(val name: String = "", val painter: Painter? = null)

class FlagViewModel : ViewModel() {
    var flagData = mutableStateOf(ChosenFlag())

    fun updateFlag(name: String, painter: Painter?) {
        flagData.value = ChosenFlag(name, painter)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample() {
    val view: FlagViewModel = viewModel()
    var flags = listOf(
        ChosenFlag("Cambodia", painterResource(id = R.drawable.cambodai)),
        ChosenFlag("china", painterResource(id = R.drawable.china)),
        ChosenFlag("english", painterResource(id = R.drawable.english))
    )

    var selectedFlag by remember { mutableStateOf(flags[0]) }
    var showBottomSheet by remember { mutableStateOf(false) }
    TextButton(
        onClick = { showBottomSheet = true },
        modifier = Modifier.padding(5.dp)

    ) {
        view.flagData.value.painter?.let { painter ->
            Image(
                painter = painter,
                contentDescription = view.flagData.value.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
            )
            Text(text = selectedFlag.name.take(3), fontSize = 10.sp)
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(modifier = Modifier.padding(16.dp)) {
                flags.forEach { flag ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(bottom = 10.dp),
                    ) {
                        RadioButton(
                            selected = (flag == selectedFlag),
                            onClick = {
                                selectedFlag = flag
                                view.updateFlag(flag.name, flag.painter)
                            }
                        )
                        Text(flag.name)
                    }
                }
            }
        }
    }
}

