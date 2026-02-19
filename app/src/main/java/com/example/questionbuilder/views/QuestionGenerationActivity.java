package com.example.questionbuilder.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbuilder.Adapter.MessageAdapter;
import com.example.questionbuilder.Model.Message;
import com.example.questionbuilder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionGenerationActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    TextView welcome_text;
    ScrollView scrollableContent;
    EditText writingText, numberOfQuestion, subjectInput;
    Spinner questionType,difficultyofquestionLevel;
    ImageButton sendButton;
    RecyclerView recyclerViewResponses;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

     String UserDefinedQuestionType;
     String UserDefinedDifficultyOfQuestionLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_generation);

        databaseReference = FirebaseDatabase.getInstance().getReference("Responses");
        messageList = new ArrayList<>();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.systemBars());
            view.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });


        // Text view widgets
        welcome_text = findViewById(R.id.welcome_text);

        // Edit text
        writingText = findViewById(R.id.writingText);
        numberOfQuestion=findViewById(R.id.numberOfQuestion);
        subjectInput=findViewById(R.id.subjectInput);


        // Button
        sendButton = findViewById(R.id.sendButton);
        recyclerViewResponses = findViewById(R.id.recyclerViewResponses);

        //Spinner
        questionType=findViewById(R.id.questionType);
        String [] QuestionType={"MCQ","True or False","Q/A"};
        ArrayAdapter<String>adapter=new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                QuestionType
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // setup of adapter
        questionType.setAdapter(adapter);
        questionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 UserDefinedQuestionType=adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(QuestionGenerationActivity.this, "Please Select Question Type ", Toast.LENGTH_SHORT).show();

            }
        });

        // spinner of Difficulty of Question
        difficultyofquestionLevel=findViewById(R.id.difficultyofquestionLevel);
        String [] difficultyLevelOfQuestion={"Easy","Medium","Hard"};
        ArrayAdapter<String>adapter1=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                difficultyLevelOfQuestion
                );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setup of adapter
        difficultyofquestionLevel.setAdapter(adapter1);
        difficultyofquestionLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserDefinedDifficultyOfQuestionLevel=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(QuestionGenerationActivity.this, "please select difficulty Level of Question ", Toast.LENGTH_SHORT).show();

            }
        });


        messageAdapter = new MessageAdapter(messageList);
        recyclerViewResponses.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerViewResponses.setLayoutManager(llm);

        sendButton.setOnClickListener(v -> {
            String question = writingText.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_ME);
                writingText.setText("");
                callAPI(question);
                questionType.setVisibility(View.GONE);
                numberOfQuestion.setVisibility(View.GONE);
                subjectInput.setVisibility(View.GONE);
                difficultyofquestionLevel.setVisibility(View.GONE);
            } else {
                Log.d("Input", "EditText is empty");
            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            Log.d("Chat", "Added message: " + message + " from: " + sentBy);
            messageAdapter.notifyDataSetChanged();
            recyclerViewResponses.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1); // Remove "Typing..."
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {
        // Add "Typing..." to the chat
        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));

        // Prepare JSON body for request
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");

            // Prepare the messages array
            JSONArray messagesArray = new JSONArray();

            // Add a system message (AI's role and behavior)
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "I want you to act like an educational assistant that helps to generate questions for an assessment or exam.");
            messagesArray.put(systemMessage);

            // Add predefined user message


            JSONObject predefinedMessage = new JSONObject();
            predefinedMessage.put("role", "user");
            String userSpecifiedNumberOfQuestion=numberOfQuestion.getText().toString().trim();
            String userSpecifiedSubject=subjectInput.getText().toString().trim();
            String contentMessage = "Generate a set of " + userSpecifiedNumberOfQuestion + " "
                    + UserDefinedQuestionType + " questions on the topic '"
                    + userSpecifiedSubject + "' for a "+UserDefinedDifficultyOfQuestionLevel+"-level exam.";
            predefinedMessage.put("content",contentMessage );
            messagesArray.put(predefinedMessage);

            // Add user's actual question
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messagesArray.put(userMessage);

            // Add the messages array to the request body
            jsonBody.put("messages", messagesArray);
            jsonBody.put("max_tokens", 2000);
            jsonBody.put("temperature", 0.7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create request body
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        // Create request
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","api key value")  // Replace with your secure API key
                .post(body)
                .build();

        // Execute API call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray choicesArray = jsonObject.getJSONArray("choices");
                        if (choicesArray.length() > 0) {
                            String result = choicesArray.getJSONObject(0).getJSONObject("message").getString("content");
                            addResponse(result.trim()); // Use the actual response here
                            saveResponse(result.trim()); // Save the actual response
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    addResponse("Failed to load response: " + response.body().string());
                }
            }
        });
    }

    private void saveResponse(String responseText) {
        String responseId = databaseReference.push().getKey(); // Generate unique ID
        databaseReference.child(responseId).setValue(responseText)
                .addOnSuccessListener(aVoid -> {
                    // Success: response saved
                    Toast.makeText(this, "Response saved", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Failure
                    Toast.makeText(this, "Failed to save response", Toast.LENGTH_SHORT).show();
                });
    }
}
