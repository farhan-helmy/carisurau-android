package com.example.carisurau.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SurauPhoto(
    @SerialName("file_path")
    val filePath: String?
)

@Serializable
data class Surau(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "unique_name")
    val uniqueName: String,
    @SerialName(value = "is_approved")
    val isApproved: Boolean,
    @SerialName(value = "created_at")
    val createdAt: String?,
    @SerialName(value = "is_approved_at")
    val isApprovedAt: String?,
    @SerialName(value = "state")
    val state: String?,
    @SerialName(value = "district")
    val district: String?,
    @SerialName(value = "mall")
    val mall: String?,
    @SerialName(value = "brief_direction")
    val briefDirection: String?,
    @SerialName("surau_photos")
    val surauPhotos: List<SurauPhoto>?
)