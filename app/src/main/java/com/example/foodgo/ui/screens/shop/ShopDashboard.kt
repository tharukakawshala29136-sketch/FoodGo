package com.example.foodgo.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDashboard(shopName: String?, onLogout: () -> Unit) {

    var currentTab by remember { mutableStateOf(ShopTab.Home) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    // Shop info state
    var name by remember { mutableStateOf(shopName ?: "FoodGo Shop") }
    var username by remember { mutableStateOf("foodgo_official") }
    var contact by remember { mutableStateOf("+94 123 456 789") }

    if (showSettingsDialog) {
        ShopSettingsDialog(
            currentName = name,
            currentUsername = username,
            currentContact = contact,
            onDismiss = { showSettingsDialog = false },
            onLogout = onLogout,
            onSave = { newName, newUsername, newContact ->
                name = newName
                username = newUsername
                contact = newContact
                showSettingsDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFF356859),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Logo
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.White, RoundedCornerShape(32.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Storefront,
                                contentDescription = null,
                                tint = Color(0xFF356859),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "@$username",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = contact,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }

                        IconButton(
                            onClick = { showSettingsDialog = true },
                            modifier = Modifier.align(Alignment.Top)
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar {
                ShopTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(tab.title)
                        }
                    )
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (currentTab) {
                ShopTab.Home -> ShopHomeContent(name, username, contact)
                ShopTab.Staff -> ManageStaffContent()
                ShopTab.FoodItems -> ManageFoodItemsContent()
                ShopTab.Sales -> ViewSalesContent()
                ShopTab.Orders -> ViewOrdersContent()
            }
        }
    }
}

enum class ShopTab(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Home("Home", Icons.Default.Home),
    Staff("Staff", Icons.Default.People),
    FoodItems("Food", Icons.Default.RestaurantMenu),
    Sales("Sales", Icons.Default.BarChart),
    Orders("Orders", Icons.Default.ListAlt)
}

enum class StaffRole { Staff, DeliveryRider }


@Composable
fun ShopHomeContent(name: String, username: String, contact: String) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Dashboard Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardCard(
                    "Orders",
                    "245",
                    Modifier.weight(1f)
                )

                DashboardCard(
                    "Revenue",
                    "Rs 85K",
                    Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardCard(
                    "Foods",
                    "52",
                    Modifier.weight(1f)
                )

                DashboardCard(
                    "Staff",
                    "12",
                    Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Text(
                "Recent Orders",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        items(
            listOf(
                Order("ORD-1001", "Completed"),
                Order("ORD-1002", "Preparing"),
                Order("ORD-1003", "Pending"),
                Order("ORD-1004", "Completed")
            )
        ) {
            RecentOrderItem(it)
        }
    }
}
