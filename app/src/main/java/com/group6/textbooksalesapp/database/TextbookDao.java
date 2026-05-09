package com.group6.textbooksalesapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.group6.textbooksalesapp.model.Textbook;

import java.util.List;

@Dao
public interface TextbookDao {

    @Insert
    void insert(Textbook book);

    @Query("SELECT * FROM textbooks")
    List<Textbook> getAllBooks();

    @Query("SELECT * FROM textbooks WHERE title = :title LIMIT 1")
    Textbook findByTitle(String title);

    @Query("SELECT * FROM textbooks WHERE title LIKE :search OR sellerName LIKE :search OR isbn LIKE :search")
    List<Textbook> searchBooks(String search);

    @Delete
    void delete(Textbook textbook);

    @Update
    void update(Textbook textbook);
}