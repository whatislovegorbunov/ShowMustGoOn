package com.example.showmustgoon.domain.model

data class Show(
    val id: String,
    val title: String,
    val description: String,
    val status: ShowStatus
)

enum class ShowStatus { UPCOMING, LIVE, ENDED }
