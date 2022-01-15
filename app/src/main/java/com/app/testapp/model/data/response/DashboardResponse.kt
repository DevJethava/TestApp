package com.app.testapp.model.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: DashboardData? = null
)

data class DashboardData(
    @SerializedName("pickupOrders")
    @Expose
    val pickupOrders: PickupOrders,
    @SerializedName("deliveryOrders")
    @Expose
    val deliveryOrders: DeliveryOrders,
    @SerializedName("unassignedOrders")
    @Expose
    val unassignedOrders: UnassignedOrders,
    @SerializedName("toBeProcessed")
    @Expose
    val toBeProcessed: ToBeProcessed
)

data class DeliveryOrders(
    @SerializedName("total")
    @Expose
    val total: Int,
    @SerializedName("completed")
    @Expose
    val completed: Int
)

data class PickupOrders(
    @SerializedName("total")
    @Expose
    val total: Int,
    @SerializedName("completed")
    @Expose
    val completed: Int
)

data class UnassignedOrders(
    @SerializedName("total")
    @Expose
    val total: Int
)

data class ToBeProcessed(
    @SerializedName("total")
    @Expose
    val total: Int
)