package au.com.news.coding.data.local

import androidx.room.TypeConverter
import au.com.news.coding.data.remote.model.Source
import com.google.gson.Gson

class TypeConverters {

    @TypeConverter
    fun appToString(app: Source): String = Gson().toJson(app)

    @TypeConverter
    fun stringToApp(string: String): Source = Gson().fromJson(string, Source::class.java)

}