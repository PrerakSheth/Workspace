package com.konkr.Webservices;

public class WebField {
    // Development Url
    public final static String BASE_URL = "http://dev2.ifuturz.com/core/konkr/webservice/";

    //Live URL
//    public final static String BASE_URL = "http://konkr.clouddownunder.com.au/webservice/";

    // QA Url
//    public final static String BASE_URL = "http://dev2.ifuturz.com/core/konkrqa/webservice/";

    public final static String STATUS = "status";
    public final static String MESSAGE = "message";
    public final static String TOKENEXPIRED = "tokenExpired";

    public final static String DEVICE_TYPE = "deviceType";
    public final static String DEVICE_TOKEN = "deviceToken";
    public final static String SPOTIFY_TOKEN = "spotifyToken";
    public final static String SPOTIFY_ID = "spotifyId";
    public final static String IS_SUBSCRIBED = "isSubscribed";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_OTHERUSERID = "otherUserId";
    public static final String BADGE = "badge";
    public static final String PARAM_ACCESSTOKEN = "accessToken";

    public final static String BADGE_STATUS = "badgeStatus";
    public static final String PARAM_PAGEINDEX = "pageIndex";
    public static final String PARAM_PAGECOUNT = "pageCount";
    public static final String PARAM_HOMEFEEDID = "homeFeedId";

    public static final class LOGIN {
        public static final String MODE = "user/Login";
        public static final String PARAM_EMAIL_ID = "email";
        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_DEVICE_TOKEN = "deviceToken";
        public static final String PARAM_DEVICE_TYPE = "deviceType";
        public static final String PARAM_COUNTRYID = "countryId";
        public static final String PARAM_FB_ACCESS_TOKEN = "fbAccessToken";
        public static final String PARAM_LOGIN_TYPE = "loginType";
    }

    public static final class IS_FB_LOGIN {
        public static final String MODE = "user/isFbUserAvailable";
        public static final String PARAM_EMAIL_ID = "email";
        public static final String PARAM_USERAVAILABLE = "userAvailable";
    }

    public static final class SIGN_UP {
        public static final String MODE = "user/SignUp";
        public static final String PARAM_EMAIL_ID = "email";
        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_DEVICE_TOKEN = "deviceToken";
        public static final String PARAM_DEVICE_TYPE = "deviceType";
        public static final String PARAM_COUNTRYID = "countryId";
        public static final String PARAM_FB_ACCESS_TOKEN = "fbAccessToken";
        public static final String PARAM_LOGIN_TYPE = "loginType";
    }

    public static final class COUNTRY_LIST {
        public static final String MODE = "user/CountryList";
    }

    public static final class ADVERTISEMENT {
        public static final String MODE = "user/getAdvertiseList";
        public static final String PARAM_SCREENPOSITION = "screenPosition";
    }

    public static final class FORGOT_PASSWORD {
        public static final String MODE = "user/ForgotPassword";
        public static final String PARAM_EMAIL_ID = "email";
        public static final String PARAM_DEVICE_TYPE = "deviceType";
    }

    public static final class GET_USER_DETAILS {
        public static final String MODE = "user/getUserDetails";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String OTHER_USER_ID = "otherUserId";
    }

    public static final class UPLOAD_USER_VIDEO {
        public static final String MODE = "user/uploadUserVideo";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String IS_COMPLETED = "isComleted";
        public static final String WORKOUT_SOURCE_DATA = "workoutSourceData";
        public static final String ITEM_THUMB = "itemThumb";
        public static final String IS_EDIT = "isEdit";
    }

