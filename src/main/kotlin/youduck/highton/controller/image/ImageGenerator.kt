package youduck.highton.controller.image

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.util.image.IMAGE
import youduck.highton.util.image.ImageSearchResponse

@RestController
@RequestMapping("/api/v1")
class ImageGenerator(private val image: IMAGE) {
    @GetMapping("/image/search/{topic}")
    fun searchImageByTopic(
        @PathVariable topic: String,
    ): ImageSearchResponse {
        return image.searchImages(topic)
    }
}
