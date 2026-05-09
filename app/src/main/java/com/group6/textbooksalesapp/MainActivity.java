package com.group6.textbooksalesapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.group6.textbooksalesapp.adapter.TextbookAdapter;
import com.group6.textbooksalesapp.database.AppDatabase;
import com.group6.textbooksalesapp.model.Textbook;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Input fields
    EditText etTitle, etAuthor, etSeller,
            etISBN, etPrice, etCopies,
            etBank, etSearch;

    // Save button
    Button btnSave;

    // RecyclerView for displaying books
    RecyclerView recyclerView;

    // Room database object
    AppDatabase db;

    // Adapter and list
    TextbookAdapter adapter;
    List<Textbook> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge screen
        EdgeToEdge.enable(this);

        // Connect Java to XML layout
        setContentView(R.layout.activity_main);

        // Create/connect Room database
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "textbook-db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Connect EditText fields
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etSeller = findViewById(R.id.etSeller);
        etISBN = findViewById(R.id.etISBN);
        etPrice = findViewById(R.id.etPrice);
        etCopies = findViewById(R.id.etCopies);
        etBank = findViewById(R.id.etBank);
        etSearch = findViewById(R.id.etSearch);

        // Connect save button
        btnSave = findViewById(R.id.btnSave);

        // Connect RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Arrange RecyclerView vertically
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Load books when app opens
        loadBooks();

        // Search books while typing
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {

                // Search books
                searchBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Save button click event
        btnSave.setOnClickListener(v -> {

            // Get text from fields
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String seller = etSeller.getText().toString();
            String isbn = etISBN.getText().toString();
            String priceText = etPrice.getText().toString();
            String copiesText = etCopies.getText().toString();
            String bank = etBank.getText().toString();

            // Check for empty fields
            if (title.isEmpty() ||
                    author.isEmpty() ||
                    seller.isEmpty() ||
                    isbn.isEmpty() ||
                    priceText.isEmpty() ||
                    copiesText.isEmpty() ||
                    bank.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // Check if textbook already exists
            Textbook existingBook =
                    db.textbookDao().findByTitle(title);

            if (existingBook != null) {

                Toast.makeText(this,
                        "Book already exists",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // Create textbook object
            Textbook book = new Textbook();

            // Store values inside object
            book.title = title;
            book.author = author;
            book.sellerName = seller;
            book.isbn = isbn;
            book.price = Double.parseDouble(priceText);
            book.copies = Integer.parseInt(copiesText);
            book.bankInfo = bank;

            // Insert into database
            db.textbookDao().insert(book);

            // Reload RecyclerView
            loadBooks();

            // Show success message
            Toast.makeText(this,
                    "Book saved successfully",
                    Toast.LENGTH_SHORT).show();

            // Clear fields
            etTitle.setText("");
            etAuthor.setText("");
            etSeller.setText("");
            etISBN.setText("");
            etPrice.setText("");
            etCopies.setText("");
            etBank.setText("");
        });
    }

    // Method to load all books
    private void loadBooks() {

        // Get books from database
        bookList = db.textbookDao().getAllBooks();

        // Create adapter
        adapter = new TextbookAdapter(bookList);

        // Display books
        recyclerView.setAdapter(adapter);
    }

    // Method to search books
    private void searchBooks(String text) {

        // Search by title, seller or ISBN
        bookList = db.textbookDao()
                .searchBooks("%" + text + "%");

        // Update RecyclerView
        adapter = new TextbookAdapter(bookList);

        recyclerView.setAdapter(adapter);
    }
}