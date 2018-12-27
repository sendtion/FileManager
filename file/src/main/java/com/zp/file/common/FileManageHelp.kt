package com.zp.file.common

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.support.v4.util.ArrayMap
import android.view.View
import com.zp.file.R
import com.zp.file.content.*
import com.zp.file.listener.*
import com.zp.file.type.FileType
import com.zp.file.type.FileTypeManage
import com.zp.file.ui.FileManageActivity
import com.zp.file.util.FileManageUtil
import org.jetbrains.annotations.NotNull
import java.io.File


class FileManageHelp : FileManage {

    private object Builder {
        val MANAGER = FileManageHelp()
    }

    companion object {

        fun getInstance() = Builder.MANAGER

        /** 默认 */
        const val BY_DEFAULT = 0x1000
        /** 根据名字 */
        const val BY_NAME = 0x1001
        /** 根据最后修改时间 */
        const val BY_DATE = 0x1003
        /** 根据大小 */
        const val BY_SIZE = 0x1004

        /** 升序 */
        const val ASC = 0x2001
        /** 降序 */
        const val DESC = 0x2002
    }

    /** 高阶函数回调，哪个Activity中调用，就在哪个Activity中使用 */
    var fileResultListener: ((ArrayList<FileBean>?) -> Unit)? = null

    /**
     * 设置文件类型监听
     */
    private var fileTypeListener: FileTypeListener? = FileTypeListener()
    fun getFileTypeListener() = fileTypeListener
    fun setFileTypeListener(fileTypeListener: FileTypeListener): FileManageHelp {
        this.fileTypeListener = fileTypeListener
        return this
    }

    /**
     * 设置文件解压监听
     */
    private var fileZipListener: FileZipListener? = FileZipListener()
    fun setFileZipListener(fileZipListener: FileZipListener): FileManageHelp {
        this.fileZipListener = fileZipListener
        return this
    }

    /**
     * 文件详情
     */
    private var fileInfoListener: FileInfoListener? = FileInfoListener()
    fun getFileInfoListener() = fileInfoListener
    fun setFileInfoListener(fileInfoListener: FileInfoListener): FileManageHelp {
        this.fileInfoListener = fileInfoListener
        return this
    }

    /**
     * 设置图片的加载方式
     */
    private var imageLoadeListener: FileImageListener? = FileImageListener()
    fun getImageLoad() = imageLoadeListener
    fun setImgeLoad(imageLoadeListener: FileImageListener) : FileManageHelp {
        this.imageLoadeListener = imageLoadeListener
        return this
    }

    /**
     * 设置跳转方式
     */
    private var jumpListener: JumpByTypeListener? = JumpByTypeListener()
    fun getJumpListener() = jumpListener
    fun setJumpListener(jumpListener: JumpByTypeListener): FileManageHelp {
        this.jumpListener = jumpListener
        return this
    }


    /** 设置最大选取数量，-1表示不限制 */
    private var maxLength = -1
    var maxLengthHintStr = ""
    fun getMaxLength() = maxLength
    fun setMaxLength(maxLength: Int, maxLengthHintStr: String = "最多可选取${maxLength}个文件"): FileManageHelp {
        if (maxLength <= 0) throw IllegalArgumentException("maxLength 必须大于 0")
        this.maxLength = maxLength
        this.maxLengthHintStr = maxLengthHintStr
        return this
    }

    /** 设置标题返回按钮图标 */
    private var titleBackIcon = R.drawable.file_back
    fun getTitleBackIcon() = titleBackIcon
    fun setTitleBackIcon(resId: Int): FileManageHelp {
        this.titleBackIcon = resId
        return this
    }

    /** 设置标题北京颜色 */
    private var titleBackgroundColor = R.color.title_background_color
    fun getTitleBackgroundColor() = titleBackgroundColor
    fun setTitleBackgroundColor(resId: Int): FileManageHelp {
        this.titleBackgroundColor = resId
        return this
    }

    /** 设置标题文字 */
    private var titleText = "文件管理"
    fun getTitleText() = titleText
    fun setTitleText(title: String): FileManageHelp {
        this.titleText = title
        return this
    }

    /** 设置标题文字大小和颜色 */
    private var titleTextAppearance = R.style.ToolbarTitleTextStyle
    fun getTitleTextAppearance() = titleTextAppearance
    fun setTitleTextAppearance(resId: Int): FileManageHelp {
        this.titleTextAppearance = resId
        return this
    }