    public static final class SET_UP_PROFILE {
        public static final String MODE = "user/SetUpProfile";
        public static final String PARAM_USERID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_PROFILEPIC = "profilePic";
        public static final String PARAM_FIRSTNAME = "firstName";
        public static final String PARAM_LASTNAME = "lastName";
        public static final String PARAM_NICKNAME = "nickName";
        public static final String PARAM_DOB = "dob";
        public static final String PARAM_GENDER = "gender";
        public static final String PARAM_CURRENTTRAININGGOALS = "currentTrainingGoals";
        public static final String PARAM_BODYTYPE = "bodyType";
        public static final String PARAM_WEIGHT = "weight";
        public static final String PARAM_HEIGHT = "height";
        public static final String PARAM_BODYFAT = "bodyFat";
        public static final String PARAM_BIO = "bio";
        public static final String PARAM_SOCIALLINKS = "socialLinks";
        public static final String PARAM_FB = "FB";
        public static final String PARAM_INSTAGRAM = "Instagram";
        public static final String PARAM_SNAPCHAT = "Snapchat";
        public static final String PARAM_TWITTER = "Twitter";
        public static final String PARAM_YOUTUBE = "Youtube";
        public static final String PARAM_LINKEDIN = "LinkedIn";

        public static final String PARAM_PHOTOS = "photos";
        public static final String PARAM_PHOTOS1 = "photo1";
        public static final String PARAM_PHOTOS2 = "photo2";
        public static final String PARAM_PHOTOS3 = "photo3";
        public static final String PARAM_PHOTOS4 = "photo4";
        public static final String PARAM_PHOTOS5 = "photo5";
        public static final String PARAM_PHOTOS6 = "photo6";
        public static final String PARAM_PHOTOS7 = "photos7";
        public static final String PARAM_PHOTOS8 = "photos8";
        public static final String PARAM_PHOTOS9 = "photos9";
    }

    public static final class ADD_EXTRA_PHOTO {
        public static final String MODE = "user/GetAddPhotoToProfile";
        public static final String PARAM_USERID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_BASE64 = "base64";
        public static final String PARAM_PHOTO_POSITION = "photoPosition";
        public static final String PARAM_MEDIA_ID = "mediaId";
        public static final String PARAM_PROFILE_ID = "profileId ";
    }

    public static final class GET_CARD_LIST {
        public static final String MODE = "user/GetCardList";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
    }

    public static final class SET_UP_CARD {
        public static final String MODE = "user/SetUpCard";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_CARD_ID = "cardId";
        public static final String PARAM_CARD_NAME = "cardName";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_CARD_NO = "cardNo";
        public static final String PARAM_EXPIRY = "expiry";
        public static final String PARAM_CVV = "cvv";
        public static final String PARAM_EXPIRYDATETIME = "expiryDateTime";
    }

    public static final class DELETE_CARD {
        public static final String MODE = "user/DeleteCard";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_CARD_ID = "cardId";
    }

    public static final class DONATE_US {
        public static final String MODE = "user/DonateUs";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_CARD_ID = "cardId";
        public static final String PARAM_CVV = "cvv";
        public static final String PARAM_AMOUNT = "amount";
    }

    public static final class SHARE_FEEDBACK {
        public static final String MODE = "user/ShareFeedback";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_TITLE = "title";
        public static final String PARAM_EXPERIENCE_DETAILS = "experienceDetails";
        public static final String PARAM_SUGGESTIONS_FOR_FUTURE = "suggestionsForFuture";
        public static final String PARAM_OSDETAIL = "osDetail";
        public static final String PARAM_MOBILENAME = "mobileName";
        public static final String PARAM_APPVERSION = "appVersion";
        public static final String PARAM_EMAIL = "email";
    }

    public static final class CHANGE_PASSWORD {
        public static final String MODE = "user/ChangePassword";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_CURRENT_PASSWORD = "currentPass";
        public static final String PARAM_NEW_PASSWORD = "newPass";
    }

    public static final class GET_SUGGESTED_TRAINING_GOALS {
        public static final String MODE = "user/getSuggestedTrainingGoals";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String SEARCHED_TRAINING_GOALS = "searchedTrainingGoals";
    }

    public static final class GET_PARTNER_LIST {
        public static final String MODE = "user/getPartnerList";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
    }

