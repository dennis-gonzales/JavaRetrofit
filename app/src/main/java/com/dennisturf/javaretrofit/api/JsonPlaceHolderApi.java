package com.dennisturf.javaretrofit.api;

import com.dennisturf.javaretrofit.model.Comment;
import com.dennisturf.javaretrofit.model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET("/posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] id,
            @Query("_sort") String sort,
            @Query("_order") String order
    );
    // Class: Integer.ValueOf(1) and as for array-> new Integer[]{1, 3}

    @GET
    Call<List<Post>> getPosts(@Url String url);
    // direct url: "/posts?userId=1"

    @GET("/posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("/posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int id);
    // primitive int: 1

    @POST("/posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("/posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("/posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);
}
