package com.example.taskofdigichal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpeechtoText extends AppCompatActivity {
    ImageButton imageButton;
    EditText editText,current_value;
    SpeechRecognizer speechRecognizer;
    Button balanace;
    int count=0;
    int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechto_text);
        imageButton=findViewById(R.id.imgbutton);
        editText=findViewById(R.id.edtxt);
        current_value=findViewById(R.id.edtxt1);
        balanace=(Button)findViewById(R.id.Balance);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
         final Intent speechRecognizerIntent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

         imageButton.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
             @Override
             public void onClick(View view) {
                if(count==0)
                {
                    imageButton.setImageDrawable(getDrawable(R.drawable.ic_mic_on));
                    //startlistining
                    speechRecognizer.startListening(speechRecognizerIntent);
                    count=1;
                }
                else{
                    imageButton.setImageDrawable(getDrawable(R.drawable.ic_mic_off));
                    speechRecognizer.stopListening();
                    count=0;

                }
             }
         });
         speechRecognizer.setRecognitionListener(new RecognitionListener() {
             @Override
             public void onReadyForSpeech(Bundle bundle) {

             }

             @Override
             public void onBeginningOfSpeech() {

             }

             @Override
             public void onRmsChanged(float v) {

             }

             @Override
             public void onBufferReceived(byte[] bytes) {

             }

             @Override
             public void onEndOfSpeech() {

             }

             @Override
             public void onError(int i) {

             }

             @Override
             public void onResults(Bundle bundle) {
                 ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                 editText.setText(data.get(0));
                 setdata();

             }

             @Override
             public void onPartialResults(Bundle bundle) {

             }

             @Override
             public void onEvent(int i, Bundle bundle) {

             }
         });
         balanace.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                // List<Integer> lisst = new ArrayList<>();
                 if(editText.getText()!=null) {
                    int  value = sendData(editText.getText().toString());
                    current_value.setText(String.valueOf(value));
                 }
                 if(current_value.getText()==null){
                     current_value.setText(0);
                 }


             }
         });
    }


    public Integer sendData(String text) {

        String text1 = null;
        int value1 = 0;
        if (text != null) {
            text1 = editText.getText().toString();
            text1 = text1.replaceAll("[^\\d]", " ");
            text1 = text1.trim();

            // Replace all the consecutive white
            // spaces with a single space
            text1 = text1.replaceAll(" +", " ");

            if (text1.equals("")) {
                Toast.makeText(SpeechtoText.this, "No integer found", Toast.LENGTH_SHORT).show();
            } else {
                value1 = Integer.parseInt(text1);
            }


        }
        return value1;

    }
    public void setdata(){
        String str1 = "Sale";
        String str3 = "sale";
        String str2 = "Expense";
        String str4 = "expense";
        if(editText.getText().toString().contains(str1)||editText.getText().toString().contains(str3)){
            int  value = sendData(editText.getText().toString());
            sum = value + sum;
            current_value.setText(String.valueOf(sum));

        }
        else if(editText.getText().toString().contains(str2)||editText.getText().toString().contains(str4)){
            int  value = sendData(editText.getText().toString());
            int sub = 0;
            //current_value.setText(value);
             sub = value-sub;
            current_value.setText(String.valueOf(sub));
        }

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==1){

            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT);
            }
            else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT);
            }
        }


    }
}