    public static final class GET_MY_WORKOUT {
        public static final String MODE = "workout/getMyworkouts";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
    }

    public static final class GET_WORKOUT_CATEGORY {
        public static final String MODE = "workout/GetWorkoutCategory";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
    }

    public static final class ADD_WORKOUT {
        public static final String MODE = "workout/addworkout";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String WORKOUT_CATEGORY_ID = "workoutCategoryId";
        public static final String WORKOUT_CATEGORY_NAME = "workoutCategoryName";
        public static final String EXCERCISE_ID = "excerciseId";
        public static final String EXCERCISE_NAME = "excerciseName";
        public static final String WORKOUT_NAME = "workoutName";
        public static final String WORKOUT_DURATION = "workoutDuration";
        public static final String HOUR = "Hour";
        public static final String MIN = "Min";
    }

    public static final class REMOVE_WORKOUT_MEDIA {
        public static final String MODE = "workout/removeWorkoutMedia";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
    }

    public static final class ADD_WORKOUT_MEDIA {
        public static final String MODE = "workout/addworkoutmedia";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String ITEM_ID = "itemId";
        public static final String MEDIA_TYPE = "mediaType";
        public static final String IS_COMPLETED = "isComleted";
        public static final String ITEM_THUMB = "itemThumb";
        public static final String WORKOUT_SOURCE_DATA = "workoutSourceData";
    }

    public static final class GET_EXCERCISE_LIST {
        public static final String MODE = "workout/GetExcerciseList";
        public static final String USER_ID = "userId";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String WORKOUT_CATEGORY_ID = "workoutCategoryId";
        public static final String SEARCH_BY = "searchBy";
    }

    public static final class REQUEST_FOR_BADGE {
        public static final String MODE = "user/RequestBadge";
    }

    public static final class GET_CURRENT_BADGE {
        public static final String MODE = "user/GetCurrentBadge";
    }

    public static final class LOG_OUT {
        public static final String MODE = "user/Logout";
    }

    public static final class SEARCH_USER {
        public static final String MODE = "user/GetUserSearchList";
        public static final String PARAM_USER_ID = "userId";
        public static final String PARAM_ACCESSTOKEN = "accessToken";
        public static final String PARAM_GENDER = "gender";
        public static final String PARAM_BODYTYPE = "bodyType";
        public static final String PARAM_CURRENTTRAININGGOALS = "currentTrainingGoals";
        public static final String PARAM_NICKNAME = "nickName";
        public static final String PARAM_EMAIL_ID = "email";
        public static final String PARAM_BADGE = "badge";
        public static final String PARAM_NAME = "name";
        public static final String PARAM_COUNTRYID = "countryId";
        public static final String PARAM_PAGEINDEX = "pageIndex";
        public static final String PARAM_PAGECOUNT = "pageCount";
    }

    public static final class GET_MY_SUPPLEMENTS {
        public static final String MODE = "meal/getMysupplements";
    }

    public static final class ADD_SUPPLEMENT {
        public static final String MODE = "meal/addsupplement";
        public static final String SUPP_NAME = "suppName";
        public static final String SUPP_DETAILS = "suppDetails";
        public static final String SUPP_PHOTO = "suppPhoto";
        public static final String SUPP_RESOURCE_ID = "resourceId";
    }

    public static final class DELETE_SUPPLEMENT {
        public static final String MODE = "meal/deleteSupplement";
        public static final String SUPP_ID = "suppId";
    }

    public static final class DELETE_WORKOUT {
        public static final String MODE = "workout/deleteWorkout";
        public static final String WORKOUTID = "workoutId";
    }

    public static final class TAG_USER {
        public static final String MODE = "user/searchTagUsers";
        public static final String USERNAME = "userName";
    }

    public static final class SEARCH_SUPPLEMENT {
        public static final String MODE = "meal/searchsupplement";
        public static final String FOOD_NAME = "foodName";
    }

