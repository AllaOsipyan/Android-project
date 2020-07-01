package com.example.project.View;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.project.Model.BookData;
import com.example.project.Model.BookService;
import com.example.project.R;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Widget extends AppWidgetProvider {

    static AppWidgetManager widgetManager;
    static int[] widgetIds;
    static int currNum = 0;
    Bitmap bitmap = null;
    static RemoteViews remoteViews;
    BookService bookService;
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        bookService = new BookService(context);
        List<BookData> books =  bookService.findAllBooks();
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        ArrayList<String> lastBooksTitles = new ArrayList<>();
        ArrayList<String> lastBooksImages = new ArrayList<>();
        Intent active = new Intent(context, Widget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        if(books.size()>=10){
            for (int i = books.size()-10; i < books.size(); i++){
                lastBooksTitles.add(books.get(i).getTitle());
                lastBooksImages.add(books.get(i).getImage());
            }

            active.putStringArrayListExtra("title", lastBooksTitles);
            active.putStringArrayListExtra("image", lastBooksImages);}
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_button, actionPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        widgetManager = appWidgetManager;
        widgetIds = appWidgetIds;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (ACTION_WIDGET_RECEIVER.equals(action)) {
            ArrayList<String> title= new ArrayList<>();
            ArrayList<String> image= new ArrayList<>();
            try {
                title = intent.getStringArrayListExtra("title");
                image = intent.getStringArrayListExtra("image");

                try {
                    final ArrayList<String> finalImage = image;
                    final ArrayList<String> finalTitle = title;
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            try {
                                bitmap = BitmapFactory.decodeStream((InputStream) new URL(finalImage.get(currNum).replace("\"","").trim()).getContent());
                                remoteViews.setImageViewBitmap(R.id.widget_image, bitmap);
                                remoteViews.setTextViewText(R.id.widget_text, finalTitle.get(currNum));
                                currNum = currNum == 9? 0: currNum+1;
                                if(widgetManager!=null)
                                    widgetManager.updateAppWidget(widgetIds, remoteViews);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                Log.e("Error", "msg = null");
            }


        }
        super.onReceive(context, intent);
    }
}