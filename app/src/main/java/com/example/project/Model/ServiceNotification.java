package com.example.project.Model;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.project.R;
import com.example.project.View.MainAccountActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class ServiceNotification extends Service {
    private static String CHANNEL_ID = "Book channel";
    BookService bookService;
    Bitmap bitmap = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookService = new BookService(this);
        getQuery();
    }

    public void notification(final String title, final String imageUrl, final String message) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl.replace("\"","").trim()).getContent());

                    builder.setSmallIcon(R.drawable.book_background)
                            .setLargeIcon(bitmap)
                            .setContentTitle(title)
                            .setContentText(message);
                    notificationManager.notify(101, builder.build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();


    }
    Random rnd = new Random();
    private void getQuery() {

        List<BookData> userBooks = bookService.findAllBooks();
        if (userBooks.size()!=0) {
            int i = rnd.nextInt(userBooks.size());
            notification(userBooks.get(i).getTitle(),userBooks.get(i).getImage(), "Вы недавно искали");
        }
    }

}