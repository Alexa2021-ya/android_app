package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var text by remember { mutableStateOf("") }
            val context = LocalContext.current

            // Функция для проверки формата номера телефона
            fun isValidPhoneNumber(phone: String): Boolean {
                val cleanPhone = phone.replace(Regex("[\\s\\-()]"), "")
                return when {
                    cleanPhone.matches(Regex("7\\d{10}")) -> true
                    cleanPhone.matches(Regex("\\+7\\d{10}")) -> true
                    cleanPhone.matches(Regex("8\\d{10}")) -> true
                    else -> false
                }
            }

            fun cleanPhoneNumber(phone: String): String {
                return phone.replace(Regex("[^\\d+]"), "")
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        label = { Text("") },
                        placeholder = { Text("") },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))  // <- 32dp отступ

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(context, SecondActivity::class.java).apply {
                                    putExtra("EXTRA_TEXT", text)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text("Открыть вторую Activity")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (isValidPhoneNumber(text)) {
                                    val cleanNumber = cleanPhoneNumber(text)
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:$cleanNumber")
                                    }
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Введите корректный номер телефона",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            },
                            enabled = text.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text("Позвонить другу")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (text.isNotEmpty()) {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, text)
                                        type = "text/plain"
                                    }
                                    val chooser = Intent.createChooser(sendIntent, "Поделиться через")
                                    context.startActivity(chooser)
                                }
                            },
                            enabled = text.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text("Поделиться текстом")
                        }
                    }
                }
            }
        }
    }
}