package comp5216.sydney.edu.au.SI.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.android.speech.R;

import java.util.Locale;

/**
 * Created by 老虎 on 2017/10/21.
 */

public class SearchByTags extends AppCompatActivity {

    private TextView mTextBeforeTranslate;
    private TextView mTextAfterTranslate;
    private TextView mShowTags1;
    private TextView mShowTags2;
    private TextView mShowTags3;

    TextToSpeech t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KeyWord.setContext(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbytags);

        mTextBeforeTranslate = (TextView) findViewById(R.id.textBeforeTranslate);
        mTextAfterTranslate = (TextView) findViewById(R.id.textAfterTranslate);
        mShowTags1 = (TextView) findViewById(R.id.showTags1);
        mShowTags2 = (TextView) findViewById(R.id.showTags2);
        mShowTags3 = (TextView) findViewById(R.id.showTags3);


        Intent intent = getIntent();
        String beforeTranslate = intent.getStringExtra("BeforeTranslate");
        String afterTranslate = intent.getStringExtra("AfterTranslate");
        mTextBeforeTranslate.setText(beforeTranslate);
        mTextAfterTranslate.setText(afterTranslate);
        KeyWord kw = new KeyWord(beforeTranslate);


        switch (kw.getSize()) {
            case 0:
                break;
            default:
                ;
            case 3:
                mShowTags3.setText("Tag :" + "  [" + kw.getTheKeyWord(2) + "]");
            case 2:
                mShowTags2.setText("Tag :" + "  [" + kw.getTheKeyWord(1) + "]");
            case 1:
                mShowTags1.setText("Tag :" + "  [" + kw.getTheKeyWord(0) + "]");
                break;

        }

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        t2 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.CHINESE);
                }
            }
        });

        mTextBeforeTranslate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String toSpeak = mTextBeforeTranslate.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mTextAfterTranslate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String toSpeak = mTextAfterTranslate.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
//    public void onClick1(View v) {
//        String toSpeak = mTextBeforeTranslate.getText().toString();
//        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
//        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//    }
//
//    public void onClick2(View v) {
//        String toSpeak = mTextAfterTranslate.getText().toString();
//        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
//        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//    }








}
