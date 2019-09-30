package com.andrewjapar.compiler.generators

import javax.annotation.processing.Filer

class FactoryGenerator(
    private val filer: Filer
) {

    fun generate(fileName: String, packageName: String) {
        val finalFileName = "Generated_$fileName"
        val fileContent = KotlinClassBuilder(finalFileName, packageName).getContent()

    }

    /**
     * This is the directory from which kapt will compile all the files together with main sources.
     */
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    /**
     * Katanya for learning purpose, kita bikin class builder sendiri.
     * Katanya sih bisa pake macem KotlinPoet buat ini (cari dewek itu apaan)
     */
    inner class KotlinClassBuilder(fileName: String, packageName: String) {
        private val contentTemplate = """
            package $packageName
            
            class $fileName {
                fun mintaAngka() = 1
            }
        """.trimIndent()

        fun getContent(): String {
            return contentTemplate
        }
    }
}