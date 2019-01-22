package com.konkr.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.konkr.Activities.CommentsActivity;
import com.konkr.Activities.FollowersFollowingActivity;
import com.konkr.Activities.MainActivity;
import com.konkr.Models.Meals;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.MyWorkouts;
import com.konkr.Models.NotificationMeal;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogM.LogE("remoteMessage " + remoteMessage);

        // Check if message contains a data  payload.
        if (remoteMessage.getData().size() > 0) {
            LogM.LogE(TAG + "data payload: " + remoteMessage.getData().toString());
            sendNotification(remoteMessage);
        }

        if (remoteMessage.getNotification() != null) {
            LogM.LogE(TAG + "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        int notificationType = 0;
        String messageBody = "";
        String homeFeed_Id = "";
        int userId = 0;
        int expression = 0;
        int profileId = 0;
        int comment_type = 0;
        int mediaId = 0;
        int expressionFor = 0;
        ArrayList<Meals.Meal> mealArrayList = new ArrayList<>();
        ArrayList<MiSuppliment.SupplementsBean> supplementsBeanArrayList = new ArrayList<>();
        MyWorkouts.WorkoutsBean workout = null;
        MyWorkouts.WorkoutsBean.WorkoutMediaBean workoutMediaBean=null;
        UserDetails.UserDetailsBean.ImageArrayBean imageArrayBean = null;
        // ArrayList<ArrayList<Meals.Meal>> mealListArrayList=new ArrayList<>();
        try {
//               int notificationType = Integer.parseInt(remoteMessage.getData().get(GlobalData.NOTIFICATION_TYPE));
//               String messageBody = (remoteMessage.getData().get(GlobalData.MESSAGE_BODY));
//               int userId = Integer.parseInt(remoteMessage.getData().get(GlobalData.USER_Id));
//               int homeFeed_Id = Integer.parseInt(remoteMessage.getData().get(GlobalData.HOME_FEED_ID));
//               LogM.LogE("user Id " + userId + "notification Type===========>" + notificationType);

            notificationType = Integer.parseInt(remoteMessage.getData().get(GlobalData.NOTIFICATION_TYPE));
            messageBody = (remoteMessage.getData().get(GlobalData.MESSAGE_BODY));
            try {
                userId = Integer.parseInt(remoteMessage.getData().get(GlobalData.USER_Id));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                homeFeed_Id = (remoteMessage.getData().get(GlobalData.HOME_FEED_ID));
            } catch (Exception e) {
                e.printStackTrace();
                homeFeed_Id = "";
            }

            try {
                expression = Integer.parseInt(remoteMessage.getData().get(GlobalData.EXPRESSION));
                LogM.LogE(TAG + "initex===>" + expression);
            } catch (Exception e) {
                e.printStackTrace();
                expression = 0;
            }

            try {
                comment_type = Integer.parseInt(remoteMessage.getData().get(GlobalData.COMMENT_TYPE));
            } catch (Exception e) {
                e.printStackTrace();
                comment_type = 0;
            }
            try {
                mediaId = Integer.parseInt(remoteMessage.getData().get(GlobalData.MEDIA_ID));
            } catch (Exception e) {
                e.printStackTrace();
                mediaId = 0;
            }

            try {
                expressionFor = Integer.parseInt(remoteMessage.getData().get(GlobalData.EXPRESSION_FOR));
            } catch (Exception e) {
                e.printStackTrace();
                expressionFor = 0;
            }

            try {
                profileId = Integer.parseInt(remoteMessage.getData().get(GlobalData.PROFILE_ID));
            } catch (Exception e) {
                e.printStackTrace();
                profileId = 0;
            }

            try {
                LogM.LogE(remoteMessage.getData().get("meals"));
                String response = remoteMessage.getData().get("meals");
//
                Meals.Meal[] meal = new Gson().fromJson(response, Meals.Meal[].class);
                mealArrayList.addAll(Arrays.asList(meal));
                LogM.LogE("size not" + mealArrayList.size());
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                LogM.LogE(remoteMessage.getData().get("supplements"));
                String response = remoteMessage.getData().get("supplements");
//
                MiSuppliment.SupplementsBean supp = new Gson().fromJson(response, MiSuppliment.SupplementsBean.class);
                supplementsBeanArrayList.add(supp);
                LogM.LogE("suppliment Size" + supplementsBeanArrayList.size());
            } catch (Exception e) {
                e.printStackTrace();

            }

            try {
                LogM.LogE(remoteMessage.getData().get("workouts"));
                String response = remoteMessage.getData().get("workouts");
//
                workout = new Gson().fromJson(response, MyWorkouts.WorkoutsBean.class);
                // supplementsBeanArrayList.add(workout);
                LogM.LogE("workout" + workout);
            } catch (Exception e) {
                e.printStackTrace();

            }



            try {
                LogM.LogE(remoteMessage.getData().get("media"));
                String response = remoteMessage.getData().get("media");
//
                imageArrayBean = new Gson().fromJson(response, UserDetails.UserDetailsBean.ImageArrayBean.class);
                // supplementsBeanArrayList.add(workout);
                LogM.LogE("imageArrayBean" + imageArrayBean);
            } catch (Exception e) {
                e.printStackTrace();

            }

//            try {
//                LogM.LogE(remoteMessage.getData().get("workoutMedia"));
//                String response = remoteMessage.getData().get("workoutMedia");
////
//                workoutMediaBean = new Gson().fromJson(response, MyWorkouts.WorkoutsBean.WorkoutMediaBean.class);
//                // supplementsBeanArrayList.add(workout);
//                LogM.LogE("workoutMediaBean" + workoutMediaBean);
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }


            Intent intent = new Intent(this, MainActivity.class);
            switch (notificationType) {

                case GlobalData.FOLLOWING_YOU: //1
                    intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWING_YOU_NOTIFICATION);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.FOLLOWING_YOU);
                    break;

                case GlobalData.ADD_MEAL: //2

                    intent.putExtra(GlobalData.FROM, GlobalData.ADD_MEAL_NOTIFICATION);
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADD_MEAL);
                    intent.putExtra(GlobalData.MEALARRAY, mealArrayList);
                    break;

                case GlobalData.ADD_WORKOUT: //3
                    intent.putExtra(GlobalData.FROM, GlobalData.ADD_WORKOUT_NOTIFICATION);
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADD_WORKOUT);
                    intent.putExtra(GlobalData.WORKOUT, workout);
                    intent.putExtra("WorkoutDuration", workout.getWorkoutDuration());
                    intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workout.getWorkoutMedia());
                    break;
                case GlobalData.ADD_SUPPLEMENT: //4
                    intent.putExtra(GlobalData.FROM, GlobalData.ADD_SUPPLEMENT_NOTIFICATION);
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADD_SUPPLEMENT);
                    intent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList);
                    break;
                case GlobalData.GIVE_EXPRESSIONON_FEED: //5
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_Id);
                    intent.putExtra(GlobalData.EXPRESSION, expression);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.GIVE_EXPRESSIONON_FEED);
                    if (workout != null) {
                        intent.putExtra(GlobalData.WORKOUT, workout);
                        intent.putExtra("WorkoutDuration", workout.getWorkoutDuration());
                        intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workout.getWorkoutMedia());
                    } else if (supplementsBeanArrayList.size() > 0) {
                        intent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList);
                    } else if (mealArrayList.size() > 0) {
                        intent.putExtra(GlobalData.MEALARRAY, mealArrayList);
                    }
                    LogM.LogE(TAG + "homeFeed_Id===>" + homeFeed_Id);
                    break;
                case GlobalData.COMMENTS: //6
                    intent.putExtra(GlobalData.FROM, GlobalData.COMMENTS_NOTIFICATION);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_Id);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.COMMENTS);
                    if (workout != null) {
                        intent.putExtra(GlobalData.WORKOUT, workout);
                        intent.putExtra("WorkoutDuration", workout.getWorkoutDuration());
                        intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workout.getWorkoutMedia());
                    } else if (supplementsBeanArrayList.size() > 0) {
                        intent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList);
                    } else if (mealArrayList.size() > 0) {
                        intent.putExtra(GlobalData.MEALARRAY, mealArrayList);
                    }
                    break;
                case GlobalData.VARIFICATION_BADGE: //7
                    intent.putExtra(GlobalData.FROM, GlobalData.VARIFICATION_BADGE_NOTIFICATION);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_Id);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.VARIFICATION_BADGE);
                    break;


                case GlobalData.ADDEXPRESSIONUSERMEDIA: //10
                    intent.putExtra(GlobalData.FROM, GlobalData.MEDIA_COMMENT_NOTIFICATION);
                    intent.putExtra(GlobalData.USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADDEXPRESSIONUSERMEDIA);
                    intent.putExtra(GlobalData.EXPRESSION_FOR, expressionFor);
                    intent.putExtra(GlobalData.EXPRESSION, expression);
                    intent.putExtra(GlobalData.MEDIA_ID, mediaId);
                    intent.putExtra(GlobalData.PROFILE_ID, profileId);
                    intent.putExtra(GlobalData.USER_ID, userId);
                    intent.putExtra(GlobalData.FROM, GlobalData.ALBUM);
                    intent.putExtra(GlobalData.Array_List, imageArrayBean);
                    break;

                case GlobalData.ADDCOMMENTMEDIA: //11
                    intent.putExtra(GlobalData.FROM, GlobalData.MEDIA_COMMENT_NOTIFICATION);
                    intent.putExtra(GlobalData.USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADDCOMMENTMEDIA);
                    intent.putExtra(GlobalData.COMMENT_TYPE, comment_type);
                    intent.putExtra(GlobalData.EXPRESSION_FOR, expressionFor);
                    intent.putExtra(GlobalData.EXPRESSION, expression);
                    intent.putExtra(GlobalData.MEDIA_ID, mediaId);
                    intent.putExtra(GlobalData.PROFILE_ID, profileId);
                    intent.putExtra(GlobalData.Array_List, imageArrayBean);
                    break;


                case GlobalData.SUBSCRIBE:  //12
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.SUBSCRIBE);
                    break;


                case GlobalData.ADD_EXPRESSION_ON_TRAINING_MEDIA:  //14
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADD_EXPRESSION_ON_TRAINING_MEDIA);
                    intent.putExtra(GlobalData.WORKOUT, workout);
                    intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workout.getWorkoutMedia());
                    intent.putExtra(GlobalData.EXPRESSION_FOR, expressionFor);
                    intent.putExtra(GlobalData.EXPRESSION, expression);
                    intent.putExtra(GlobalData.PROFILE_ID, profileId);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_Id);
                    intent.putExtra(GlobalData.USER_ID, userId);
                    break;

                case GlobalData.ADD_COMMENT_ON_TRAININGMEDIA:  //13
                    intent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    intent.putExtra(GlobalData.NOTIFICATION_TYPE, GlobalData.ADD_COMMENT_ON_TRAININGMEDIA);
                    intent.putExtra(GlobalData.WORKOUT, workout);
                    intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workout.getWorkoutMedia());
                    break;

            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationType, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
//                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher_foreground))     //large icon
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(notificationType, notificationBuilder.build());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.ic_notification : R.mipmap.ic_launcher_foreground;
    }
}



