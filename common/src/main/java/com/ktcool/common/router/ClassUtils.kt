package com.ktcool.common.router

import android.content.Context
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexFile
import java.lang.reflect.Field
import java.util.*
import kotlin.jvm.Throws

object ClassUtils {
    //经过BaseDexClassLoader反射获取app全部的DexFile
    private fun getDexFiles(context: Context): List<DexFile> {
        val dexFiles: MutableList<DexFile> = ArrayList()
        val loader = context.classLoader as BaseDexClassLoader
        try {
            val pathListField = field("dalvik.system.BaseDexClassLoader", "pathList")
            val list = pathListField[loader]
            val dexElementsField = field("dalvik.system.DexPathList", "dexElements")
            val dexElements = dexElementsField[list] as Array<*>
            val dexFileField = field("dalvik.system.DexPathList\$Element", "dexFile")
            for (dex in dexElements) {
                val dexFile = dexFileField[dex] as DexFile
                dexFiles.add(dexFile)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return dexFiles
    }

    @Throws(ClassNotFoundException::class, NoSuchFieldException::class)
    private fun field(clazz: String, fieldName: String): Field {
        val cls = Class.forName(clazz)
        val field = cls.getDeclaredField(fieldName)
        field.isAccessible = true
        return field
    }

    /**
     * 经过指定包名，扫描包下面包含的全部的ClassName
     *
     * @param context     U know
     * @param packageName 包名
     * @return 全部class的集合
     */
    fun getFileNameByPackageName(context: Context, packageName: String?): Set<String>? {
        val classNames: MutableSet<String> = HashSet()
        val dexFiles = getDexFiles(context)
        for (dexFile in dexFiles) {
            val dexEntries = dexFile.entries()
            while (dexEntries.hasMoreElements()) {
                val className = dexEntries.nextElement()
                if (className.startsWith(packageName!!)) {
                    classNames.add(className)
                }
            }
        }
        return classNames
    }
}