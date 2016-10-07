/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cjw.evolution.data.source.remote.api;

import com.cjw.evolution.data.model.Token;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Dribbble Auth API (a different endpoint)
 */
public interface DribbbleAuthService {

    @POST("/oauth/token")
    Observable<Token> accessToken(@Query("client_id") String client_id,
                                     @Query("client_secret") String client_secret,
                                     @Query("code") String code);

}
