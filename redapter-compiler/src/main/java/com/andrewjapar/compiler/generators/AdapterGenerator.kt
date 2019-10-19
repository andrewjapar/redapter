package com.andrewjapar.compiler.generators

import com.andrewjapar.compiler.RedapterProcessor.ViewHolderInfo
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer

@Suppress("SpellCheckingInspection")
class AdapterGenerator(
    private val annotatedAdapter: Map<ClassName, List<ViewHolderInfo>>,
    private val filer: Filer
) {

    fun generate() {
        annotatedAdapter.forEach { (className, viewHolders) ->

            val packageName = className.packageName
            val generatedAdapterName = generateAdapterName(className)

            val adapterClass = generateClassBuilder(generatedAdapterName).apply {
                addFunction(generateOnCreateViewHolder(viewHolders))
                addFunction(generateGetItemViewType(viewHolders))
            }.build()

            val kotlinFile = FileSpec.builder(packageName, generatedAdapterName)
                .addType(adapterClass)
                .build()

            kotlinFile.writeTo(filer)
        }
    }

    private fun generateClassBuilder(name: String) =
        TypeSpec.classBuilder(name)
            .superclass(ADAPTER)
            .addModifiers(KModifier.OPEN)
            .addModifiers(KModifier.PUBLIC)

    private fun generateOnCreateViewHolder(infoList: List<ViewHolderInfo>): FunSpec {
        val methodSpec = FunSpec.builder("onCreateViewHolder")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(ParameterSpec.builder("parent", VIEW_GROUP).build())
            .addParameter(ParameterSpec.builder("viewType", INT).build())
            .returns(VIEW_HOLDER)
            .beginControlFlow("return when (viewType)")

        infoList.forEach { info ->
            methodSpec.generateViewHolderBlock(info)
        }
        methodSpec.addCode("else -> throw RuntimeException(\"Not support type\" + viewType)\n")
        methodSpec.endControlFlow()

        return methodSpec.build()
    }

    private fun generateGetItemViewType(infoList: List<ViewHolderInfo>): FunSpec {
        val methodSpec = FunSpec.builder("getItemViewType")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(ParameterSpec.builder("position", INT).build())
            .returns(INT)
            .beginControlFlow("return when (data[position])")

        infoList.forEach { info ->
            methodSpec.generateViewTypeBlock(info)
        }
        methodSpec.addCode("else -> throw RuntimeException(\"Not support type\")\n")
        methodSpec.endControlFlow()

        return methodSpec.build()
    }

    private fun FunSpec.Builder.generateViewHolderBlock(info: ViewHolderInfo) {
        val viewType = info.layoutResId
        beginControlFlow("$viewType ->")
            .addCode(
                "%T(%T.from(parent.getContext()).inflate(%L, parent, false))\n",
                info.className,
                LAYOUT_INFLATER,
                info.layoutResId
            )
            .endControlFlow()
    }

    private fun FunSpec.Builder.generateViewTypeBlock(info: ViewHolderInfo) {
        val modelClass = info.modelClass
        beginControlFlow("is $modelClass ->")
            .addCode(
                "%L\n",
                info.layoutResId
            )
            .endControlFlow()
    }

    private fun generateAdapterName(className: ClassName) =
        className.simpleName + "_Helper"

    companion object {
        private val VIEW_GROUP: ClassName = ClassName("android.view", "ViewGroup")
        private val LAYOUT_INFLATER: ClassName = ClassName("android.view", "LayoutInflater")
        private val ADAPTER: ClassName =
            ClassName("com.andrewjapar.redapter", "Redapter", "Adapter")
        private val VIEW_HOLDER: ClassName =
            ClassName("com.andrewjapar.redapter", "Redapter", "ViewHolder")
    }
}