package com.andrewjapar.compiler

import com.andrewjapar.annotations.BindLayout
import com.andrewjapar.annotations.BindViewHolder
import com.andrewjapar.compiler.generators.AdapterGenerator
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

@Suppress("SpellCheckingInspection")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(RedapterProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class RedapterProcessor : AbstractProcessor() {

    private lateinit var messager: Messager
    private lateinit var filer: Filer

    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        messager = processingEnvironment.messager
        filer = processingEnvironment.filer
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        BindViewHolder::class.java.canonicalName,
        BindLayout::class.java.canonicalName
    )

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(
        set: MutableSet<out TypeElement>,
        roundEnvironment: RoundEnvironment
    ): Boolean {

        val viewHolderLayoutMap = getViewHolderLayout(roundEnvironment)

        val annotatedAdapter = getAnnotatedAdapter(roundEnvironment, viewHolderLayoutMap)

        AdapterGenerator(annotatedAdapter, filer).generate()

        return true
    }

    private fun getViewHolderLayout(roundEnvironment: RoundEnvironment): Map<ClassName, Pair<Int, ClassName>> {
        return roundEnvironment.getElementsAnnotatedWith(BindLayout::class.java)
            .associate {
                val className = getClassName(it)
                val annotatedElement = it.getAnnotation(BindLayout::class.java)
                val layoutRes = annotatedElement.layout
                val model = getClassName(annotatedElement)
                Pair(className, Pair(layoutRes, model))
            }
    }

    private fun getAnnotatedAdapter(
        roundEnvironment: RoundEnvironment,
        layoutMap: Map<ClassName, Pair<Int, ClassName>>
    ): Map<ClassName, List<ViewHolderInfo>> {
        return roundEnvironment.getElementsAnnotatedWith(BindViewHolder::class.java)
            .associate { element ->
                when (element.kind) {
                    ElementKind.FIELD -> getAdapterField(element, layoutMap)
                    else -> getAdapterClass(element, layoutMap)
                }
            }
    }

    private fun getAdapterClass(
        element: Element,
        layoutMap: Map<ClassName, Pair<Int, ClassName>>
    ): Pair<ClassName, List<ViewHolderInfo>> {
        val adapterClassName = getClassName(element.asType())
        val viewHolders =
            getBindViewHolders(element.getAnnotation(BindViewHolder::class.java), layoutMap)

        return Pair(adapterClassName, viewHolders)
    }

    private fun getAdapterField(
        element: Element,
        layoutMap: Map<ClassName, Pair<Int, ClassName>>
    ): Pair<ClassName, List<ViewHolderInfo>> {
        val declaringClassName = getClassName(element.enclosingElement.asType())
        val fieldClassName =
            ClassName(
                declaringClassName.packageName,
                "${declaringClassName.simpleName}_${element.simpleName}"
            )
        val viewHolders =
            getBindViewHolders(element.getAnnotation(BindViewHolder::class.java), layoutMap)

        return Pair(fieldClassName, viewHolders)
    }

    private fun getBindViewHolders(
        annotationValue: BindViewHolder,
        layoutMap: Map<ClassName, Pair<Int, ClassName>>
    ): List<ViewHolderInfo> {
        return try {
            annotationValue.viewHolders.map { it.asClassName() }
        } catch (e: MirroredTypesException) {
            e.typeMirrors.map { getClassName(it) }
        }
            .filter { layoutMap[it] != null }
            .map { viewHolderClassName ->
                if (layoutMap[viewHolderClassName] == null) error(ERROR_NO_LAYOUT_BIND)

                ViewHolderInfo(
                    viewHolderClassName,
                    layoutMap.getValue(viewHolderClassName).first,
                    layoutMap.getValue(viewHolderClassName).second
                )
            }
    }

    private fun getClassName(annotationValue: BindLayout): ClassName {
        return try {
            annotationValue.model.asClassName()
        } catch (e: MirroredTypeException) {
            getClassName(e.typeMirror)
        }
    }

    private fun getClassName(typeMirror: TypeMirror): ClassName {
        val rawString = typeMirror.toString()
        val dotPosition = rawString.lastIndexOf(".")
        val packageName = rawString.substring(0, dotPosition)
        val className = rawString.substring(dotPosition + 1)
        return ClassName(packageName, className)
    }

    private fun getClassName(annotatedElement: Element): ClassName {
        val typeElement = annotatedElement as TypeElement
        val simpleName = typeElement.simpleName.toString()
        return ClassName(
            typeElement.qualifiedName.toString().replace(".$simpleName", ""),
            simpleName
        )
    }

    data class ViewHolderInfo(
        val className: ClassName,
        val layoutResId: Int,
        val modelClass: ClassName
    )

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        private const val ERROR_NO_LAYOUT_BIND = "There is no layout bind in this view Holder"
    }
}