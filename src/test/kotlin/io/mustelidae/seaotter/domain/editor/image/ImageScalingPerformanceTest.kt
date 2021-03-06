package io.mustelidae.seaotter.domain.editor.image

import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage

class ImageScalingPerformanceTest : ImagePerformanceTemplate() {
    override fun crop(bufferedImage: BufferedImage, x1: Int, y1: Int, x2: Int, y2: Int): BufferedImage {
        val flabbyImage = ImageScalingFlabbyImage(bufferedImage)
        flabbyImage.crop(x1, y1, x2, y2)
        return flabbyImage.getBufferedImage()
    }

    override fun crop(bufferedImage: BufferedImage, width: Int, height: Int): BufferedImage {
        val flabbyImage = ImageScalingFlabbyImage(bufferedImage)
        flabbyImage.crop(width, height)
        return flabbyImage.getBufferedImage()
    }

    override fun resize(bufferedImage: BufferedImage, width: Int, height: Int): BufferedImage {
        val flabbyImage = ImageScalingFlabbyImage(bufferedImage)
        flabbyImage.resize(width, height)
        return flabbyImage.getBufferedImage()
    }

    override fun resize(bufferedImage: BufferedImage, scale: Double): BufferedImage {
        val flabbyImage = ImageScalingFlabbyImage(bufferedImage)
        flabbyImage.resize(scale)

        return flabbyImage.getBufferedImage()
    }

    override fun getProcessorName(): String = "imageScaling"

    @Test
    fun imageScalingTest() {
        allImageAreScaledUsingProportions()
    }

    @Test
    fun imageFixFrameTest() {
        allImageAreFrameUsingProportions()
    }

    @Test
    fun imageCropTest() {
        cropAllImagesToFixedSize()
    }

    @Test
    fun imageCropTestByCoordinate() {
        cropAllImagesToCoordinate()
    }
}
