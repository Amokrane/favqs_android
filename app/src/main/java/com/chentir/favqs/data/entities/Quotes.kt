package com.chentir.favqs.data.entities

import com.google.gson.annotations.SerializedName

data class Quotes(@SerializedName("quotes") val quotes: List<QuoteEntity>)