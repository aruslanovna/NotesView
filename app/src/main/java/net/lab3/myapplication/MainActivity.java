/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$AdapterContextMenuInfo
 *  android.widget.BaseAdapter
 *  android.widget.Filter
 *  android.widget.Filter$FilterResults
 *  android.widget.Filterable
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SearchView
 *  android.widget.SearchView$OnQueryTextListener
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.Date
 *  java.util.List
 */
package net.lab3.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import net.lab3.myapplication.AddNote;
import net.lab3.myapplication.DatabaseHelper;
import net.lab3.myapplication.ItemsModel;
import net.lab3.myapplication.*;

public class MainActivity
extends AppCompatActivity {
    int[] images = new int[]{R.drawable.apple, R.drawable.banana};
    List<ItemsModel> itemsModelList = new ArrayList();
    ListView listView;
    DatabaseHelper myDb;
    NotesAdapter notesAdapter;

    public void DeleteData(int n) {
        if (this.myDb.deleteData(n) > 0) {
            Toast.makeText((Context)this, (CharSequence)"Data Deleted", (int)1).show();
        } else {
            Toast.makeText((Context)this, (CharSequence)"Data not Deleted", (int)1).show();
        }
        this.notesAdapter.notifyDataSetChanged();
        this.listView.invalidateViews();
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo();
        int n = menuItem.getItemId();
        if (n != 0) {
            if (n == 1) {
                this.DeleteData((int)this.notesAdapter.getItemId(adapterContextMenuInfo.position));
                this.notesAdapter.notifyDataSetChanged();
            }
        } else {
            ItemsModel itemsModel = new ItemsModel((int)this.notesAdapter.getItemId(adapterContextMenuInfo.position), this.notesAdapter.getItemTitle(adapterContextMenuInfo.position), this.notesAdapter.getItemDescription(adapterContextMenuInfo.position), this.notesAdapter.getItemPriority(adapterContextMenuInfo.position), null, this.notesAdapter.getItemDate(adapterContextMenuInfo.position));
            this.startActivity(new Intent((Context)this, UpdateNote.class).putExtra("items", (Serializable)itemsModel));
        }
        return super.onContextItemSelected(menuItem);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);
        this.myDb = new DatabaseHelper((Context)this);
        this.listView = (ListView)this.findViewById(R.id.listview);
        List<ItemsModel> list = null;
        try {
            List<ItemsModel> list2;
            list = list2 = this.myDb.getAllCotacts();
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        this.notesAdapter = new NotesAdapter(list, (Context)this);
        this.listView.setAdapter((ListAdapter)this.notesAdapter);
        this.registerForContextMenu((View)this.listView);
        new Date();
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        contextMenu.add(0, 0, 0, (CharSequence)"Update");
        contextMenu.add(0, 1, 1, (CharSequence)"Delete");
        Log.e((String)"main activity", (String)"item long clicked");
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.search_menu, menu2);
        ((SearchView)menu2.findItem(R.id.searchView).getActionView()).setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            public boolean onQueryTextChange(String string2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" data search");
                stringBuilder.append(string2);
                Log.e((String)"Main", (String)stringBuilder.toString());
                MainActivity.this.notesAdapter.getFilter().filter((CharSequence)string2);
                return true;
            }

            public boolean onQueryTextSubmit(String string2) {
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.create:
                startActivity(new Intent(MainActivity.this, AddNote.class));
                Toast.makeText((Context)this, (CharSequence)"Add new note", (int)0).show();
                return true;
            case R.id.searchView:
                return super.onOptionsItemSelected(menuItem);

            case R.id.sort:
                this.notesAdapter.Sort();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }



    }

    public class NotesAdapter
    extends BaseAdapter
    implements Filterable {
        private Context context;
        private List<ItemsModel> itemsModelListFiltered;
        private List<ItemsModel> itemsModelsl;

        public NotesAdapter(List<ItemsModel> list, Context context) {
            this.itemsModelsl = list;
            this.itemsModelListFiltered = list;
            this.context = context;
        }

        public void Sort() {
            Collections.sort(this.itemsModelListFiltered, (Comparator)new Comparator<ItemsModel>(){

                public int compare(ItemsModel itemsModel, ItemsModel itemsModel2) {
                    if (itemsModel.getPriority() == itemsModel2.getPriority()) {
                        return 0;
                    }
                    if (itemsModel.getPriority() > itemsModel2.getPriority()) {
                        return 1;
                    }
                    return -1;
                }
            });
            this.notifyDataSetChanged();
        }

        public int getCount() {
            return this.itemsModelListFiltered.size();
        }

        public Filter getFilter() {
            return new Filter(){

                protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                    Filter.FilterResults filterResults = new Filter.FilterResults();
                    if (charSequence != null && charSequence.length() != 0) {
                        ArrayList arrayList = new ArrayList();
                        String string2 = charSequence.toString().toLowerCase();
                        for (ItemsModel itemsModel : NotesAdapter.this.itemsModelsl) {
                            if (itemsModel.getTitle().toLowerCase().contains((CharSequence)string2) || itemsModel.getDescription().toLowerCase().contains((CharSequence)string2) || Integer.toString((int)itemsModel.getPriority()).contains((CharSequence)string2)) {
                                arrayList.add((Object)itemsModel);
                            }
                            filterResults.count = arrayList.size();
                            filterResults.values = arrayList;
                        }
                    } else {
                        filterResults.count = NotesAdapter.this.itemsModelsl.size();
                        filterResults.values = NotesAdapter.this.itemsModelsl;
                    }
                    return filterResults;
                }

                protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                    NotesAdapter.this.itemsModelListFiltered = (List)filterResults.values;
                    NotesAdapter.this.notifyDataSetChanged();
                }
            };
        }

        public Object getItem(int n) {
            return this.itemsModelListFiltered.get(n);
        }

        public String getItemDate(int n) {
            return ((ItemsModel)this.itemsModelListFiltered.get(n)).getDate();
        }

        public String getItemDescription(int n) {
            return ((ItemsModel)this.itemsModelListFiltered.get(n)).getDescription();
        }

        public long getItemId(int n) {
            return ((ItemsModel)this.itemsModelListFiltered.get(n)).getId();
        }

        public int getItemPriority(int n) {
            return ((ItemsModel)this.itemsModelListFiltered.get(n)).getPriority();
        }

        public String getItemTitle(int n) {
            return ((ItemsModel)this.itemsModelListFiltered.get(n)).getTitle();
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            View view2 = MainActivity.this.getLayoutInflater().inflate(R.layout.row_items, null);
            TextView textView = (TextView)view2.findViewById(R.id.name);
            TextView textView2 = (TextView)view2.findViewById(R.id.email);
            TextView textView3 = (TextView)view2.findViewById(R.id.date);
            ImageView imageView = (ImageView)view2.findViewById(R.id.images);
            ImageView imageView2 = (ImageView)view2.findViewById(R.id.imagePriority);
            textView.setText((CharSequence)((ItemsModel)this.itemsModelListFiltered.get(n)).getTitle());
            textView2.setText((CharSequence)Integer.toString((int)((ItemsModel)this.itemsModelListFiltered.get(n)).getId()));
            textView3.setText((CharSequence)((ItemsModel)this.itemsModelListFiltered.get(n)).getDate());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray((byte[])((ItemsModel)this.itemsModelListFiltered.get(n)).getImageByteArray(), (int)0, (int)((ItemsModel)this.itemsModelListFiltered.get(n)).getImageByteArray().length));
            int n2 = ((ItemsModel)this.itemsModelListFiltered.get(n)).getPriority();
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        imageView2.setImageResource(R.drawable.one);
                        return view2;
                    }
                    imageView2.setImageResource(R.drawable.three);
                    return view2;
                }
                imageView2.setImageResource(R.drawable.two);
                return view2;
            }
            imageView2.setImageResource(R.drawable.one);
            return view2;
        }

        protected void onListItemClick(ListView listView, View view, int n, long l) {
            this.onListItemClick(listView, view, n, l);
            Log.e((String)"main activity", (String)"item clicked");
        }

    }

}

