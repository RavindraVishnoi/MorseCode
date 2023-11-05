package com.ca3.morsecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ca3.morsecode.ui.theme.MorseCodeTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorseCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val morse = remember {
                        mutableStateOf("")
                    }
                    val string = remember {
                        mutableStateOf("")
                    }
                    Column (verticalArrangement = Arrangement.SpaceEvenly,modifier=Modifier.fillMaxSize()){
                        Normalbox(modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                            string)
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp) ,
                                horizontalArrangement= Arrangement.SpaceEvenly){
                            Encodebutton(string,morse)
                            Clearbutton(string,morse)
                            Decodebutton(string,morse)
                        }
                        Morsebox(modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                            morse)
                    }

                }
            }
        }
    }
}

@Composable
fun Encodebutton(string: MutableState<String>, morse: MutableState<String>) {
    Button(onClick = {
        morse.value= encode(string.value)
    }) {
        Text(text = "Encode")
    }

}
@Composable
fun Decodebutton(string: MutableState<String>, morse: MutableState<String>){
    Button(onClick = {
        string.value= decode(morse.value)
    }) {
        Text(text = "Decode")
    }
}
@Composable
fun Clearbutton(string: MutableState<String>, morse: MutableState<String>){
    Button(onClick = { string.value="";morse.value=""}
    ) {
    Text(text = "Clear")
}
}
@ExperimentalMaterial3Api
@Composable
fun Morsebox(modifier: Modifier, morse: MutableState<String>){
    TextField(value = morse.value,
        onValueChange = { morse.value = it },
        label = { Text("Morse Code") },
        modifier = modifier,
        trailingIcon = {Icon(Icons.Filled.PlayArrow , contentDescription = null)}
        )
}
@ExperimentalMaterial3Api
@Composable
fun Normalbox(modifier: Modifier, string: MutableState<String>){

    TextField(value = string.value,
        onValueChange = { string.value = it },
        label = { Text("Text") },
        modifier = modifier,
        trailingIcon = {Icon(Icons.Filled.PlayArrow , contentDescription = null)}
    )
}

fun decode(input: String): String {
    val morseWords = input.split(" ")
    val textBuilder = StringBuilder()
    val alphaNumeric = Maps.AlphaNumeric
    val morseCode= Maps.AlphaNumeric1
    for (morseWord in morseWords) {
        val index = morseCode.indexOf(morseWord)
        if (index != -1) {
            textBuilder.append(alphaNumeric[index])
        }
        else{
            return "Invalid Morse Code"
        }
    }
    return textBuilder.toString()
}
fun encode(input: String): String {
    val upperInput = input.uppercase(Locale.ROOT)
    val morseCodeBuilder = StringBuilder()
    val alphaNumeric = Maps.AlphaNumeric
    val morseCode= Maps.AlphaNumeric1
    for (char in upperInput) {
        val index = alphaNumeric.indexOf(char.toString())
        if (index != -1) {
            morseCodeBuilder.append(morseCode[index]).append(" ")
        }
        else{
            return "Invalid Input"
        }
    }
    return morseCodeBuilder.toString().trim()
}