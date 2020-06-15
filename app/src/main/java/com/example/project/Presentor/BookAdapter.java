package com.example.project.Presentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.BookData;
import com.example.project.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookAdapter  extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<BookData> mDataset;
    private Context context;

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookTitle;
        TextView bookAuthors;
        ImageView bookImage;
        Button delete;

        BookViewHolder(final View itemView) {
            super(itemView);
            bookTitle = (TextView)itemView.findViewById(R.id.card_title);
            bookAuthors = (TextView)itemView.findViewById(R.id.card_book_authors);
            bookImage = (ImageView)itemView.findViewById(R.id.book_pic);
            delete = (Button)itemView.findViewById(R.id.delete_card);


        }
    }

    public BookAdapter(Context context, List<BookData> myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                         int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        BookViewHolder pvh = new BookViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttpDownloader(context));
        StringBuilder authors = new StringBuilder();


        bookViewHolder.bookTitle.setText(mDataset.get(i).getTitle());
        JsonArray authorsList =  mDataset.get(i).getAuthors();
        if (authorsList!=null)
            for (JsonElement author :authorsList)
                authors.append(author.toString()+"\n");


        bookViewHolder.bookAuthors.setText(authors);
        builder.build()
                .load(mDataset.get(i).getImageLink("smallThumbnail").replace("\"","").trim())
                .into(bookViewHolder.bookImage);


        int pos = i;
        final BookData bookData = mDataset.get(pos);
        bookViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(bookData);
            }
        });

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void removeItem(BookData bookData){
        int position = mDataset.indexOf(bookData);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
}
