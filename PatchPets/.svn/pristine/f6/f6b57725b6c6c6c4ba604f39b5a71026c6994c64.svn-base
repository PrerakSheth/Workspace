package com.patchpets.webservices;

import com.google.gson.GsonBuilder;
import com.patchpets.BuildConfig;
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
import com.patchpets.utils.Constants;
import com.patchpets.webservices.loggingInterceptor.Level;
import com.patchpets.webservices.loggingInterceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.platform.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequest {
    private Retrofit retrofit;
    private APIInterface apiInterface;
    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(120);

    public APIRequest() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        client.writeTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        client.readTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

        client.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("Version", BuildConfig.VERSION_NAME)
                .build());
        OkHttpClient okHttpClient = client.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().disableHtmlEscaping().create()))
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    public void signUpAPI(SignUpRequest param, final ResponseCallback callback) {
        try {
            Call<SignUpResponse> requestCall = apiInterface.signUp(param);
            requestCall.enqueue(new Callback<SignUpResponse>() {

                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signInAPI(SignInRequest param, final ResponseCallback callback) {
        try {
            Call<SignInResponse> requestCall = apiInterface.signIn(param);
            requestCall.enqueue(new Callback<SignInResponse>() {

                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPasswordAPI(ForgotPasswordRequest param, final ResponseCallback callback) {
        try {
            Call<ForgotPasswordResponse> requestCall = apiInterface.forgotPassword(param);
            requestCall.enqueue(new Callback<ForgotPasswordResponse>() {

                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpDogOwnerProfileAPI(RequestBody param, final ResponseCallback callback) {
        try {
            Call<SetUpDogOwnerProfileResponse> requestCall = apiInterface.setUpDogOwnerProfile(param);
            requestCall.enqueue(new Callback<SetUpDogOwnerProfileResponse>() {

                @Override
                public void onResponse(Call<SetUpDogOwnerProfileResponse> call, Response<SetUpDogOwnerProfileResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<SetUpDogOwnerProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDogProfileAPI(RequestBody param, final ResponseCallback callback) {
        try {
            Call<AddDogToProfileResponse> requestCall = apiInterface.addDogProfile(param);
            requestCall.enqueue(new Callback<AddDogToProfileResponse>() {

                @Override
                public void onResponse(Call<AddDogToProfileResponse> call, Response<AddDogToProfileResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<AddDogToProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpBusinessUserProfileAPI(RequestBody param, final ResponseCallback callback) {
        try {
            Call<SetUpBusinessProfileResponse> requestCall = apiInterface.setUpBusinessProfile(param);
            requestCall.enqueue(new Callback<SetUpBusinessProfileResponse>() {

                @Override
                public void onResponse(Call<SetUpBusinessProfileResponse> call, Response<SetUpBusinessProfileResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<SetUpBusinessProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserProfileAPI(ProfileRequest param, final ResponseCallback callback) {
        try {
            Call<ProfileResponse> requestCall = apiInterface.getUserProfile(param);
            requestCall.enqueue(new Callback<ProfileResponse>() {

                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideShowProfileAPI(HideShowProfileRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.hideShowProfile(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cardListAPI(CardListRequest param, final ResponseCallback callback) {
        try {
            Call<CardListResponse> requestCall = apiInterface.cardList(param);
            requestCall.enqueue(new Callback<CardListResponse>() {

                @Override
                public void onResponse(Call<CardListResponse> call, Response<CardListResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<CardListResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void breedListAPI(BreedListRequestModel param, final ResponseCallback callback) {
        try {
            Call<BreedListResponseModel> requestCall = apiInterface.breedList(param);
            requestCall.enqueue(new Callback<BreedListResponseModel>() {

                @Override
                public void onResponse(Call<BreedListResponseModel> call, Response<BreedListResponseModel> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<BreedListResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCardAPI(DeleteCardRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.deleteCard(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void partnerListAPI(PartnerListRequest param, final ResponseCallback callback) {
        try {
            Call<PartnerListResponse> requestCall = apiInterface.partnerList(param);
            requestCall.enqueue(new Callback<PartnerListResponse>() {

                @Override
                public void onResponse(Call<PartnerListResponse> call, Response<PartnerListResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<PartnerListResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePasswordAPI(ChangePasswordRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.changePassword(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDogDetailsAPI(GetDogDetailsRequest param, final ResponseCallback callback) {
        try {
            Call<GetDogDetailsResponse> requestCall = apiInterface.getDogDetails(param);
            requestCall.enqueue(new Callback<GetDogDetailsResponse>() {

                @Override
                public void onResponse(Call<GetDogDetailsResponse> call, Response<GetDogDetailsResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<GetDogDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void favouriteOrUnfavouriteDogAPI(FavouriteUnFavDogRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.favouriteOrUnfavouriteDog(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareYourExperienceAPI(ShareYourExperienceRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.shareYourExperience(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logoutAPI(LogoutRequest param, final ResponseCallback callback) {
        try {
            Call<ApiResponse> requestCall = apiInterface.logout(param);
            requestCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cardDetailAPI(CardDetailRequest param, final ResponseCallback callback) {
        try {
            Call<CardDetailResponse> requestCall = apiInterface.cardDetail(param);
            requestCall.enqueue(new Callback<CardDetailResponse>() {

                @Override
                public void onResponse(Call<CardDetailResponse> call, Response<CardDetailResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<CardDetailResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void favouriteDogListAPI(ApiRequest param, final ResponseCallback callback) {
        try {
            Call<FavouriteDogListResponse> requestCall = apiInterface.getFavouriteDogList(param);
            requestCall.enqueue(new Callback<FavouriteDogListResponse>() {

                @Override
                public void onResponse(Call<FavouriteDogListResponse> call, Response<FavouriteDogListResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<FavouriteDogListResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDogListAPI(HomeDataRequest param, final ResponseCallback callback) {
        try {
            Call<HomeDataResponse> requestCall = apiInterface.getDogList(param);
            requestCall.enqueue(new Callback<HomeDataResponse>() {

                @Override
                public void onResponse(Call<HomeDataResponse> call, Response<HomeDataResponse> response) {
                    callback.ResponseSuccessCallBack(response.body());
                }

                @Override
                public void onFailure(Call<HomeDataResponse> call, Throwable t) {
                    t.printStackTrace();
                    callback.ResponseFailCallBack(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


