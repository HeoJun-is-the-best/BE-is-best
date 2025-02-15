package youduck.highton.repository.place

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.place.Place

interface PlaceRepository : JpaRepository<Place, Long>
