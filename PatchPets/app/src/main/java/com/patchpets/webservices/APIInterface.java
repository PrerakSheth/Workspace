package com.patchpets.webservices;

import com.patchpets.model.requestModel.ApiRequest;
import com.patchpets.model.requestModel.BreedListRequestModel;
import com.patchpets.model.requestModel.CardDetailRequest;
import com.patchpets.model.requestModel.CardListRequest;
import com.patchpets.model.requestModel.ChangePasswordRequest;
import com.patchpets.model.requestModel.DeleteCardRequest;
import com.patchpets.model.requestModel.FavouriteUnFavDogRequest;
import com.patchpets.model.requestModel.ForgotPasswordRequest;
import com.patchpets.model.requestModel.GetDogDetailsRequest;
import com.patchpets.model.requestModel.HideShowProfileRequest;
import com.patchpets.model.requestModel.HomeDataRequest;
import com.patchpets.model.requestModel.LogoutRequest;
import com.patchpets.model.requestModel.PartnerListRequest;
import com.patchpets.model.requestModel.ProfileRequest;
import com.patchpets.model.requestModel.ShareYourExperienceRequest;
import com.patchpets.model.requestModel.SignInRequest;
import com.patchpets.model.requestModel.SignUpRequest;
import com.patchpets.model.responseModel.AddDogToProfileResponse;
import com.patchpets.model.responseModel.ApiResponse;
import com.patchpets.model.responseModel.BreedListResponseModel;
import com.patchpets.model.responseModel.CardDetailResponse;
import com.patchpets.model.responseModel.CardListResponse;
import com.patchpets.model.responseModel.FavouriteDogListResponse;
import com.patchpets.model.responseModel.ForgotPasswordResponse;
import com.patchpets.model.responseModel.GetDogDetailsResponse;
import com.patchpets.model.responseModel.HomeDataResponse;
import com.patchpets.model.responseModel.PartnerListResponse;
import com.patchpets.model.responseModel.ProfileResponse;
import com.patchpets.model.responseModel.SetUpBusinessProfileResponse;
import com.patchpets.model.responseModel.SetUpDogOwnerProfileResponse;
import com.patchpets.model.responseModel.SignInResponse;
import com.patchpets.model.responseModel.SignUpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("login")
    Call<SignInResponse> signIn(@Body SignInRequest param);

    @POST("signUp")
    Call<SignUpResponse> signUp(@Body SignUpRequest param);

    @POST("forgotPassword")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest param);

    @POST("resetPassword")
    Call<ApiResponse> changePassword(@Body ChangePasswordRequest param);

    @POST("getDogDetails")
    Call<GetDogDetailsResponse> getDogDetails(@Body GetDogDetailsRequest param);

    @POST("favouriteOrUnfavouriteDog")
    Call<ApiResponse> favouriteOrUnfavouriteDog(@Body FavouriteUnFavDogRequest param);

    @POST("setUpDogOwnerProfile")
    Call<SetUpDogOwnerProfileResponse> setUpDogOwnerProfile(@Body RequestBody request);

    @POST("addDogToProfile")
    Call<AddDogToProfileResponse> addDogProfile(@Body RequestBody request);

    @POST("setUpBusinessUserProfile")
    Call<SetUpBusinessProfileResponse> setUpBusinessProfile(@Body RequestBody request);

    @POST("shareFeedback")
    Call<ApiResponse> shareYourExperience(@Body ShareYourExperienceRequest param);

    @POST("getUserProfile")
    Call<ProfileResponse> getUserProfile(@Body ProfileRequest param);

    @POST("hideUserProfile")
    Call<ApiResponse> hideShowProfile(@Body HideShowProfileRequest param);

    @POST("logout")
    Call<ApiResponse> logout(@Body LogoutRequest param);

    @POST("getCardList")
    Call<CardListResponse> cardList(@Body CardListRequest param);

    @POST("getPartnerList")
    Call<PartnerListResponse> partnerList(@Body PartnerListRequest param);

    @POST("setUpCard")
    Call<CardDetailResponse> cardDetail(@Body CardDetailRequest param);

    @POST("deleteCard")
    Call<ApiResponse> deleteCard(@Body DeleteCardRequest param);

    @POST("getBreedList")
    Call<BreedListResponseModel> breedList(@Body BreedListRequestModel request);

    @POST("getFavouriteDogList")
    Call<FavouriteDogListResponse> getFavouriteDogList(@Body ApiRequest param);

    @POST("getDogList")
    Call<HomeDataResponse> getDogList(@Body HomeDataRequest param);
}
