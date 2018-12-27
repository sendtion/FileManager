package com.zp.file.content

import java.io.Serializable

data class FileBean(
        var fileName: String,
        var isFile: Boolean, // true---文件；false---文件夹
        var filePath: String,
        var date: String,
        var originalDate: String, // 原始时间
        var size: String,
        var originaSize: Long // 原始大小
) : Serializable {
    constructor() : this("", false, "", "", "", "", 0L)
}

data class FileInfoBean(
        var duration: String,
        var width: String,
        var height: String
)

data class FilePathBean(
        var fileName: String, // 路径名称
        var filePath: String // 文件路径
)



 