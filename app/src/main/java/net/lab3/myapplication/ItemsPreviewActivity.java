/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.appcompat.app.AppCompatActivity
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package net.lab3.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import net.lab3.myapplication.ItemsModel;

public class ItemsPreviewActivity
extends AppCompatActivity {
    ImageView imageView;
    ItemsModel itemsModel;
    TextView textView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131361820);
        this.imageView = (ImageView)this.findViewById(2131165292);
        this.textView = (TextView)this.findViewById(2131165370);
        Intent intent = this.getIntent();
        if (intent.getExtras() != null) {
            this.itemsModel = (ItemsModel)intent.getSerializableExtra("items");
            this.imageView.setImageBitmap(BitmapFactory.decodeByteArray((byte[])this.itemsModel.getImageByteArray(), (int)0, (int)this.itemsModel.getImageByteArray().length));
            TextView textView = this.textView;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.itemsModel.getTitle());
            stringBuilder.append('\n');
            stringBuilder.append(this.itemsModel.getDescription());
            stringBuilder.append('\n');
            stringBuilder.append(this.itemsModel.getDate());
            stringBuilder.append('\n');
            stringBuilder.append(this.itemsModel.getPriority());
            textView.setText((CharSequence)stringBuilder.toString());
        }
    }
}

