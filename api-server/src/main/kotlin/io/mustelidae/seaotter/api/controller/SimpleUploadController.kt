package io.mustelidae.seaotter.api.controller

import io.mustelidae.seaotter.api.resources.UploadResources
import io.mustelidae.seaotter.common.Replies
import io.mustelidae.seaotter.domain.delivery.Image
import io.mustelidae.seaotter.domain.delivery.PureDelivery
import io.mustelidae.seaotter.utils.toReplies
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["Image Uploader"])
@RestController
@RequestMapping("/upload/simple")
class SimpleUploadController
@Autowired constructor(
    private val pureDelivery: PureDelivery
) {

    @ApiOperation("upload by multipart")
    @PostMapping(
        "multipart",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun upload(
        @RequestPart(required = true) multiPartFile: MultipartFile,
        @RequestParam(required = false) hasOriginal: Boolean?
    ): Replies<UploadResources.ReplyOnImage> {

        val image = Image.from(multiPartFile).apply {
            randomizeName()
        }

        val shippingItem = pureDelivery.delivery(image, hasOriginal ?: false)

        return shippingItem.shippedImages
            .map { UploadResources.ReplyOnImage.from(it) }
            .toReplies()
    }
}