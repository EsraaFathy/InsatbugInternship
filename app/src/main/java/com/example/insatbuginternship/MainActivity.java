package com.example.insatbuginternship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.insatbuginternship.databinding.ActivityMainBinding;
import com.squareup.leakcanary.LeakCanary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding activityMainBinding;
    ArrayList<ItemModel> itemModelsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            Toast.makeText(this, "there is a leak", Toast.LENGTH_SHORT).show();
            return;
        }
        LeakCanary.install(getApplication());
        
        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoIt().execute();

            }
        });


    }


    private void findNumberOfWordsRebated(String text){
        String[] wordsArray =text.split(" ");

        Map<String,Integer> wordCount = new HashMap<>();
        for (String word :wordsArray){
            if (wordCount.containsKey(word)){
                wordCount.put(word.toLowerCase(),wordCount.get((word)+1));
            }else {
                wordCount.put(word,1);
            }
        }
        Set<String> worsInString=wordCount.keySet();
        itemModelsArray=new ArrayList<>();
        for (String word : worsInString){
            String s= String.valueOf(wordCount.get(word));
            ItemModel itemModel=new ItemModel(s,word);
            itemModelsArray.add(itemModel);
        }
        WordsAdapter wordsAdapter = new WordsAdapter(this, itemModelsArray);
        activityMainBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recycler.setAdapter(wordsAdapter);

    }



    @SuppressLint("StaticFieldLeak")
    public class DoIt extends AsyncTask<Void,Void,Void> {
        String words;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityMainBinding.progressPar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document= Jsoup.connect("https://instabug.com/").get();
                words=document.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activityMainBinding.progressPar.setVisibility(View.INVISIBLE);
            findNumberOfWordsRebated(words);


        }
    }

}