    public static final class SEARCH_MEAL {
        public static final String MODE = "meal/searchMeal";
        public static final String FOOD_NAME = "foodName";
    }

    public static final class ADD_MEAL {
        public static final String MODE = "meal/addmeal";
        public static final String PARAM_MEALPHOTO = "mealPhoto";
        public static final String PARAM_MEALTYPE = "mealType";
        public static final String PARAM_MEALS = "meals";
        public static final String PARAM_FOODNAME = "foodName";
        public static final String PARAM_UNIT = "unit";
        public static final String PARAM_QUANTITY = "quantity";
        public static final String PARAM_RESOURCEID = "resourceId";
        public static final String PARAM_MEAL_ID = "mealId";
        public static final String PARAM_MEAL_NAME = "mealName";
        public static final String PARAM_ACTION = "action";
        public static final String PARAM_HOMEFEED_ID = "homeFeedId";
    }

    //    {
//        "userId": 71,
//            "accessToken": "QQdKyZ50jiRayk5GoEENhuh3ybWoJ5CAVQltPqt8JljiFoY33D",
//            "otherUserId": 72
//    }
    public static final class USER_DETAILS {
        public static final String MODE = "user/getUserDetails";
        public static final String PARAM_OTHERUSERID = "otherUserId";

    }

//    http://dev2.ifuturz.com/core/konkr/webservice/user/getCheckQrCode
//
//    {
//        "userId": 71,
//            "accessToken": "QQdKyZ50jiRayk5GoEENhuh3ybWoJ5CAVQltPqt8JljiFoY33D",
//            "otherUserId": 72
//    }

    public static final class QR_CODE {
        public static final String MODE = "user/getCheckQrCode";
        public static final String PARAM_OTHERUSERID = "otherUserId";

    }
//    {
//        "userId": 71,
//            "accessToken": "QQdKyZ50jiRayk5GoEENhuh3ybWoJ5CAVQltPqt8JljiFoY33D",
//            "otherUserId": 72,
//            "followOrUnfollow": 1
//    }

    public static final class FOLLOW_UNFOLLOW {
        public static final String MODE = "user/FollowUnFollowUser";
        public static final String PARAM_FOLLOWORUNFOLLOW = "followOrUnfollow";
        public static final String PARAM_OTHERUSERID = "otherUserId";
    }

    public static final class FOLLOWERS_LIST {
        public static final String MODE = "user/getFollowersList";
    }

    public static final class FOLLOWING_LIST {
        public static final String MODE = "user/GetFollowingList";
    }

    public static final class HOME_FEED {
        public static final String MODE = "user/getHomeFeed";
    }

    public static final class GET_COMMENT {
        public static final String MODE = "user/getCommentsList";
        public static final String PARAM_HOMEFEEDID = "homeFeedId";
    }

    public static final class GET_COMMENT_ON_MEDIA {
        public static final String MODE = "workout/getCommentOnTrainingMedia";
        public static final String PARAM_HOMEFEEDID = "homeFeedId";

        public static final String PARAM_ITEM_ID = "itemId";
        public static final String PARAM_WORKOUTID = "workoutId";
        public static final String PARAM_PROFILE_ID = "profileId";
    }

    public static final class ADD_COMMENT_ON_MEDIA {
        public static final String MODE = "workout/AddCommentOnTrainingMedia";
        public static final String PARAM_HOMEFEEDID = "homeFeedId";

        public static final String PARAM_ITEM_ID = "itemId";
        public static final String PARAM_WORKOUTID = "workoutId";
        public static final String PARAM_PROFILE_ID = "profileId";
        public static final String PARAM_COMMENT = "comment";
    }

    public static final class SEND_COMMENT {
        public static final String MODE = "user/givecommentOnFeed";
        public static final String PARAM_HOMEFEEDID = "homeFeedId";
        public static final String PARAM_COMMENT = "comment";
    }

