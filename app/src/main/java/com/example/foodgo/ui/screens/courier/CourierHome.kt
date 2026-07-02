package com.example.foodgo.ui.screens.courier


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
fun CourierHome() {
    val deliveries = listOf("Delivery #1234", "Delivery #1237")

    Scaffold(
        topBar = {
            Text(
                "Courier - Assigned Deliveries",
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
            items(deliveries) { delivery ->
                CourierDeliveryCard(delivery)
            }
        }
    }
}

@Composable
fun CourierDeliveryCard(deliveryId: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(deliveryId, fontWeight = FontWeight.Bold)
            Text("Address: Sadia Villa, Habiganj", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { /* TODO */ }) {
                    Text("Pick Up")
                }
                OutlinedButton(onClick = { /* TODO */ }) {
                    Text("Delivered")
                }
            }
        }
    }
}
