package com.example.foodgo.ui.screens.staff

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StaffHome() {
    val orders = listOf("Order #1234", "Order #1235", "Order #1236")

    Scaffold(
        topBar = {
            Text(
                "Staff - Order Management",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                StaffOrderCard(order)
            }
        }
    }
}

@Composable
fun StaffOrderCard(orderId: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(orderId, fontWeight = FontWeight.Bold)
            Text("Items: Lentil Curry x2, Burger x1", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { /* TODO */ }) {
                    Text("Prepare")
                }
                OutlinedButton(onClick = { /* TODO */ }) {
                    Text("Ready")
                }
            }
        }
    }
}
