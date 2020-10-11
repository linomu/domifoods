package com.unicauca.domifoods.apiUser;

import com.unicauca.domifoods.modelsUser.Create_user_request;
import com.unicauca.domifoods.modelsUser.Create_user_response;
import com.unicauca.domifoods.modelsUser.Login_request;
import com.unicauca.domifoods.modelsUser.Login_response;
import com.unicauca.domifoods.modelsUser.PostsRestaurants;
import com.unicauca.domifoods.modelsUser.User_client_register;
import com.unicauca.domifoods.modelsUser.User_restaurant_register;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUser {

    @FormUrlEncoded
    @POST("accounts/api/user_register/")
    Call<ResponseBody>singUpUserRegisterTwo(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password2") String password2
    );

    @POST("accounts/api/user_register/")
    Call<Create_user_response>singUpUserRegister(@Body Create_user_request create_user_request);

    @POST("accounts/api/user_restaurant_register/")
    Call<User_restaurant_register>user_restaurant_register(@Body User_restaurant_register user_restaurant_register);

    @POST("accounts/api/user_client_register/")
    Call<User_client_register>user_client_register(@Body User_client_register user_restaurant_register);


    @FormUrlEncoded
    @POST("accounts/api/login/")
    Call<ResponseBody>login( @Field("username") String username, @Field("password") String password);

    @POST("accounts/api/login/")
    Call<Login_response>loginFull(@Body Login_request login_request);

    //Metodos David
    @GET("restaurants")
    Call<List<PostsRestaurants>> getPosts();


}
