package com.sophie.sampleapp.data.entity

import com.sophie.sampleapp.domain.model.Main;

data class MainEntity(private val id: Int) {
    fun toMain() = Main(id)
}
