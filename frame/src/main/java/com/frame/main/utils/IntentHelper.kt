package com.frame.main.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Log
import com.frame.main.constant.Constants.IntentValue.INTENT_KEY
import java.io.Serializable
import java.lang.reflect.Array


object IntentHelper {

    fun sendToActivity(
        context: Context?,
        clazz: Class<*>,
        flag: Int = -1,
        requestCode: Int = -1
    ): IntentBuilder {
        return IntentBuilder(context, clazz, flag, requestCode)
    }

    fun sendToActivity(
        context: Context?,
        clzzName: String,
        flag: Int = -1,
        requestCode: Int = -1
    ): IntentBuilder {
        return IntentBuilder(context, clzzName, flag, requestCode)
    }


    /**
     * 设置bundle
     */
    fun <T> get(bundle: Bundle?, key: String, value: T): T {
        if (bundle == null) {
            return value
        }
        if (value is Int) {
            return bundle?.getInt(key, value) as T
        } else if (value is String) {
            return bundle?.getString(key, value) as T
        } else if (value is Serializable) {
            return bundle?.getSerializable(key) as T
        } else if (value is ArrayList<*>) {
            if (value != null && value.size > 0) {
                if (value.get(0) is String) {
                    return bundle?.getStringArrayList(key) as T
                } else if (value.get(0) is Int) {
                    return bundle?.getIntegerArrayList(key) as T
                }
            }
        } else if (value is IntArray) {
            return bundle?.getIntArray(key) as T
        } else if (value is Array) {
            try {
                if (value != null) {
                    return bundle?.getStringArray(key) as T
                }
            } catch (e: Exception) {
                Log.e("日志", "转换错误:" + e.localizedMessage)
                return value
            }
        } else if (value is Long) {
            return bundle?.getLong(key, value) as T
        } else if (value is LongArray) {
            return bundle?.getLongArray(key) as T
        } else if (value is Byte) {
            return bundle?.getByte(key, value) as T
        } else if (value is ByteArray) {
            return bundle?.getByteArray(key) as T
        } else if (value is IBinder) {
            return bundle?.getBinder(key) as T
        } else if (value is Char) {
            return bundle?.getChar(key, value) as T
        } else if (value is CharArray) {
            return bundle?.getCharArray(key) as T
        } else if (value is Short) {
            return bundle?.getShort(key, value) as T
        } else if (value is ShortArray) {
            return bundle?.getShortArray(key) as T
        }
        return value
    }


    class IntentBuilder {
        private var sendToActivity: Intent? = null
        private var bundle: Bundle? = null
        private var context: Context? = null
        private var mclazzName: String? = null
        private var mFlag: Int = -1
        private var mClazz: Class<*>? = null
        private var mRequestCode: Int = -1

        constructor(context: Context?, clazzName: String, flag: Int = -1, requestCode: Int) {
            this.mclazzName = clazzName
            this.mFlag = flag
            this.context = context
            this.mRequestCode = requestCode
            startConfigIntent()
        }

        constructor(context: Context?, clazz: Class<*>, flag: Int = -1, requestCode: Int) {
            this.mClazz = clazz
            this.mFlag = flag
            this.context = context
            this.mRequestCode = requestCode
            startConfigIntent()
        }

        private fun startConfigIntent() {
            this.context = context
            if (this.context == null) {
                this.context = Applications.context()
            }
            if (mClazz == null) {
                sendToActivity = Intent()
                mclazzName?.let {
                    sendToActivity!!.setClassName(this.context!!, it)
                }
            } else {
                sendToActivity = Intent(context, mClazz)
            }
            if (mFlag != -1) {
                sendToActivity?.flags = mFlag
                if (this.context == Applications.context()) {
                    sendToActivity?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }
            bundle = Bundle()
            sendToActivity?.putExtra(INTENT_KEY, bundle)
        }


        /**
         * 设置bundle
         */
        fun put(key: String, value: Any): IntentBuilder {
            if (value is Int) {
                bundle?.putInt(key, value)
            } else if (value is String) {
                bundle?.putString(key, value)
            } else if (value is Serializable) {
                bundle?.putSerializable(key, value)
            } else if (value is Parcelable) {
                bundle?.putParcelable(key, value)
            } else if (value is ArrayList<*>) {
                if (value != null && value.size > 0) {
                    if (value.get(0) is String) {
                        bundle?.putStringArrayList(key, value as ArrayList<String>)
                    } else if (value.get(0) is Int) {
                        bundle?.putIntegerArrayList(key, value as ArrayList<Int>)
                    }
                }
            } else if (value is IntArray) {
                bundle?.putIntArray(key, value)
            } else if (value is Array) {
                try {
                    if (value != null) {
                        bundle?.putStringArray(key, value as kotlin.Array<out String>);
                    }
                } catch (e: Exception) {
                    Log.e("日志", "转换错误:" + e.localizedMessage)
                }
            } else if (value is Long) {
                bundle?.putLong(key, value)
            } else if (value is LongArray) {
                bundle?.putLongArray(key, value)
            } else if (value is Byte) {
                bundle?.putByte(key, value)
            } else if (value is ByteArray) {
                bundle?.putByteArray(key, value)
            } else if (value is IBinder) {
                bundle?.putBinder(key, value)
            } else if (value is Char) {
                bundle?.putChar(key, value)
            } else if (value is CharArray) {
                bundle?.putCharArray(key, value)
            } else if (value is Short) {
                bundle?.putShort(key, value)
            } else if (value is ShortArray) {
                bundle?.putShortArray(key, value)
            }
            return this
        }

        /**
         * 执行跳转
         */
        fun go() {
            if (mRequestCode != -1) {
                if (context is Activity) {
                    (context as Activity).startActivityForResult(sendToActivity, mRequestCode)
                }
            } else {
                context?.startActivity(sendToActivity)
            }
        }
    }
}