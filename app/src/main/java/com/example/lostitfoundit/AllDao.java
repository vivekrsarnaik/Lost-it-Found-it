package com.example.lostitfoundit;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AllDao {

    @Insert
    void createPost(Post post);

    @Query("select * from post")
    List<Post> getAllPosts();

    @Query("select * from post where creator = :creatorId")
    Post[] getUserPosts(int creatorId);

    @Query("update post set status = 'CLAIMED', creator = :uid where pid = :pid")
    void claimItem(int pid, int uid);

    @Insert
    void insertUser(User user);

    @Query("select * from user")
    List<User> getAllUsers();

    @Query("select * from user where email = :email and password = :password")
    User getUserAtLogin(String email, String password);

    @Query("select * from user where uid = :id")
    User getUserById(int id);
}