    /** 设置标题更多按钮是否显示 */
    private var isShowTitleMore = false
    fun getShowTitleMore() = isShowTitleMore
    fun setShowTitleMore(isShowTitleMore: Boolean): FileManageHelp {
        this.isShowTitleMore = isShowTitleMore
        return this
    }

    /** 设置是否可以滑动 */
    private var canRightTouch = false
    fun getCanRightTouch() = canRightTouch
    fun setCanRightTouch(canRightTouch: Boolean): FileManageHelp {
        this.canRightTouch = canRightTouch
        return this
    }

    /**
     * 是否显示隐藏文件
     */
    private var isShowHiddenFile = false
    fun getShowHiddenFile() = isShowHiddenFile
    fun setShowHiddenFile(@NotNull isShowHiddenFile: Boolean): FileManageHelp {
        this.isShowHiddenFile = isShowHiddenFile
        return this
    }

    /**
     * 设置过滤规则
     */
    private var fileFilterArray: Array<String>? = null
    fun getFileFilterArray() = fileFilterArray
    fun setFileFilterArray(fileFilterArray: Array<String>): FileManageHelp {
        this.fileFilterArray = fileFilterArray
        return this
    }

    /**
     * 设置排序的依据
     */
    private var sortordByWhat = BY_NAME
    fun getSortordByWhat() = sortordByWhat
    fun setSortordByWhat(@NotNull sortordByWhat: Int): FileManageHelp {
        if (sortordByWhat != BY_DEFAULT && sortordByWhat != BY_NAME && sortordByWhat != BY_DATE && sortordByWhat != BY_SIZE)
            throw IllegalArgumentException("sortordByWhat error")
        this.sortordByWhat = sortordByWhat
        return this
    }

    /**
     * 设置排序的方式
     */
    private var sortord = ASC
    fun getSortord() = sortord
    fun setSortord(@NotNull sortord: Int): FileManageHelp {
        if (sortord != ASC && sortord != DESC)
            throw IllegalArgumentException("sortord error")
        this.sortord = sortord
        return this
    }

    /** 是否显示log文件 */
    var isShowLog = true

    /**
     * 跳转至文件管理页面
     * @param path 指定的文件路径
     */
    fun start(context: Context, path: String? = null) {
        context.jumpActivity(FileManageActivity::class.java,
                if (path == null) null else ArrayMap<String,Any>().apply { put("filePath", path) })
    }

    /**
     * 打开文件
     */
    override fun openFile(fileType: FileType?, filePath: String, view: View, context: Context) {
        FileTypeManage.getInstance().openFile(filePath, view, context)
    }

    /**
     * 删除文件
     */
    override fun deleteFile(context: Context, filePath: String, deleteListener: (Boolean) -> Unit) {
        CommonDialog(context).showDialog2({
            deleteListener.invoke(File(filePath).delete())
        }, {}, "你确定要删除吗？", "删除", "取消")
    }

    /**
     * 复制文件
     */
    override fun copyFile(filePath: String, outPath: String, context: Context) {
        callFileByType(filePath, outPath, context, COPY_TYPE)
    }

    /**
     * 剪切文件
     */
    override fun cutFile(filePath: String, outPath: String, context: Context) {
        callFileByType(filePath, outPath, context, CUT_TYPE)
    }

    /**
     * 解压文件
     */
    override fun zipFile(filePath: String, outZipPath: String, context: Context) {
        callFileByType(filePath, outZipPath, context, ZIP_TYPE)
    }

    /**
     * 查看文件详情
     */
    override fun infoFile(fileType: FileType?, bean: FileBean, context: Context) {
        FileTypeManage.getInstance().infoFile(bean, context)
    }

    private fun callFileByType(filePath: String, outPath: String, context: Context, type: Int) {
        val msg = when (type) {
            COPY_TYPE -> "复制"
            CUT_TYPE -> "剪切"
            else -> "解压"
        }
        val activity = context as Activity
        val dialog = ProgressDialog(activity).run {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage("${msg}中，请稍后...")
            setCancelable(false)
            show()
            this
        }
        Thread({
            val isSuccess = when (type) {
                COPY_TYPE -> FileManageUtil.getInstance().copyFile(filePath, outPath)
                CUT_TYPE -> FileManageUtil.getInstance().cutFile(filePath, outPath)
                else -> fileZipListener?.zipFile(filePath, outPath, context) ?: false
            }
            activity.runOnUiThread ({
                dialog.dismiss()
                activity.toast(if (isSuccess) "${msg}成功" else "${msg}失败")
            })
        }).start()
    }

}