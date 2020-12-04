package com.unicauca.domifoods.apiUser;

import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.modelsOrder.OrderRequest;
import com.unicauca.domifoods.modelsOrder.OrderResponse;
import com.unicauca.domifoods.modelsOrder.ProductOrderRequest;
import com.unicauca.domifoods.modelsOrder.ProductOrderResponse;
import com.unicauca.domifoods.modelsProduct.ProductResponse;
import com.unicauca.domifoods.modelsRestaurantLino.RestaurantResponse;
import com.unicauca.domifoods.modelsUser.Client_detail_response;
import com.unicauca.domifoods.modelsUser.Create_user_request;
import com.unicauca.domifoods.modelsUser.Create_user_response;
import com.unicauca.domifoods.modelsUser.GetRestaurant;
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
import retrofit2.http.Path;
 //api for web service
public interface ApiUser {

    @FormUrlEncoded
    @POST("accounts/api/user_register/")
    Call<ResponseBody>singUpUserRegisterTwo(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password2") String password2
            //hola
    );

    @POST("accounts/api/user_register/")
    Call<Create_user_response>singUpUserRegister(@Body Create_user_request create_user_request);

    @POST("accounts/api/user_restaurant_register/")
    Call<User_restaurant_register>user_restaurant_register(@Body User_restaurant_register user_restaurant_register);

    @POST("accounts/api/user_client_register/")
    Call<User_client_register>user_client_register(@Body User_client_register user_restaurant_register);

    //method for api service login
    @FormUrlEncoded
    @POST("accounts/api/login/")
    Call<ResponseBody>login( @Field("username") String username, @Field("password") String password);

    @POST("accounts/api/login/")
    Call<Login_response>loginFull(@Body Login_request login_request);

    //Metodos David
    @GET("restaurants/api/restaurants/")
    Call<List<PostsRestaurants>> getPosts();

    //methods for the second sprint javier cuasapud

    // method for the list of categories for restaurnts this send  id
    @GET("restaurants/api/restaurants/{id}/categories/")
    Call<List<CategoriesResponse>>getCategoriesByRestaurant(@Path("id") int idRestaurant);

    // method for the list of categories for restaurnts this send  id and id resaurants
    @GET("/restaurants/api/restaurants/{id}/category/{idCat}/products/")
    Call<List<ProductResponse>>getProductsByCategoryAndRestaurant(@Path("id") int idRestaurant, @Path("idCat") int idCategoria);
    // method for the list  of restaurnts this send  id and id resaurants

     @GET("restaurants/api/restaurants/{id}/")
    Call<RestaurantResponse>getInfoRestaurantByID(@Path("id") int idRestaurant);


     @GET("accounts/api/user_client_detail/{id}")
     Call<Client_detail_response>getClientDetailByDocument(@Path("id") int document);


     @POST("shopping_cars/api/order_register/")
     Call<OrderResponse>createOrder(@Body OrderRequest orderRequest);


     @POST("shopping_cars/api/order_product/")
     Call<ProductOrderResponse>createProductOrder(@Body ProductOrderRequest productOrderRequest);
    // method for the   of restaurnts
    @GET("restaurants")
    Call<GetRestaurant> list();



    //Obtener los productos de una categoria ejecutiva X y un restaurante Y
     @GET("/restaurants/api/restaurants/{id}/category-executive/{idCat}/products/")
     Call<List<ProductResponse>>getProductsByExecutiveCategoryAndRestaurant(@Path("id") int idRestaurant, @Path("idCat") int idCategoria);


     //Obtener todas las categorias ejecutivas para un restaurnte con id x
     @GET("/restaurants/api/restaurants/{id}/category-executive/")
     Call<List<CategoriesResponse>>getExecutiveCategoriesByRestaurantId(@Path("id") int idRestaurant);

 }
