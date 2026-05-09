package com.group6.textbooksalesapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.group6.textbooksalesapp.model.Textbook;

@Database(entities = {Textbook.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TextbookDao textbookDao();
}