    public static final class GIVE_EXPRESSION_ON_FEED {
        public static final String MODE = "user/giveExpressionOnFeed";
        public static final String PARAM_EXPRESSION = "expression";
    }

    public static final class GET_MY_MEALS {
        public static final String MODE = "meal/getMyMeals";
        public static final String PARAM_EXPRESSION = "expression";
    }

    public static final class DELETE_MY_MEALS {
        public static final String MODE = "meal/deleteMyMeals";
        public static final String MEAL_TYPE = "mealType";
        public static final String MEAL_ID = "mealId";
    }

    public static final class GET_EXPRESSION_LIST {
        public static final String MODE = "user/getExpressionList";
        public static final String MODE_ON_TRAINING_MEDIA = "workout/getexpressionOnTrainingMedia";

        public static final String HOME_FEED_ID = "homeFeedId";
        public static final String PROFILE_ID = "profileId";
        public static final String ITEM_ID = "itemId";
        public static final String WORKOUTID = "workoutId";
    }

    public static final class GET_SUBSCRIPTION_LISTS {
        public static final String MODE = "user/getSubscriptionLists";
    }

    public static final class PURCHASE_SUBSCRIPTION {
        public static final String MODE = "user/purchaseSubscription";
        public static final String SUBSCRIPTION_ID = "subscriptionId";
    }

    public static final class GET_NOTIFICATION_LIST {
        public static final String MODE = "user/getNotificationList";
    }

    public static final class GET_PLAYLIST {
        //  https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "https://api.spotify.com/v1/playlists/";

        public static final String USER_ID = "user_id";
        public static final String AUTHORIZATION = "Authorization";
        public static final String PLAYLISTS = "playlists";

    }

    public static final class GET_COMMENT_MEDIA {
        //  https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "user/getCommentMedia";

        public static final String MEDIA_ID = "mediaId";
        public static final String COMMENT_TYPE = "commentType";
        public static final String PROFILE_ID = "profileId";

    }

    public static final class ADD_COMMENT_MEDIA {
        //https://api.spotify.com/v1/users/{user_id}/playlists

        public static final String MODE = "user/addCommentMedia";
        public static final String MEDIA_ID = "mediaId";
        public static final String COMMENT = "comment";
        public static final String COMMENT_TYPE = "commentType";
        public static final String PROFILE_ID = "profileId";

    }

    public static final class GET_EXPRESSION_MEDIA {
        //https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "user/getExpressionMedia";
        public static final String MEDIA_ID = "mediaId";
        public static final String EXPRESSION_FOR = "expressionFor";
        public static final String PROFILE_ID = "profileId";

    }

    public static final class ADD_EXPRESSION_ON_INDIVIDUAL_MEDIA {
        //https://api.spotify.com/v1/users/{user_id}/playlists

        public static final String MODE = "workout/addexpressionOnTrainingMedia";
        public static final String ITEM_ID = "itemId";
        public static final String HOME_FEED_ID = "homeFeedId ";
        public static final String WORKOUTID = "workoutId";
        public static final String EXPRESSION = "expression";
        public static final String PROFILE_ID = "profileId";

    }

    public static final class ADD_EXPRESSION_USERMEDIA {
        //https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "user/addexpressionUserMedia";
        public static final String MEDIA_ID = "mediaId";
        public static final String EXPRESSION = "expression";
        public static final String EXPRESSION_FOR = "expressionFor";
        public static final String PROFILE_ID = "profileId";

    }

    public static final class SUBSCRIBEFORUSER {
        //https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "user/SubscribeUnSubscribe";
        public static final String SUBSCRIBEORUNSUBSCRIBE = "SubscribeOrUnSubscribe";

    }


    public static final class ADDSPOTIFYUSER {
        //https://api.spotify.com/v1/users/{user_id}/playlists
        public static final String MODE = "user/addSpotifyUser";
        public static final String USER_ID = "userId";
        public static final String SPOTIFY_USER_ID = "spotifyUserId";

    }
}