package comp5216.sydney.edu.au.SI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.android.speech.R;

import java.util.ArrayList;
import java.util.Locale;

import comp5216.sydney.edu.au.SI.Tag.RecyclerViewClickListener;
import comp5216.sydney.edu.au.SI.Tag.SearchByTags;
import comp5216.sydney.edu.au.SI.Tag.Translation;
import comp5216.sydney.edu.au.SI.Translator.GoogleTranslate;

public class Speaker extends AppCompatActivity {

    float x1,x2,y1,y2;
    TextToSpeech t1;
    TextView ed1;
    ImageButton b1;

    GoogleTranslate translator;
    EditText translateedittext;
    TextView translatabletext;



    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private static final String STATE_RESULTS = "results";

    private ArrayList<Translation> translationArrayList = new ArrayList<Translation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        ed1=(TextView) findViewById(R.id.editText);
        b1=(ImageButton)findViewById(R.id.button);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> results = savedInstanceState == null ? null :
                savedInstanceState.getStringArrayList(STATE_RESULTS);
        mAdapter = new Speaker.ResultAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Speaker.this, SearchByTags.class);
                String afterTranslate = translationArrayList.get(translationArrayList.size()-position-1).getAfterTranslate();
                intent.putExtra("AfterTranslate", afterTranslate);
                String beforeTranslate = translationArrayList.get(translationArrayList.size()-position-1).getBeforeTranslate();
                intent.putExtra("BeforeTranslate", beforeTranslate);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //TODO item 长按事件
            }
        }));

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        translateedittext = (EditText) findViewById(R.id.translateedittext);
        ImageButton translatebutton = (ImageButton) findViewById(R.id.translatebutton);
        translatebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new EnglishToTagalog().execute();




            }
        });
    }

//    public void translatebutton(){
//
//        new EnglishToTagalog().execute();
//
//    }

    private class EnglishToTagalog extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        protected void onError(Exception ex) {

        }
        @Override
        protected Void doInBackground(Void... params) {

            try {
                translator = new GoogleTranslate("AIzaSyDq2bR7kYod5qSFSIDlszEpog-VhNMroYk");

                Thread.sleep(1000);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            //start the progress dialog
            progress = ProgressDialog.show(Speaker.this, null, "Translating...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();

            super.onPostExecute(result);
            translated();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }




    public void translated(){

        String translatetotagalog = translateedittext.getText().toString();//get the value of text
        String text = translator.translte(translatetotagalog, "zh-CN", "en");
        translationArrayList.add(new Translation(translatetotagalog,text));
            ed1.setText(text);
            mAdapter.addResult(text);
            mRecyclerView.smoothScrollToPosition(0);

    }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_result, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
        }

    }

    private static class ResultAdapter extends RecyclerView.Adapter<Speaker.ViewHolder> {

        private final ArrayList<String> mResults = new ArrayList<>();

        ResultAdapter(ArrayList<String> results) {
            if (results != null) {
                mResults.addAll(results);
            }
        }

        @Override
        public Speaker.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Speaker.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(Speaker.ViewHolder holder, int position) {
            holder.text.setText(mResults.get(position));
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        void addResult(String result) {
            mResults.add(0, result);
            notifyItemInserted(0);
        }

        public ArrayList<String> getResults() {
            return mResults;
        }

    }




    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=touchevent.getX();
                y1=touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=touchevent.getX();
                y2=touchevent.getY();
                if(x1>x2){
                    Intent i = new Intent(Speaker.this, MainActivity.class);
                    startActivities(new Intent[]{i});

                }
                break;
        }
        return false;
    }
}
