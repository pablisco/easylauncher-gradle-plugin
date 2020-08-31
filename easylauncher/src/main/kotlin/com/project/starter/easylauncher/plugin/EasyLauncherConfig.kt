package com.project.starter.easylauncher.plugin

import com.project.starter.easylauncher.filter.ColorRibbonFilter
import com.project.starter.easylauncher.filter.EasyLauncherFilter
import com.project.starter.easylauncher.filter.OverlayFilter
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Nested
import java.awt.Color
import java.io.File
import java.io.Serializable
import javax.inject.Inject

@Suppress("TooManyFunctions", "MagicNumber")
open class EasyLauncherConfig @Inject constructor(
    val name: String,
    objectFactory: ObjectFactory
) : Serializable {

    val enabled = objectFactory.property(Boolean::class.java).apply {
        set(true)
    }

    @Nested
    internal val filters = objectFactory.setProperty(EasyLauncherFilter::class.java).apply {
        set(emptyList())
    }

    fun enable(enabled: Boolean) {
        this.enabled.value(enabled)
    }

    fun setFilters(filters: Iterable<EasyLauncherFilter>) {
        this.filters.addAll(filters)
    }

    fun setFilters(filter: EasyLauncherFilter) {
        this.filters.value(this.filters.get() + filter)
    }

    fun filters(vararg filters: EasyLauncherFilter) {
        this.filters.value(this.filters.get() + filters)
    }

    @JvmOverloads
    @Deprecated("use customRibbon method instead")
    fun customColorRibbonFilter(
        name: String? = null,
        ribbonColor: String?,
        labelColor: String = "#FFFFFF",
        position: String = "topleft",
        textSizeRatio: Float? = null
    ): ColorRibbonFilter {
        return ColorRibbonFilter(
            name ?: this.name,
            Color.decode(ribbonColor),
            Color.decode(labelColor),
            ColorRibbonFilter.Gravity.valueOf(position.toUpperCase()),
            textSizeRatio
        )
    }

    fun customRibbon(properties: Map<String, Any>): ColorRibbonFilter {
        val ribbonText = properties["label"]?.toString() ?: name
        val background = properties["ribbonColor"]?.toString()?.let { Color.decode(it) }
        val labelColor = properties["labelColor"]?.toString()?.let { Color.decode(it) }
        val position = properties["position"]?.toString()?.toUpperCase()?.let { ColorRibbonFilter.Gravity.valueOf(it) }
        val textSizeRatio = properties["textSizeRatio"]?.toString()?.toFloatOrNull()

        return ColorRibbonFilter(
            label = ribbonText,
            ribbonColor = background,
            labelColor = labelColor,
            gravity = position,
            textSizeRatio = textSizeRatio
        )
    }

    @JvmOverloads
    fun grayRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0x60, 0x60, 0x60, 0x99))
    }

    @JvmOverloads
    fun greenRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0, 0x72, 0, 0x99))
    }

    @JvmOverloads
    fun orangeRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0xff, 0x76, 0, 0x99))
    }

    @JvmOverloads
    fun yellowRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0xff, 251, 0, 0x99))
    }

    @JvmOverloads
    fun redRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0xff, 0, 0, 0x99))
    }

    @JvmOverloads
    fun blueRibbonFilter(name: String? = null): ColorRibbonFilter {
        return ColorRibbonFilter(name ?: this.name, Color(0, 0, 255, 0x99))
    }

    fun overlayFilter(fgFile: File): OverlayFilter {
        return OverlayFilter(fgFile)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
