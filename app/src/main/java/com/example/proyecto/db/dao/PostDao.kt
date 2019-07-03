package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.Post
import com.example.proyecto.db.models.Sport

@Dao
interface PostDao {
    @Query("SELECT * FROM posts WHERE owner NOT LIKE :owner")
    fun getPostFromOthersUsers(owner: String): List<Post>

    @Query("SELECT * FROM posts WHERE title LIKE :title")
    fun filterByTitle(title: String): List<Post>

    @Query("SELECT * FROM posts WHERE id LIKE :id")
    fun getPost(id: Int): Post

    @Query("SELECT * FROM posts WHERE required > 0")
    fun selectAllActive(): List<Post>

    //funcion para buscar por deporte

    @Insert
    fun insertPost(vararg post: Post)

    @Update
    fun updatePost( post: Post)

    @Delete
    fun deletePost(post: Post)
}