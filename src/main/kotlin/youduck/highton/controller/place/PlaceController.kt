package youduck.highton.controller.place

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.place.dto.PlaceRecommendHttpDto
import youduck.highton.service.place.PlaceService

@RestController
@RequestMapping("/api/v1")
class PlaceController(
    private val placeService: PlaceService,
) {
    @PostMapping("/places/recommend")
    fun getPlaceRecommendations(
        @RequestBody request: PlaceRecommendHttpDto.Request,
    ): List<PlaceRecommendHttpDto.Response> {
        return placeService.getPlaceList(request)
    }
}
