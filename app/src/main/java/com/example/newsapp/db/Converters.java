package com.example.newsapp.db;

import androidx.room.TypeConverter;

import com.example.newsapp.model.Source;

// This class provides type converters for Room database to handle custom data types.
// It allows the conversion of a custom Source object to a String and vice versa,
// enabling Room to store non-primitive types in the database.
public class Converters {

    /**
     * Converts a Source object to its String representation.
     * This method is used by Room to convert the Source object into a format
     * that can be stored in the database, such as a String.
     *
     * @param source The Source object to be converted.
     * @return The name of the Source as a String.
     */
    @TypeConverter
    public String fromSource(Source source) {
        return source.getName();
    }

    /**
     * Converts a String back to a Source object.
     * This method is used by Room to convert the stored String value
     * back into a Source object when reading from the database.
     *
     * @param name The name of the Source stored as a String.
     * @return A new Source object with the given name.
     */
    @TypeConverter
    public Source toSource(String name) {
        return new Source(name, name);
    }
}
