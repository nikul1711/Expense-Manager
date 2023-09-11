package com.example.mvvmroomdbandkotlincoroutines.utils

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import java.lang.reflect.Field
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MyUtils {

    companion object {
        @JvmStatic
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

        fun isValidEmail(email: String): Boolean {
            return EMAIL_REGEX.toRegex().matches(email)
        }

        fun isValidPassword(password: String): Boolean {
            if (password.length < 8) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                    .firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                    .firstOrNull() == null) return false
            if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false
            return true
        }

        fun showMassage(applicationContext: Context, msg: String) {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        }

        const val NOTE_TABLE = "notes"

        const val DATE_FORMAT = "M/d/yyyy"
        const val DATE_TIME_FORMAT = "M/d/yyyy hh:mm aa"
        const val DATE_TIME_SEC_FORMAT = "M/d/yyyy hh:mm:ss aa"
        const val DATE_TIME_12_FORMAT = "M/d/yyyy hh:mm aa"
        const val TIME_FORMAT = "hh:mm aa"
        const val TIME_SEC_FORMAT = "hh:mm:ss aa"
        const val DAY_TIME_FORMAT = "E, M/d/yyyy hh:mm a"
        const val TIME_STAMP_FORMATE = "EEEE, MMM dd, yyyy HH:mm:ss a"
        const val MONTH_DATE_FORMAT = "MMdd"
        const val REPORT_DATE_FORMAT = "EEE, MMM dd, YYYY hh:mm aa"


        fun isValidMobileNo(str: String): Boolean {
            //(0/91): number starts with (0/91)
            //[7-9]: starting of the number may contain a digit between 0 to 9
            //[0-9]: then contains digits 0 to 9
            val ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}")
            //the matcher() method creates a matcher that will match the given input against this pattern
            val match = ptrn.matcher(str)
            return match.find() && match.group() == str
        }

        private fun getDefaultDateTime(date: Date?): String? {
            if (date == null) {
                return ""
            }
            val dateFormat: DateFormat = SimpleDateFormat(DATE_TIME_FORMAT)
            return dateFormat.format(date)
        }

        fun getDefaultDate(date: Date?): String? {
            if (date == null) {
                return ""
            }
            val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT)
            return dateFormat.format(date)
        }

        fun getDateFromString(dateStr: String?): Date? {
            if (dateStr == null || dateStr.isEmpty()) {
                return Date()
            }
            val format = SimpleDateFormat(DATE_FORMAT)
            try {
                return format.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun getRandomString(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..30)
                .map { allowedChars.random() }
                .joinToString("")
        }


        fun getString(data: Any?): String? {
            return data?.toString() ?: ""
        }

        private var DEFAULT_EMPTY_STRING = ""

        fun getString(`val`: String?): String? {
            return `val` ?: DEFAULT_EMPTY_STRING
        }

        fun getString(`val`: Int?): String? {
            return `val`?.toString() ?: "0"
        }

        fun getString(`val`: Double?): String? {
            return getString(`val`, 2)
        }

        fun getString(`val`: Double?, decimal: Int): String? {
            return if (`val` == null) {
                "0.0"
            } else String.format("%." + decimal + "f", MyUtils.getDouble3(`val`))
        }

        fun getString3(`val`: Double?): String? {
            return if (`val` == null) {
                "0.000"
            } else String.format("%.3f", MyUtils.getDouble3(`val`))
        }

        fun getDouble3(field: String?): Double? {
            var field = field
            if (field != null && !field.equals("null", ignoreCase = true) && !field.isEmpty()) {
                field = field.replace("£", "")
                if (field != null) {
                    return Math.round(field.toDouble() * 1000).toDouble() / 1000
                }
            }
            return 0.0
        }

        fun getDouble3(value: Double?): Double? {
            return if (value == null) {
                0.0
            } else Math.round(value * 1000).toDouble() / 1000
        }

        fun getString(`val`: Float?): String? {
            return `val`?.toString() ?: MyUtils.DEFAULT_EMPTY_STRING
        }

        fun getString(lvalue: Long?): String? {
            return lvalue?.toString() ?: ""
        }

        fun getString(`val`: Boolean?): String? {
            return if (`val` == null) {
                "No"
            } else if (`val`) {
                "Yes"
            } else {
                "No"
            }
        }

        fun getBoolean(data: Any?): Boolean? {
            return if (data == null) {
                false
            } else java.lang.Boolean.valueOf(MyUtils.getString(data))
        }

        fun getBoolean(value: Boolean): Boolean {
            return value
        }

        fun getBoolean(value: String?): Boolean {
            if (value == null) {
                return false
            } else if (value.equals("true", ignoreCase = true) || value.equals(
                    "yes",
                    ignoreCase = true
                )
            ) {
                return true
            } else if (value.equals("false", ignoreCase = true) || value.equals(
                    "no",
                    ignoreCase = true
                )
            ) {
                return false
            }
            return false
        }


        fun getDrawable(context: Context, resId: Int): Drawable? {
            try {
                return context.resources.getDrawable(resId)
            } catch (e: Exception) {
            }
            return null
        }

        fun getInteger(field: Double?): Int? {
            return field?.toInt() ?: 0
        }

        fun getInteger(field: String?): Int? {
            try {
                if (field != null && !field.isEmpty()) {
                    return if (field.contains("£")) {
                        getDouble(field).toInt()
                    } else {
                        field.toInt()
                    }
                }
            } catch (exception: Exception) {
            }
            return 0
        }

        fun getInteger(value: Long?): Int? {
            return value?.toInt() ?: 0
        }

        fun getInteger(integerValue: Int?): Int? {
            return integerValue ?: 0
        }

        fun getInteger(value: Any?): Int {
            return getInteger(value, 0)
        }

        fun getInteger(value: Any?, defaultVal: Int): Int {
            if (value == null) {
                return defaultVal
            }
            val str = getString(value)
            if (str!!.isEmpty()) {
                return defaultVal
            }
            return if (str.contains(".")) {
                str.substring(0, str.lastIndexOf(".")).toInt()
            } else str.toInt()
        }

        fun getDimension(context: Context, resId: Int): Float {
            return context.resources.getDimension(resId)
        }

        fun getDimensionInt(context: Context, resId: Int): Int {
            return getInteger(context.resources.getDimension(resId))
        }

        fun getInteger(value: Float?): Int {
            return value?.toInt() ?: 0
        }

        fun getDouble(field: String?): Double {
            return getDouble(field, 2)
        }

        fun getDouble(field: String?, decimals: Int): Double {
            var field = field
            if (field != null && !field.isEmpty()) {
                field = field.replace("£", "")
                return getRoundOff(field.toDouble(), decimals)
            }
            return 0.0
        }

        fun getDouble(field: Int?): Double? {
            return getDouble(field, 2)
        }

        fun getDouble(field: Int?, decimal: Int): Double? {
            return if (field != null) {
                getRoundOff(field.toString().toDouble(), decimal)
            } else 0.0
        }

        fun getDouble(value: Double?): Double {
            return getDouble(value, 2)
        }

        fun getDouble(value: Double?, decimal: Int): Double {
            return value?.let { getRoundOff(it, decimal) } ?: 0.0
        }

        fun getRoundOff(value: Double?): Double {
            return getRoundOff(value, 2)
        }

        fun getRoundOff(value: Double?, dec: Int): Double {
            // return 0.01 * (Math.round(getDouble(value) / 0.01));
            return if (value != null) {
                Math.round(value * Math.pow(10.0, dec.toDouble())).toDouble() / Math.pow(
                    10.0,
                    dec.toDouble()
                )
            } else 0.0
        }

        fun isNumeric(string: String?): Boolean {
            val intValue: Int
            if (string == null || string == "") {
                return false
            }
            try {
                intValue = string.toInt()
                return true
            } catch (e: NumberFormatException) {
            }
            return false
        }

        fun setFocus(editText: EditText) {
            val handler = Handler()
            handler.post { editText.requestFocus() }
        }

        fun getDecimal(`val`: Double?): Double? {
            return if (`val` == null) {
                0.0
            } else getDouble(`val` - `val`.toInt())
        }

        fun getIntString(`val`: Double?): String? {
            return if (`val` == null) {
                "0"
            } else getString(`val`.toInt())
        }

        fun getIntAmount(`val`: String?): Int? {
            return if (`val` == null || `val`.trim { it <= ' ' }
                    .isEmpty() || `val`.trim { it <= ' ' }
                    .contains("Infinity") || `val`.trim { it <= ' ' }
                    .contains("NaN")) {
                0
            } else `val`.replace("£", "").replace(".", "").toInt()
        }

        fun getCurrencyString(doubl: Double?): String? {
            return getCurrencyString(doubl, "£")
        }

        fun getCurrencyString(integer: Int): String? {
            return getCurrencyString(getDouble(integer.toString() + ""), "£")
        }

        fun getCurrencyString(doubl: Double?, curr: String): String? {
            var doubl = doubl
            if (doubl == null) {
                doubl = 0.0
            }
            doubl = getDouble(doubl)
            val format = String.format("%.2f", doubl)
            return if (doubl < 0) {
                "-" + curr + format.replace("-", "")
            } else curr + format
        }


        fun getCurrencyString(doubl: Double?, round: Int): String? {
            var doubl = doubl
            if (doubl == null) {
                doubl = 0.0
            }
            val format = String.format("%." + round + "f", doubl)
            return if (doubl < 0) {
                "-" + "£" + format.replace("-", "")
            } else "$$format"
        }

        fun getDay(date: Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar[Calendar.DATE]
        }


        fun getYear(date: Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar[Calendar.YEAR]
        }

        fun getHour(date: Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar[Calendar.HOUR]
        }

        fun getMinute(date: Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar[Calendar.MINUTE]
        }


        fun getFormattedTime(date: Date?): String? {
            if (date == null) {
                return null
            }
            val format = SimpleDateFormat(TIME_FORMAT)
            try {
                return format.format(date)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun getTimeFromHourAndMinute(hour: Int, minute: Int): String? {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR] = hour
            calendar[Calendar.MINUTE] = minute
            return MyUtils.getFormattedTime(calendar.time)
        }

        fun getColor(context: Context, resId: Int): Int {
            return context.resources.getColor(resId)
        }

        fun setFromTimeTo0Hour(date: Date?): Date? {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar.time
        }

        fun setToTimeTo24Hour(date: Date?): Date? {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.SECOND] = 59
            calendar[Calendar.MILLISECOND] = 0
            return calendar.time
        }

        fun between(checkDate: Date?, dateStart: Date?, dateEnd: Date?): Boolean {
            return if (checkDate != null && dateStart != null && dateEnd != null) {
                dateStart.time <= checkDate.time && checkDate.time <= dateEnd.time
            } else false
        }

        fun getDateFromTime(calenderDate: Date?, time: String): Date? {
            var date: Date? = null
            val format2: DateFormat = SimpleDateFormat("dd-MM-yyyy-hh:mm aa")
            try {

                //
                date = format2.parse(getDate(calenderDate) + "-" + time)
            } catch (e: ParseException) {
            } catch (e: NullPointerException) {
            }
            return date
        }

        fun getDate(date: Date?): String {
            if (date == null) {
                return ""
            }
            val DATE_FORMAT_OBJ = SimpleDateFormat("dd-MM-yyyy")
            return DATE_FORMAT_OBJ.format(date)
        }

        fun getStringFromListWithPattern(list: List<String?>, pattern: String): String? {
            var string = ""
            for (`val` in list) {
                if (`val` != null && !`val`.trim { it <= ' ' }.isEmpty()) {
                    if (!string.isEmpty()) {
                        string += pattern
                    }
                    string += `val`
                }
            }
            return string
        }

        fun getDefaultDateTimeIn12Hour(date: Date?): String? {
            if (date == null) {
                return ""
            }
            val SIMPLE_DATE_TIME_FORMAT = SimpleDateFormat("M/d/yyyy hh:mm aa")
            return SIMPLE_DATE_TIME_FORMAT.format(date)
        }

        fun getDefaultDateTimeSec(date: Date?): String? {
            if (date == null) {
                return ""
            }
            val SIMPLE_DATE_TIME_FORMAT = SimpleDateFormat(MyUtils.DATE_TIME_SEC_FORMAT)
            return SIMPLE_DATE_TIME_FORMAT.format(date)
        }


        fun getVisibility(value: Boolean): Int {
            return if (value) View.VISIBLE else View.GONE
        }

        fun getDefaultBackground(context: Context?, resId: Int, value: Boolean): Int {
            return if (value) resId else R.color.transparent
        }

        fun getDefaultBackgroundColor(context: Context, resId: Int, value: Boolean): Int {
            return if (value) getColor(context, resId) else R.color.transparent
        }

        fun getLong(data: String?): Long {
            return if (data == null) {
                0
            } else java.lang.Long.valueOf(getString(data))
        }


        fun getField(klass: Class<*>, fieldName: String?): Field? {
            var klass = klass
            do {
                try {
                    val f = klass.getDeclaredField(fieldName)
                    f.isAccessible = true
                    return f
                } catch (e: NoSuchFieldException) {
                    klass = klass.superclass
                }
            } while (klass != Any::class.java)
            return null
        }

        fun getStringValue(klass: Class<*>, item: Any?, fieldName: String?): String? {
            var klass = klass
            do {
                try {
                    val f = klass.getDeclaredField(fieldName)
                    f.isAccessible = true
                    val `val` = f[item]
                    return if (`val` is Date) {
                        MyUtils.getDefaultDateTime(`val`)
                    } else getString(`val`)
                } catch (e: NoSuchFieldException) {
                    klass = klass.superclass
                } catch (e: IllegalAccessException) {
                    return ""
                }
            } while (klass != Any::class.java)
            return ""
        }


        fun getString(string: String, objects: Array<Any>): String? {
            var string = string
            return try {
                for (i in objects.indices) {
                    val `object` = objects[i]
                    string = string.replace("{$i}", `object`.toString())
                }
                string
            } catch (e: java.lang.Exception) {
                "!$string!"
            }
        }

        fun getDefaultTime(date: Date?): String? {
            if (date == null) {
                return ""
            }
            val SIMPLE_TIME_FORMAT = SimpleDateFormat(TIME_FORMAT, Locale.US)
            return SIMPLE_TIME_FORMAT.format(date)
        }

        fun getStringFromIntListWithPattern(list: List<Int?>, pattern: String): String? {
            var string = ""
            for (`val` in list) {
                if (`val` != null) {
                    if (!string.isEmpty()) {
                        string += pattern
                    }
                    string += `val`
                }
            }
            return string
        }

        @SuppressLint("NewApi")
        fun getTextSizeByScreen(context: Context, minSize: Int, maxsize: Int): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val size = Point()
            wm.defaultDisplay.getRealSize(size)
            val width = size.x
            val height = size.y
            return if (width == 1920 && height < 1200) {
                maxsize
            } else minSize
        }

        fun getDeviceDetail(): String? {
            var s = ""
            s += """
             OS Version: ${System.getProperty("os.version")}(${Build.VERSION.INCREMENTAL})
             
             """.trimIndent()
            s += """
             OS API Level: ${Build.VERSION.RELEASE}(${Build.VERSION.SDK_INT})
             
             """.trimIndent()
            s += """
             Device: ${getDeviceName()}
             
             """.trimIndent()
            s += """
             Model ${Build.MODEL})
             
             """.trimIndent()
            return s
        }
        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

        private fun capitalize(str: String): String {
            if (TextUtils.isEmpty(str)) {
                return str
            }
            val arr = str.toCharArray()
            var capitalizeNext = true
            val phrase = StringBuilder()
            for (c in arr) {
                if (capitalizeNext && Character.isLetter(c)) {
                    phrase.append(c.uppercaseChar())
                    capitalizeNext = false
                    continue
                } else if (Character.isWhitespace(c)) {
                    capitalizeNext = true
                }
                phrase.append(c)
            }
            return phrase.toString()
        }


    }

}