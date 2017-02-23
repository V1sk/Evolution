package com.cjw.evolution.data.source.remote.api;

import com.cjw.evolution.data.model.Comment;
import com.cjw.evolution.data.model.Following;
import com.cjw.evolution.data.model.LikeResponse;
import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.model.User;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by CJW on 2016/7/19.
 */
public interface DribbbleApiService {

    @GET("shots")
    Observable<List<Shots>> getShots(@Query("sort") String sort, @Query("page") int page, @Query("per_page") int pageSize);

    @GET("users/{user}/shots")
    Observable<List<Shots>> listShotsForUser(@Path("user") long userId, @Query("page") int page, @Query("per_page") int pageSize);

    @GET("shots")
    Observable<List<Shots>> getTeamsShots(@Query("list") String teams, @Query("page") int page, @Query("per_page") int pageSize);

    @GET("shots/{id}/comments")
    Observable<List<Comment>> getComments(@Path("id") long shotId,
                                          @Query("page") int page,
                                          @Query("per_page") int pageSize);

    @GET("user")
    Observable<User> getUser();

    @POST("shots/{id}/like")
    Observable<LikeResponse> addToLike(@Path("id") long id);

    @GET("shots/{id}/like")
    Observable<LikeResponse> checkIfLike(@Path("id") long id);

    @DELETE("shots/{id}/like")
    Observable<Object> unLike(@Path("id") long id);

    @GET("user/following")
    Observable<List<Following>> getFollowing(@Query("page") int page,
                                             @Query("per_page") int pageSize);

    @GET("user/following/{user}")
    Observable<Void> following(@Path("user") long userId);

    @PUT("users/{user}/follow")
    Observable<Void> follow(@Path("user") long userId);

    @DELETE("users/{user}/follow")
    Observable<Void> unfollow(@Path("user") long userId);

    @GET("shots/{id}/likes")
    Observable<List<LikeUser>> listLikes(@Path("id") long shotsId,
                                   @Query("page") int page,
                                   @Query("per_page") int pageSize);
}
