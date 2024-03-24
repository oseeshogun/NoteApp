package com.example.notesapp.features.notes.domain.utils

sealed class OrderType {
    object  Ascending: OrderType()
    object Descending: OrderType()
}
