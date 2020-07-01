package com.example.project.View;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Presentor.BookAdapter;
import com.example.project.Presentor.BookPresenter;
import com.example.project.R;

public class MainAccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Editor editor;
    private RecyclerView recyclerView;
    BookPresenter bookPresenter;
    ImageView bookShelf;
    TextView mainText;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bookShelf = findViewById(R.id.book_shelf);
        mainText = findViewById(R.id.search_view_text);

        mainText.setVisibility(View.VISIBLE);
        bookShelf.setVisibility(View.VISIBLE);

        sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bookPresenter = new BookPresenter(this);

        recyclerView =  findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.INVISIBLE);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void showBooks(final BookAdapter adapter){
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_book).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                search(query);
                return false;
            }
        };
        searchView.clearFocus();
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    public void search(String query){
        mainText.setVisibility(View.INVISIBLE);
        bookShelf.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        bookPresenter.getBook(this,query);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.acc_exit:
                editor.clear();
                editor.putBoolean("isInAccount", false).commit(); //запомнить состояние выхода
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
