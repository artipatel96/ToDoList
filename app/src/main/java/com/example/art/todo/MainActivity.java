package com.example.art.todo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText title_txt;
    EditText description_txt;
    ListView list;
    Button b_add;
    ArrayList<String> titles_arr =new ArrayList<String>();
    ArrayList<String> descriptions_arr =new ArrayList<String>();
    CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List");
        centerTitle();

        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar)

        title_txt = (EditText)findViewById(R.id.input_title);
        description_txt = (EditText)findViewById(R.id.input_description);
        list = (ListView)findViewById(R.id.list);
        b_add = (Button)findViewById(R.id.add_button);
        list.setAdapter(customAdapter);

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTitle = title_txt.getText().toString();
                String getDescription = description_txt.getText().toString();

                if(getTitle.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Title is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(getDescription.equals("")){
                    Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                    titles_arr.add(getTitle);
                    descriptions_arr.add("");
                    customAdapter.notifyDataSetChanged();
                    title_txt.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                    titles_arr.add(getTitle);
                    descriptions_arr.add(getDescription);
                    customAdapter.notifyDataSetChanged();
                    title_txt.setText("");
                    description_txt.setText("");
                }
                // type whatevr in this function
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                titles_arr.remove(position);
                descriptions_arr.remove(position);
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                customAdapter.notifyDataSetChanged();
                return true;
            }

        });
    }

    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles_arr.size(); // returns the # of items
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.list_children,null);
            TextView title_view = convertView.findViewById(R.id.title);
            TextView descr_view = convertView.findViewById(R.id.descr);
            title_view.setText(titles_arr.get(position));
            descr_view.setText(descriptions_arr.get(position));

            return convertView;
        }
    }
}
