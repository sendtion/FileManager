package com.zp.file.listener

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.zp.file.R
import com.zp.file.content.*
import com.zp.file.type.*
import com.zp.file.ui.*
import com.zp.file.util.FileManageUtil
import com.zp.file.util.FileOpenUtil

interface TelActivityListener {
    fun telActivity(oldPath: String, outPath: String?, type: Int)
}

/**
 * 文件类型
 */
open class FileTypeListener {

    open fun getFileType(filePath: String): FileType {
        val typeStr = filePath.run {
            substring(lastIndexOf(".") + 1, length)
        }
        return when (typeStr.toLowerCase()) {
            PNG, JPG, GIF -> ImageType()
            MP3, AAC, WAV -> AudioType()
            MP4, _3GP -> VideoType()
            TXT, XML, JSON -> TxtType()
            ZIP -> ZipType()
            DOC -> DocType()
            XLS -> XlsType()
            PPT -> PptType()
            PDF -> PdfType()
            else -> OtherType()
        }
    }
}


/**
 * 图片加载
 */
open class FileImageListener {
    open fun loadImage(imageView: ImageView, path: String) = Unit
}



/**
 * 跳转
 */
open class JumpByTypeListener {

    open fun jumpAudio(filePath: String, view: View, context: Context) {
        (context as AppCompatActivity).apply {
            checkFragmentByTag(AUDIO_DIALOG_TAG)
            AudioPlayDialog.getInstance(filePath).apply {
                show(supportFragmentManager, AUDIO_DIALOG_TAG)
            }
        }
    }

    open fun jumpImage(filePath: String, view: View, context: Context) {
        context.startActivity(Intent(context, PicActivity::class.java).apply {
            putExtra("picFilePath", filePath)
        }, ActivityOptions.makeSceneTransitionAnimation(context as Activity, view,
                context.getStringById(R.string.sharedElement_pic)).toBundle())
    }

    open fun jumpVideo(filePath: String, view: View, context: Context) {
        context.startActivity(Intent(context, VideoPlayActivity::class.java).apply {
            putExtra("videoFilePath", filePath)
        }, ActivityOptions.makeSceneTransitionAnimation(context as Activity, view,
                context.getStringById(R.string.sharedElement_video)).toBundle())
    }

    open fun jumpTxt(filePath: String, view: View, context: Context) {
        log("jumpTxt")
        FileOpenUtil.openTXT(filePath, view, context)
    }

    open fun jumpZip(filePath: String, view: View, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("请选择")
            setItems(arrayOf("打开", "解压"), { dialog, which ->
                dialog.dismiss()
                if (which == 0) {
                    FileOpenUtil.openZIP(filePath, view, context)
                } else {
                    (context as FileManageActivity).apply {
                        val activity = this
                        checkFragmentByTag(FOLDER_DIALOG_TAG)
                        FolderDialog.newInstance(filePath, ZIP_TYPE).apply {
                            telActivityListener = activity
                            show(supportFragmentManager, FOLDER_DIALOG_TAG)
                        }
                    }
                }
            })
            setPositiveButton("取消", { dialog, _ -> dialog.dismiss() })
            show()
        }
    }

    open fun jumpDoc(filePath: String, view: View, context: Context) {
        FileOpenUtil.openDOC(filePath, view, context)
    }

    open fun jumpXls(filePath: String, view: View, context: Context) {
        FileOpenUtil.openXLS(filePath, view, context)
    }

    open fun jumpPpt(filePath: String, view: View, context: Context) {
        FileOpenUtil.openPPT(filePath, view, context)
    }

    open fun jumpPdf(filePath: String, view: View, context: Context) {
        FileOpenUtil.openPDF(filePath, view, context)
    }

    open fun jumpOther(filePath: String, view: View, context: Context) {
        log("jumpOther")
        context.toast("暂不支持预览该文件")
    }
}

/**
 * 文件解压
 */
open class FileZipListener {

    /**
     * @return Boolean true --->>> 成功
     */
    open fun zipFile(filePath: String, outZipPath: String, context: Context): Boolean {
        return FileManageUtil.getInstance().extractFile(filePath, outZipPath)
    }
}

/**
 * 文件详情
 */
open class FileInfoListener {
    open fun fileInfo(bean: FileBean, context: Context) {
        (context as AppCompatActivity).apply {
            checkFragmentByTag(INFO_DIALOG_TAG)
            InfoDialog.newInstance(bean).apply {
                show(supportFragmentManager, INFO_DIALOG_TAG)
            }
        }
    }
}


