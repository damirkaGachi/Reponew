package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random
data class Product(
    val id: Int,
    val name: String,
    val price: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingCartScreen()
        }
    }
    @Composable
    fun ShoppingCartScreen() {
        var products by remember {
            mutableStateOf(
                listOf(
                    Product(0, "Товар #1", 100),
                    Product(1, "Товар #2", 150),
                    Product(2, "Товар #3", 56)
                )
            )
        }
        val totalSum = products.sumOf { it.price }
        val productSize = products.size
        val context = LocalContext.current
        if (totalSum>500) {
            Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            products.forEach { product ->
                Text(
                    text = "${product.name} - ${product.price} рублей",
                    modifier = Modifier.padding(vertical = 5.dp)

                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Товаров на сумму: $totalSum рублей")

            AddProduct(
                onAdd = {
                    products = products + Product(
                        id = products.size,
                        name = "Товар #${products.size + 1}",
                        price = Random.nextInt(1, 100)
                    )
                }
            )
            RemoveProduct(
                productSize = productSize,
                onRemove = {
                    products = products.dropLast(1)
                }
            )
        }
    }

    @Composable
    fun AddProduct(
        onAdd: () -> Unit
    ) {
        Button(
            onClick = onAdd,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(text = "Добавить товар")
        }
    }

    @Composable
    fun RemoveProduct(
        productSize: Int,
        onRemove: () -> Unit
    ) {
        if (productSize > 0) {
            Button(
                onClick = onRemove,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(text = "Удалить товар")
            }
        }
    }
}
