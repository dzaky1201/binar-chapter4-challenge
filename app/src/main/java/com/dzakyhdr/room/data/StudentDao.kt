package com.dzakyhdr.room.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.dzakyhdr.room.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM Student ORDER BY judul ASC")
    fun getAllStudent(): List<Student>

    @Query("SELECT * FROM Student ORDER BY judul DESC")
    fun getAllDataDesc(): List<Student>

    @Insert(onConflict = REPLACE)
    fun insertStudent(student: Student):Long

    @Update
    fun updateStudent(student: Student):Int

    @Delete
    fun deleteStudent(student: Student):Int
}