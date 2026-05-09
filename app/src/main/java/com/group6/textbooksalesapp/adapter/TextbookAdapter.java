package com.group6.textbooksalesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.group6.textbooksalesapp.R;
import com.group6.textbooksalesapp.database.AppDatabase;
import com.group6.textbooksalesapp.model.Textbook;

import java.util.List;

public class TextbookAdapter extends RecyclerView.Adapter<TextbookAdapter.BookViewHolder> {

    // List of books
    List<Textbook> bookList;

    // Constructor
    public TextbookAdapter(List<Textbook> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book,
                        parent,
                        false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder,
                                 int position) {

        // Get current book
        Textbook book = bookList.get(position);

        // Display book information
        holder.tvTitle.setText(book.title);

        holder.tvAuthor.setText(
                "Author: " + book.author
        );

        holder.tvSeller.setText(
                "Seller: " + book.sellerName
        );

        holder.tvPrice.setText(
                "Price: R" + book.price
        );

        holder.tvBank.setText(
                "Bank Info: " + book.bankInfo
        );

        // DELETE BUTTON
        holder.btnDelete.setOnClickListener(v -> {

            // Connect database
            AppDatabase db = Room.databaseBuilder(
                            holder.itemView.getContext(),
                            AppDatabase.class,
                            "textbook-db"
                    )
                    .allowMainThreadQueries()
                    .build();

            // Delete from database
            db.textbookDao().delete(book);

            // Remove from list
            bookList.remove(position);

            // Refresh RecyclerView
            notifyItemRemoved(position);

            notifyItemRangeChanged(
                    position,
                    bookList.size()
            );
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // ViewHolder class
    public static class BookViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTitle,
                tvAuthor,
                tvSeller,
                tvPrice,
                tvBank;

        Button btnDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            // Connect TextViews
            tvTitle = itemView.findViewById(R.id.tvTitle);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);

            tvSeller = itemView.findViewById(R.id.tvSeller);

            tvPrice = itemView.findViewById(R.id.tvPrice);

            tvBank = itemView.findViewById(R.id.tvBank);

            // Connect delete button
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}