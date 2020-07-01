package com.example.project.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.example.project.Presentor.BookPresenter;
import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonConvert {

    private static String BASE_URL = "https://www.googleapis.com";
    private static String API_KEY = "AIzaSyBu8p3GUzpvoj6PVlZRGi6XMvD1l0kc9F0";
    private static Gson gson = new GsonBuilder().create();

    static Retrofit  retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build();



    public static class ParseTask extends AsyncTask {
        BookPresenter bookPresenter = new BookPresenter();
        BookData bookData;
        Context context;
        String bookName = "qwertyuiopsd";
        BookService bookService ;
        public  List<BookData> booksList = new ArrayList<>();
        public Link book_response = retrofit.create(Link.class);

        public ParseTask(Context context, String bookName){
            this.context = context;
            this.bookName = bookName;
            bookService = new BookService(context);
        }
        boolean hasNetwork() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        )
                            return true;
                    }
                } else {
                    try {
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            return false;
        }
        @Override
        protected List<BookData> doInBackground(Object[] objects) {
            if(hasNetwork()){
                try {
                    Map<String, String> mapJson = new HashMap<String, String>();
                    mapJson.put("key", API_KEY);
                    mapJson.put("q", bookName);
                    Call<JsonObject> call = book_response.getBooks(mapJson);
                    Response<JsonObject> response = call.execute();

                    if (response.isSuccessful() && response.code() == 200) {
                        JSONObject parentJson = new JSONObject(String.valueOf(response.body()));
                        JSONArray books = parentJson.getJSONArray("items");
                        for (int i = 0; i<books.length(); i++){
                            JSONObject bookInfo = ((JSONObject) books.get(i)).getJSONObject("volumeInfo");
                            Gson gson = new Gson();
                            bookData = gson.fromJson(String.valueOf(bookInfo), BookData.class);
                            booksList.add(bookData);

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            if(hasNetwork()){
                for (BookData book : booksList) {
                    bookService.addBook(book, bookName);
                }
                bookPresenter.presentBook(context);
            }

        }
    }
}