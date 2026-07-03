package com.example.foodgo.ui.screens.shop

import androidx.compose.foundation.BorderStroke
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


@Composable
fun DashboardCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(130.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF356859),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class Order(
    val id: String,
    val status: String
)

@Composable
fun RecentOrderItem(order: Order) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    order.id,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    order.status
                )
            }

            AssistChip(
                onClick = {},
                label = {
                    Text(order.status)
                }
            )
        }
    }
}

data class StaffMember(val name: String, val role: StaffRole)


@Composable
fun RecentOrderItem(order: Order) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    order.id,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    order.status
                )
            }

            AssistChip(
                onClick = {},
                label = {
                    Text(order.status)
                }
            )
        }
    }
}

data class StaffMember(val name: String, val role: StaffRole)

@Composable
fun StaffActionDialog(
    title: String,
    initialName: String = "",
    initialEmail: String = "",
    initialRole: StaffRole = StaffRole.Staff,
    isEdit: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (String, String, String, StaffRole, Boolean) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(initialRole) }
    var isEnabled by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(if (isEdit) "New Password (Optional)" else "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("User Type:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StaffTypeButton(
                        label = "Staff",
                        selected = role == StaffRole.Staff,
                        onClick = { role = StaffRole.Staff },
                        modifier = Modifier.weight(1f)
                    )
                    StaffTypeButton(
                        label = "Delivery Rider",
                        selected = role == StaffRole.DeliveryRider,
                        onClick = { role = StaffRole.DeliveryRider },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (isEdit) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = if (isEnabled) "Status: Active" else "Status: Inactive")
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = { isEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF356859))
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, email, password, role, isEnabled) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF356859))
            ) {
                Text(if (isEdit) "Update" else "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}
@Composable
fun StaffTypeButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color(0xFF356859).copy(alpha = 0.1f) else Color.Transparent,
            contentColor = if (selected) Color(0xFF356859) else Color.Gray
        ),
        border = BorderStroke(
            1.dp,
            if (selected) Color(0xFF356859) else Color.LightGray
        )
    ) {
        Text(label, fontSize = 12.sp)
    }
}
@Composable
fun ManageStaffContent() {
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedStaff by remember { mutableStateOf("") }

    val staffList = listOf(
        StaffMember("Kasun", StaffRole.Staff),
        StaffMember("Nimal", StaffRole.DeliveryRider),
        StaffMember("John", StaffRole.Staff),
        StaffMember("Akila", StaffRole.DeliveryRider),
        StaffMember("Sahan", StaffRole.Staff)
    )

    if (showAddDialog) {
        StaffActionDialog(
            title = "Add New Staff",
            onDismiss = { showAddDialog = false },
            onSave = { name, email, pass, role, enabled ->
                showAddDialog = false
            }
        )
    }

    if (showEditDialog) {
        StaffActionDialog(
            title = "Edit Staff Details",
            initialName = selectedStaff,
            initialEmail = "${selectedStaff.lowercase()}@foodgo.com",
            isEdit = true,
            onDismiss = { showEditDialog = false },
            onSave = { name, email, pass, role, enabled ->
                showEditDialog = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Manage Staff",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF356859)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Staff", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(staffList) { staff ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            staff.name,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (staff.role == StaffRole.Staff) "Staff Member" else "Delivery Rider",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    IconButton(onClick = {
                        selectedStaff = staff.name
                        showEditDialog = true
                    }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Staff",
                            tint = Color(0xFF356859)
                        )
                    }
                }
            }
        }
    }
}