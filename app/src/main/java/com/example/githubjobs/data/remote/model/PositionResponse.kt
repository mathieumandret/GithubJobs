package com.example.githubjobs.data.remote.model

import com.google.gson.annotations.SerializedName

data class PositionResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("company_url")
    val companyURL: String?,
    @SerializedName("location")
    val location: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("how_to_apply")
    val howToApply: String,
    @SerializedName("company_logo")
    val companyLogoURL: String?
)
