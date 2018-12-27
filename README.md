*** 简介

采用Kotlin编码(100%兼容Java)， SwipeMenuView 该文件使用方式请--->>><a href="https://github.com/jdsjlzx/LRecyclerView">戳这里</a><<<---<br>
<br>详情请<a href="https://blog.csdn.net/qq_28322987/article/details/81384886">戳这里</a><br>
<br>
支持操作音频，视频，图片，txt，zip，word，excel，ppt，pdf等文件<br> 
支持查看指定文件类型<br> 
支持音频，视频播放，图片查看，zip解压<br> 
支持多选，最大数量限制<br> 
支持实时排序<br>
支持指定文件路径访问<br> 

*** 引入方式

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your module build.gradle at dependencies:
```
dependencies {
    implementation 'com.github.sendtion:FileManager:1.0.0'
}
```

*** 使用方式
- 在Application中，请根据需要自行添加，初始均为为默认值<br><br>
FileManageHelp.getInstance()<br>
                .setFileTypeListener(FileTypeListener()) // 获取文件类型<br>
                .setImgeLoad(MyFileImageListener()) // 图片加载方式（自己实现）<br>
                .setJumpListener(JumpByTypeListener()) // 跳转方式 <br>
                .setFileInfoListener(FileInfoListener()) // 文件详情 <br>
                .setMaxLength(9, "最大选取数量：9") <br>
                .setCanRightTouch(true) // 滑动删除 <br>
                .setShowHiddenFile(false) // 是否显示隐藏文件 <br>
                .setFileFilterArray(arrayOf(PNG, JPG, GIF, MP3, AAC, MP4, _3GP, TXT, ZIP)) // 设置过滤规则<br>
                .setSortordByWhat(FileManageHelp.BY_DEFAULT) // 设置排序方式<br>
                .setSortord(FileManageHelp.ASC) // 升序或降序<br>
                .isShowLog = true // 是否显示日志<br><br>
- 在Activity或Fragment中<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、申明回调 FileManageHelp.getInstance().fileResultListener = { list -> Log.e("选中的文件size：$list.size")}<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、打开文件管理 FileManageHelp.getInstance().start(this) // 默认SD卡根目录<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或FileManageHelp.getInstance().start(this,"指定目录")
<br><br>
- 文件类型拓展 <br>
如果上述类型不能满足，可自定义文件类型！ <b>请注意：以下 " : " 是继承 ，不是 冒号</b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、新建一个类 : FileType，重写里面的openFile()、loadingFile()方法<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、新建一个类 : FileTypeListener，重写里面的getFileType()方法(参考MyFileTypeListener)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、新建一个类 : JumpByTypeListener，自己新建jump()方法(参考MyJumpByTypeListener)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、在Application中<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FileManageHelp.getInstance().setFileTypeListener(FileTypeListener()).setJumpListener(JumpByTypeListener())<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、在openFile()方法中直接调用第3步的方法即可，详情见demo
<br><br>
- 关于自定义<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看 file 工程里面的 drawable,values里面的值，并在主工程目录下的相同位置 保持命名一致即可替换 颜色，图片，&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选中样式，或者自己修改file工程里面的样式
<br><br>
最后不要忘了权限<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /\><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /\><br><br>

*** 部分截图如下

主界面<br><br>
<img src = "file/src/main/assets/file_manager_main.png" width = 300px><br><br>
排序方式<br><br>
<img src = "file/src/main/assets/file_manager_sort.png" width = 300px><br><br>
选择文件<br><br>
<img src = "file/src/main/assets/file_manager_selected.png" width = 300px><br><br>
音频播放<br><br>
<img src = "file/src/main/assets/file_manager_music.png" width = 300px></br><br>
视频播放<br><br>
<img src = "file/src/main/assets/file_manager_video.png" width = 300px></br><br>


 
