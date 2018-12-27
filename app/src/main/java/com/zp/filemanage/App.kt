package com.zp.filemanage

import android.app.Application
import com.zp.file.common.FileManageHelp
import com.zp.file.listener.FileInfoListener
import com.zp.file.listener.FileZipListener
import com.zp.filemanage.custom.MyFileTypeListener
import com.zp.filemanage.custom.MyJumpListener

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FileManageHelp.getInstance()
                .setFileTypeListener(MyFileTypeListener())
                .setImgeLoad(MyFileImageListener())
                .setJumpListener(MyJumpListener())
                .setFileZipListener(FileZipListener())
                .setFileInfoListener(FileInfoListener())
                .setMaxLength(3, "最大选取数量：3")
                .setCanRightTouch(true)
                .setShowHiddenFile(false)
                //.setFileFilterArray(arrayOf(PNG, JPG, GIF, MP3, AAC, MP4, _3GP, TXT, ZIP))
                .setFileFilterArray(arrayOf("png", "jpg", "gif", "txt", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"))
                .setSortordByWhat(FileManageHelp.BY_NAME)
                .setSortord(FileManageHelp.ASC)
                .setShowTitleMore(true)
                .setTitleBackIcon(R.drawable.ic_arrow_back_black_24dp)
                .setTitleBackgroundColor(R.color.title_background_color)
                .setTitleText("选择文件")
                .setTitleTextAppearance(R.style.ToolbarTitleTextStyle)
                .isShowLog = true
    }

}