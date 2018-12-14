package com.example.tyler.simplechess;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class PlaybackActivity extends AppCompatActivity {

    ArrayList<String> listOfFiles = new ArrayList<>();
    ArrayList<String[]> tempArrayList = new ArrayList<>();
    String fileName;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        try {
            File fileDir = getFilesDir();
//            FileInputStream readFiles = openFileInput("GudGame.ser");
//            ObjectInputStream in = new ObjectInputStream(readFiles);
//            tempArrayList = (ArrayList<String[]>) in.readObject();
//            in.close();
//            readFiles.close();
            for (String strFiles : fileDir.list())
                System.out.println(strFiles);
        }
        catch(Exception e){
        }
//        final ListView listView = (ListView) findViewById(R.id.playbackList);
        listView = findViewById(R.id.playbackList);
        adapter = new ArrayAdapter<>(this,
                R.layout.layout, getFilesDir().list());
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fileName = (String) listView.getItemAtPosition(position);
                System.out.println(fileName);
                for(int a = 0; a < parent.getChildCount(); a++)
                {
                    parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(getResources().getColor(R.color.selected));
            }
        });

    }

    public void deletePlayback(View v){
        if (fileName != null) {
            File dir = getFilesDir();
            File file = new File(dir, fileName);
            file.delete();
            System.out.println(fileName + " deleted");
            fileName = null;
            ArrayAdapter adapter = new ArrayAdapter<>(this,
                    R.layout.layout, getFilesDir().list());
            listView.setAdapter(adapter);
        }
    }
    public void playback(View v){
        if (fileName != null) {
            try{
                FileInputStream readFiles = openFileInput(fileName);
                ObjectInputStream in = new ObjectInputStream(readFiles);
                tempArrayList = (ArrayList<String[]>) in.readObject();
                in.close();
                readFiles.close();
                Intent intent = new Intent(this, PlayActivity.class);
                intent.putExtra("listOfMoves", tempArrayList);
                startActivity(intent);
                for(String[] str:tempArrayList){
                    System.out.println("MOVE " + str[0] + " " + str[1]);
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
