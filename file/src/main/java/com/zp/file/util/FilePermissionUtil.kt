package com.zp.file.util

import android.Manifest
import android.support.v4.app.ActivityCompat
import android.os.Build
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

object FilePermissionUtil {

    /** 读写SD卡权限  */
    const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val WRITE_EXTERNAL_CODE = 0x1001

    /**
     * 判断是否申请过权限
     * @param permissions   权限
     * @return true表示没有申请过
     */
    fun hasPermission(context: Context, vararg permissions: String) =
            permissions.any { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }

    /**
     * 判断是否申请过权限
     * @param permissions  权限
     * @return  返回未授权的数组
     */
    fun checkPermission(context: Context, vararg permissions: String): Array<String> {
        val list = ArrayList<String>()
        permissions.filterTo(list) { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
        return list.toTypedArray()
    }

    /**
     * 请求权限
     * @param code  请求码
     * @param requestPermission 权限
     */
    fun requestPermission(a: Activity, code: Int, vararg requestPermission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(a, requestPermission, code)
        }
    }
}