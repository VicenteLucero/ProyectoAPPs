package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM posts WHERE owner NOT LIKE :owner")
    fun getPostFromOthersUsers(owner: String): List<Post>

    @Query("SELECT * FROM posts WHERE title LIKE :title")
    fun filterByTitle(title: String): List<Post>

    //funcion para buscar por deporte

    @Insert
    fun insertPost(vararg post: Post)

    @Update
    fun updatePost( post: Post)

    @Delete
    fun deletePost(post: Post)
}