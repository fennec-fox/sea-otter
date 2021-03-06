package io.mustelidae.seaotter.domain.uploader

import com.google.common.io.Files
import io.mustelidae.seaotter.config.AppEnvironment
import io.mustelidae.seaotter.domain.delivery.Image
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

internal class LocalStorageUploader(
    private val localStorage: AppEnvironment.LocalStorage
) : Uploader {

    override fun upload(image: Image): String {
        val directoryPath: DirectoryPath = newPath(image.isOriginal)

        val out = ByteArrayOutputStream()
        ImageIO.write(image.bufferedImage, image.getExtension(), out)

        val path = File(directoryPath.getPath())
        if (path.exists().not())
            path.mkdirs()

        directoryPath.append(image.name, image.imageFileFormat)
        val file = File(directoryPath.getPath())

        file.createNewFile()
        @Suppress("UnstableApiUsage")
        Files.write(out.toByteArray(), file)

        return directoryPath.getPath()
    }

    private fun newPath(isOriginal: Boolean): DirectoryPath {
        return DirectoryPath(localStorage.path, localStorage.shardType).apply {
            append(isOriginal)
        }
    }

    companion object {
        fun makeUrl(localStorage: AppEnvironment.LocalStorage, uploadedPath: String): URL {
            val url = localStorage.url + uploadedPath.removePrefix(localStorage.path)
            return URL(url)
        }
    }
}
