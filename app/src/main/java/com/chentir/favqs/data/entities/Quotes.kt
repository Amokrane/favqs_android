package com.chentir.favqs.data.entities

import com.google.gson.annotations.SerializedName

data class Quotes(
    @SerializedName("page") val page: Int,
    @SerializedName("last_page") val lastPage: Boolean,
    @SerializedName("quotes") val quoteEntities: List<QuoteEntity>
)