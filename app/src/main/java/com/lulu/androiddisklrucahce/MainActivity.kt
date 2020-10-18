package com.lulu.androiddisklrucahce

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_main.*
import libcore.io.DiskLruCache
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var mCache: DiskLruCache
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1. 缓存地址 2. 版本号  3. 指定同一个key可以对应多少个缓存文件 4. 指定最多可以缓存多少字节的数据
        mCache = DiskLruCache.open(getDiskCacheDir(this, "lruCache"), 101, 1, 10 * 1024 * 1024)
        btRead.setOnClickListener {
            val key = etInputKey.text.toString()
            val snapshot = mCache.get(key)
            if (snapshot != null) {
                val inputStream = snapshot.getInputStream(0)
                tvContent.text = "$key ：${String(inputStream.readBytes())}"
                snapshot.close()
            } else {
                tvContent.text = "$key ：数据为空！"
            }
        }
        btWrite.setOnClickListener {
            val value = etInputValue.text.toString()
            val key = etInputKey.text.toString()
            val editor  = mCache.edit(key)
            val ops = editor.newOutputStream(0)
            ops.write(value.toByteArray())
            ops.flush()
            // editor.abort() 忽略提交
            editor.commit()
            mCache.flush()
        }

    }

    private fun getDiskCacheDir(context: Context, fileName: String): File {
        val cachePath  = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir?.path
        } else {
            context.cacheDir.path
        }
        return File(cachePath + File.separator + fileName)
    }


}
