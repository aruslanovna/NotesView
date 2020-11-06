/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  java.io.ByteArrayOutputStream
 *  java.io.FileNotFoundException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.List
 */
package net.lab3.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.lab3.myapplication.DatabaseHelper;
import net.lab3.myapplication.ItemsModel;
import net.lab3.myapplication.MainActivity;
import net.lab3.myapplication.MinMaxFilter;

public class AddNote
extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    Button btnAddData;
    Button btnAddImg;
    EditText editDescription;
    EditText editPriority;
    EditText editTitle;
    ImageView imageView;
    List<ItemsModel> itemsModelList = new ArrayList();
    ListView listView;
    DatabaseHelper myDb;
    MainActivity.NotesAdapter notesAdapter;

    public void AddData() {
        this.btnAddData.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)AddNote.this.imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)byteArrayOutputStream);
                byte[] arrby = byteArrayOutputStream.toByteArray();
                if (AddNote.this.myDb.insertData(AddNote.this.editTitle.getText().toString(), AddNote.this.editDescription.getText().toString(), Integer.parseInt((String)String.valueOf((Object)AddNote.this.editPriority.getText())), arrby)) {
                    Log.e((String)"main activity", (String)"item long clicked");
                    return;
                }
                Log.e((String)"main activity", (String)"ite not clicked");
            }
        });
    }

    public void AddImg() {
        this.btnAddImg.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AddNote.this.getImageFromAlbum();
            }
        });
    }

    public void getImageFromAlbum() {
        try {
            this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            return;
        }
        catch (Exception exception) {
            Log.i((String)"Error", (String)exception.toString());
            return;
        }
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n2 == -1) {
            try {
                Uri uri = intent.getData();
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)this.getContentResolver().openInputStream(uri));
                this.imageView.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                Toast.makeText((Context)this, (CharSequence)"Something went wrong", (int)1).show();
            }
            return;
        }
        Toast.makeText((Context)this, (CharSequence)"You haven't picked Image", (int)1).show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.add_note);
        this.getIntent();
        this.myDb = new DatabaseHelper((Context)this);
        this.listView = (ListView)this.findViewById(R.id.listview);
        try {
            this.myDb.getAllCotacts();
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        this.listView.setAdapter((ListAdapter)this.notesAdapter);
        this.registerForContextMenu((View)this.listView);
        new Date();
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.editTitle = (EditText)this.findViewById(R.id.editText_title);
        this.editDescription = (EditText)this.findViewById(R.id.editText_description);
        EditText editText = this.editPriority = (EditText)this.findViewById(R.id.editText_priority);
        InputFilter[] arrinputFilter = new InputFilter[]{new MinMaxFilter("1", "3")};
        editText.setFilters(arrinputFilter);
        this.btnAddData = (Button)this.findViewById(R.id.button_add);
        this.btnAddImg = (Button)this.findViewById(R.id.button_img);
        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        this.imageView.setImageResource(R.drawable.apple);
        this.AddData();
        this.AddImg();
    }

}

