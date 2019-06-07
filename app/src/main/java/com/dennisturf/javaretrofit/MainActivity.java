package com.dennisturf.javaretrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dennisturf.javaretrofit.api.JsonPlaceHolderApi;
import com.dennisturf.javaretrofit.controlller.AppController;
import com.dennisturf.javaretrofit.model.Comment;
import com.dennisturf.javaretrofit.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Post> posts;
    private List<Comment> comments;

    private Post postResponse;

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getPosts(jsonPlaceHolderApi);
        //getComments(jsonPlaceHolderApi);
        //createPosts(jsonPlaceHolderApi);
    }


    private void getPosts(JsonPlaceHolderApi jsonPlaceHolderApi) {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts( parameters );

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) {

                    posts = response.body();

                    StringBuilder str = new StringBuilder();

                    assert posts != null;
                    for (Post post : posts) {

                        str.append("ID: ").append(post.getId()).append("\n");
                        str.append("User ID: ").append(post.getUserId()).append("\n");
                        str.append("Title: ").append(post.getTitle()).append("\n");
                        str.append("Text: ").append(post.getText()).append("\n\n");

                        textViewResult.setText(str);
                    }

                } else {
                    textViewResult.setText(response.code());
                    Log.d(AppController.TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call,@NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d(AppController.TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getComments(JsonPlaceHolderApi jsonPlaceHolderApi) {

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(1);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call,@NonNull Response<List<Comment>> response) {
                if (response.isSuccessful()) {

                    comments = response.body();

                    StringBuilder str = new StringBuilder();

                    assert comments != null;
                    for (Comment comments : comments) {

                        str.append("Post ID: ").append(comments.getPostId()).append("\n");
                        str.append("ID: ").append(comments.getId()).append("\n");
                        str.append("Name: ").append(comments.getName()).append("\n");
                        str.append("Email: ").append(comments.getEmail()).append("\n\n");
                        str.append("Text: ").append(comments.getText()).append("\n\n");

                        textViewResult.setText(str);
                    }

                } else {
                    textViewResult.setText(response.code());
                    Log.d(AppController.TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call,@NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d(AppController.TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void createPosts(JsonPlaceHolderApi jsonPlaceHolderApi) {

        Post post = new Post(23, "New title", "New text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New title3");
        fields.put("body", "New text3");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call,@NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    postResponse = response.body();

                    StringBuilder str = new StringBuilder();

                    str.append("Code: ").append(response.code()).append("\n");
                    str.append("ID: ").append(postResponse.getId()).append("\n");
                    str.append("User ID: ").append(postResponse.getUserId()).append("\n");
                    str.append("Title: ").append(postResponse.getTitle()).append("\n");
                    str.append("Text: ").append(postResponse.getText()).append("\n\n");

                    textViewResult.setText(str);

                } else {
                    textViewResult.setText(response.code());
                    Log.d(AppController.TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call,@NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d(AppController.TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}