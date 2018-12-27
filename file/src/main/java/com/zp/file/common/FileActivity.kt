package com.zp.file.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zp.file.listener.TelActivityListener

abstract class FileActivity : AppCompatActivity(), TelActivityListener {

    protected var manage: FileManage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        manage = getFileManage()
        init(savedInstanceState)
    }

    abstract fun getContentView(): Int
    abstract fun getFileManage(): FileManage?
    abstract fun init(savedInstanceState: Bundle?)

    override fun telActivity(oldPath: String, outPath: String?, type: Int) = Unit
}