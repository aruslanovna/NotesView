/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.ListView
 *  android.widget.TextView
 *  androidx.appcompat.app.AppCompatActivity
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package net.lab3.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.lab3.myapplication.DatabaseHelper;
import net.lab3.myapplication.ItemsModel;
import net.lab3.myapplication.MainActivity;
import net.lab3.myapplication.MinMaxFilter;

public class UpdateNote
extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    Button btnAddImg;
    Button btnUpdateData;
    EditText editDate;
    EditText editMarks;
    EditText editName;
    EditText editSurname;
    EditText editTextId;
    ImageView imageView;
    ItemsModel itemsModel;
    List<ItemsModel> itemsModelList = new ArrayList();
    ListView listView;
    DatabaseHelper myDb;
    MainActivity.NotesAdapter notesAdapter;
    TextView textDate;
    EditText textDescription;
    EditText textPriority;
    EditText textTitle;

    public void UpdateData() {
        this.btnUpdateData.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (UpdateNote.this.myDb.updateDataWithout(UpdateNote.this.itemsModel.getId(), UpdateNote.this.textTitle.getText().toString(), UpdateNote.this.textDescription.getText().toString(), Integer.parseInt((String)String.valueOf((Object)UpdateNote.this.textPriority.getText())))) {
                    Log.e((String)"main activity", (String)"item updated");
                    return;
                }
                Log.e((String)"main activity", (String)"item not updated");
            }
        });
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.update_note);
        Intent intent = this.getIntent();
        this.textTitle = (EditText)this.findViewById(R.id.editText_title);
        this.textDescription = (EditText)this.findViewById(R.id.editText_description);
        this.textPriority = (EditText)this.findViewById(R.id.editText_priority);
        this.textDate = (TextView)this.findViewById(R.id.Date);
        if (intent.getExtras() != null) {
            this.itemsModel = (ItemsModel)intent.getSerializableExtra("items");
            this.textTitle.setText((CharSequence)this.itemsModel.getTitle());
            this.textDescription.setText((CharSequence)this.itemsModel.getDescription());
            this.textPriority.setText((CharSequence)String.valueOf((int)this.itemsModel.getPriority()));
            EditText editText = this.textPriority;
            InputFilter[] arrinputFilter = new InputFilter[]{new MinMaxFilter("1", "3")};
            editText.setFilters(arrinputFilter);
            this.textDate.setText((CharSequence)this.itemsModel.getDate());
        }
        this.myDb = new DatabaseHelper((Context)this);
        this.btnAddImg = (Button)this.findViewById(R.id.button_img);
        this.btnUpdateData = (Button)this.findViewById(R.id.button_update);
        this.UpdateData();
    }

}

