package youduck.highton.entity.place

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class Place(
    val name: String,
    val address: String,
    val description: String,
    val longitude: Double,
    val latitude: Double,
) : BaseEntity()
