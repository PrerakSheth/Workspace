package com.konkr.Models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android on 6/25/2018.
 */

public class HomeFeed {

    /*
     * message : Home feed found successfully.
     * status : 1
     * homeFeeds : [{"userId":"46","homeFeedId":"277","profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/46.png","firstName":"John","lastName":"Abesta","expressionCount":0,"commentCount":0,"badge":"1","supplements":{"suppId":183,"suppName":"Caramel Apple","suppDetails":"Hi how are you","suppPhoto":"http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/183.png"},"mealsBreakfast":[{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}],"mealsLunch":[{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}],"mealsSnacks":[{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}],"mealsDinner":[{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}],"workouts":{"workoutId":39,"workoutName":"Ja","workoutCategoryId":8,"workoutCategoryName":"Arms","excerciseId":0,"excerciseName":"ha","workoutDuration":{"Hour":8,"Min":45},"workoutMedia":[{"itemId":"59","mediaType":"2","videoThumbImage":"","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/59.png"}]},"is_goals":0,"is_inspiring":0,"is_admiring":0}]
     */

    private String message;
    private int status;
    private List<HomeFeedsBean> homeFeeds;
    private int totalRecords;
    private int notificationCount;

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HomeFeedsBean> getHomeFeeds() {
        return homeFeeds;
    }

    public void setHomeFeeds(List<HomeFeedsBean> homeFeeds) {
        this.homeFeeds = homeFeeds;
    }

    @SuppressLint("ParcelCreator")
    public static class HomeFeedsBean implements Parcelable {
        /**
         * userId : 46
         * homeFeedId : 277
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/46.png
         * firstName : John
         * lastName : Abesta
         * expressionCount : 0
         * commentCount : 0
         * badge : 1
         * supplements : {"suppId":183,"suppName":"Caramel Apple","suppDetails":"Hi how are you","suppPhoto":"http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/183.png"}
         * mealsBreakfast : [{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}]
         * mealsLunch : [{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}]
         * mealsSnacks : [{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}]
         * mealsDinner : [{"mealId":197,"mealPhoto":"","mealType":2,"foodName":"Egg Twists","unit":"roll","quantity":"66"}]
         * workouts : {"workoutId":39,"workoutName":"Ja","workoutCategoryId":8,"workoutCategoryName":"Arms","excerciseId":0,"excerciseName":"ha","workoutDuration":{"Hour":8,"Min":45},"workoutMedia":[{"itemId":"59","mediaType":"2","videoThumbImage":"","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/59.png"}]}
         * is_goals : 0
         * is_inspiring : 0
         * is_admiring : 0
         */

        private String userId;
        private String homeFeedId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private int expressionCount;
        private int commentCount;
        private String badge;
        private SupplementsBean supplements;
        private WorkoutsBean workouts;
        private int is_goals;
        private int is_inspiring;
        private int is_admiring;
        private List<MealsBreakfastBean> mealsBreakfast;
        private List<MealsLunchBean> mealsLunch;
        private List<MealsSnacksBean> mealsSnacks;
        private List<MealsDinnerBean> mealsDinner;
        private List<List<OtherMealDataBean>> otherMealData;

        protected HomeFeedsBean(Parcel in) {
            userId = in.readString();
            homeFeedId = in.readString();
            profilePic = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            expressionCount = in.readInt();
            commentCount = in.readInt();
            badge = in.readString();
            supplements = in.readParcelable(SupplementsBean.class.getClassLoader());
            workouts = in.readParcelable(WorkoutsBean.class.getClassLoader());
            is_goals = in.readInt();
            is_inspiring = in.readInt();
            is_admiring = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userId);
            dest.writeString(homeFeedId);
            dest.writeString(profilePic);
            dest.writeString(firstName);
            dest.writeString(lastName);
            dest.writeInt(expressionCount);
            dest.writeInt(commentCount);
            dest.writeString(badge);
            dest.writeParcelable(supplements, flags);
            dest.writeParcelable(workouts, flags);
            dest.writeInt(is_goals);
            dest.writeInt(is_inspiring);
            dest.writeInt(is_admiring);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<HomeFeedsBean> CREATOR = new Creator<HomeFeedsBean>() {
            @Override
            public HomeFeedsBean createFromParcel(Parcel in) {
                return new HomeFeedsBean(in);
            }

            @Override
            public HomeFeedsBean[] newArray(int size) {
                return new HomeFeedsBean[size];
            }
        };

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getHomeFeedId() {
            return homeFeedId;
        }

        public void setHomeFeedId(String homeFeedId) {
            this.homeFeedId = homeFeedId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getExpressionCount() {
            return expressionCount;
        }

        public void setExpressionCount(int expressionCount) {
            this.expressionCount = expressionCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public SupplementsBean getSupplements() {
            return supplements;
        }

        public void setSupplements(SupplementsBean supplements) {
            this.supplements = supplements;
        }

        public WorkoutsBean getWorkouts() {
            return workouts;
        }

        public void setWorkouts(WorkoutsBean workouts) {
            this.workouts = workouts;
        }

        public int getIs_goals() {
            return is_goals;
        }

        public void setIs_goals(int is_goals) {
            this.is_goals = is_goals;
        }

        public int getIs_inspiring() {
            return is_inspiring;
        }

        public void setIs_inspiring(int is_inspiring) {
            this.is_inspiring = is_inspiring;
        }

        public int getIs_admiring() {
            return is_admiring;
        }

        public void setIs_admiring(int is_admiring) {
            this.is_admiring = is_admiring;
        }

        public List<MealsBreakfastBean> getMealsBreakfast() {
            return mealsBreakfast;
        }

        public void setMealsBreakfast(List<MealsBreakfastBean> mealsBreakfast) {
            this.mealsBreakfast = mealsBreakfast;
        }

        public List<MealsLunchBean> getMealsLunch() {
            return mealsLunch;
        }

        public void setMealsLunch(List<MealsLunchBean> mealsLunch) {
            this.mealsLunch = mealsLunch;
        }

        public List<MealsSnacksBean> getMealsSnacks() {
            return mealsSnacks;
        }

        public void setMealsSnacks(List<MealsSnacksBean> mealsSnacks) {
            this.mealsSnacks = mealsSnacks;
        }

        public List<MealsDinnerBean> getMealsDinner() {
            return mealsDinner;
        }

        public void setMealsDinner(List<MealsDinnerBean> mealsDinner) {
            this.mealsDinner = mealsDinner;
        }

        public List<List<OtherMealDataBean>> getOtherMealData() {
            return otherMealData;
        }

        public void setOtherMealData(List<List<OtherMealDataBean>> otherMealData) {
            this.otherMealData = otherMealData;
        }

        public static class SupplementsBean implements Parcelable {
            /**
             * suppId : 183
             * suppName : Caramel Apple
             * suppDetails : Hi how are you
             * suppPhoto : http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/183.png
             */

            private int suppId;
            private String suppName;
            private String suppDetails;
            private String suppPhoto;

            protected SupplementsBean(Parcel in) {
                suppId = in.readInt();
                suppName = in.readString();
                suppDetails = in.readString();
                suppPhoto = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(suppId);
                dest.writeString(suppName);
                dest.writeString(suppDetails);
                dest.writeString(suppPhoto);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<SupplementsBean> CREATOR = new Creator<SupplementsBean>() {
                @Override
                public SupplementsBean createFromParcel(Parcel in) {
                    return new SupplementsBean(in);
                }

                @Override
                public SupplementsBean[] newArray(int size) {
                    return new SupplementsBean[size];
                }
            };

            public int getSuppId() {
                return suppId;
            }

            public void setSuppId(int suppId) {
                this.suppId = suppId;
            }

            public String getSuppName() {
                return suppName;
            }

            public void setSuppName(String suppName) {
                this.suppName = suppName;
            }

            public String getSuppDetails() {
                return suppDetails;
            }

            public void setSuppDetails(String suppDetails) {
                this.suppDetails = suppDetails;
            }

            public String getSuppPhoto() {
                return suppPhoto;
            }

            public void setSuppPhoto(String suppPhoto) {
                this.suppPhoto = suppPhoto;
            }
        }

        public static class WorkoutsBean implements Parcelable {
            /**
             * workoutId : 19
             * userId : 10
             * homeFeedId : 86
             * workoutName : Okk
             * workoutCategoryId : 13
             * workoutCategoryName : Shoulders
             * excerciseId : 0
             * excerciseName : uhh
             * expressionCount : 0
             * commentCount : 0
             * is_admiring : 0
             * is_goals : 0
             * is_inspiring : 0
             * workoutDuration : {"Hour":9,"Min":45}
             * workoutMedia : [{"itemId":"30","homeFeedId":"86","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/30.png","expressionCount":0,"commentCount":0,"is_admiring":0,"is_goals":0,"is_inspiring":0}]
             */

            private int workoutId;
            private int userId;
            private String homeFeedId;
            private String workoutName;
            private int workoutCategoryId;
            private String workoutCategoryName;
            private int excerciseId;
            private String excerciseName;
            private int expressionCount;
            private int commentCount;
            private int is_admiring;
            private int is_goals;
            private int is_inspiring;
            private WorkoutDurationBean workoutDuration;
            private List<WorkoutMediaBean> workoutMedia;

            protected WorkoutsBean(Parcel in) {
                workoutId = in.readInt();
                userId = in.readInt();
                homeFeedId = in.readString();
                workoutName = in.readString();
                workoutCategoryId = in.readInt();
                workoutCategoryName = in.readString();
                excerciseId = in.readInt();
                excerciseName = in.readString();
                expressionCount = in.readInt();
                commentCount = in.readInt();
                is_admiring = in.readInt();
                is_goals = in.readInt();
                is_inspiring = in.readInt();
            }

            public static final Creator<WorkoutsBean> CREATOR = new Creator<WorkoutsBean>() {
                @Override
                public WorkoutsBean createFromParcel(Parcel in) {
                    return new WorkoutsBean(in);
                }

                @Override
                public WorkoutsBean[] newArray(int size) {
                    return new WorkoutsBean[size];
                }
            };

            public int getWorkoutId() {
                return workoutId;
            }

            public void setWorkoutId(int workoutId) {
                this.workoutId = workoutId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getHomeFeedId() {
                return homeFeedId;
            }

            public void setHomeFeedId(String homeFeedId) {
                this.homeFeedId = homeFeedId;
            }

            public String getWorkoutName() {
                return workoutName;
            }

            public void setWorkoutName(String workoutName) {
                this.workoutName = workoutName;
            }

            public int getWorkoutCategoryId() {
                return workoutCategoryId;
            }

            public void setWorkoutCategoryId(int workoutCategoryId) {
                this.workoutCategoryId = workoutCategoryId;
            }

            public String getWorkoutCategoryName() {
                return workoutCategoryName;
            }

            public void setWorkoutCategoryName(String workoutCategoryName) {
                this.workoutCategoryName = workoutCategoryName;
            }

            public int getExcerciseId() {
                return excerciseId;
            }

            public void setExcerciseId(int excerciseId) {
                this.excerciseId = excerciseId;
            }

            public String getExcerciseName() {
                return excerciseName;
            }

            public void setExcerciseName(String excerciseName) {
                this.excerciseName = excerciseName;
            }

            public int getExpressionCount() {
                return expressionCount;
            }

            public void setExpressionCount(int expressionCount) {
                this.expressionCount = expressionCount;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public int getIs_admiring() {
                return is_admiring;
            }

            public void setIs_admiring(int is_admiring) {
                this.is_admiring = is_admiring;
            }

            public int getIs_goals() {
                return is_goals;
            }

            public void setIs_goals(int is_goals) {
                this.is_goals = is_goals;
            }

            public int getIs_inspiring() {
                return is_inspiring;
            }

            public void setIs_inspiring(int is_inspiring) {
                this.is_inspiring = is_inspiring;
            }

            public WorkoutsBean.WorkoutDurationBean getWorkoutDuration() {
                return workoutDuration;
            }

            public void setWorkoutDuration(WorkoutDurationBean workoutDuration) {
                this.workoutDuration = workoutDuration;
            }

            public List<WorkoutMediaBean> getWorkoutMedia() {
                return workoutMedia;
            }

            public void setWorkoutMedia(List<WorkoutMediaBean> workoutMedia) {
                this.workoutMedia = workoutMedia;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(workoutId);
                parcel.writeInt(userId);
                parcel.writeString(homeFeedId);
                parcel.writeString(workoutName);
                parcel.writeInt(workoutCategoryId);
                parcel.writeString(workoutCategoryName);
                parcel.writeInt(excerciseId);
                parcel.writeString(excerciseName);
                parcel.writeInt(expressionCount);
                parcel.writeInt(commentCount);
                parcel.writeInt(is_admiring);
                parcel.writeInt(is_goals);
                parcel.writeInt(is_inspiring);
            }

            public static class WorkoutDurationBean implements Parcelable {
                /**
                 * Hour : 9
                 * Min : 45
                 */

                private int Hour;
                private int Min;

                protected WorkoutDurationBean(Parcel in) {
                    Hour = in.readInt();
                    Min = in.readInt();
                }

                public static final Creator<WorkoutDurationBean> CREATOR = new Creator<WorkoutDurationBean>() {
                    @Override
                    public WorkoutDurationBean createFromParcel(Parcel in) {
                        return new WorkoutDurationBean(in);
                    }

                    @Override
                    public WorkoutDurationBean[] newArray(int size) {
                        return new WorkoutDurationBean[size];
                    }
                };

                public int getHour() {
                    return Hour;
                }

                public void setHour(int Hour) {
                    this.Hour = Hour;
                }

                public int getMin() {
                    return Min;
                }

                public void setMin(int Min) {
                    this.Min = Min;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeInt(Hour);
                    parcel.writeInt(Min);
                }
            }

            @SuppressLint("ParcelCreator")
            public static class WorkoutMediaBean implements Parcelable {
                /**
                 * itemId : 30
                 * homeFeedId : 86
                 * mediaType : 2
                 * url : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/30.png
                 * expressionCount : 0
                 * commentCount : 0
                 * is_admiring : 0
                 * is_goals : 0
                 * is_inspiring : 0
                 */

                private String itemId;
                private String homeFeedId;
                private String mediaType;
                private String url;
                private int expressionCount;
                private int commentCount;
                private int is_admiring;
                private int is_goals;
                private int is_inspiring;
                private String videoThumbImage;

                protected WorkoutMediaBean(Parcel in) {
                    itemId = in.readString();
                    homeFeedId = in.readString();
                    mediaType = in.readString();
                    url = in.readString();
                    expressionCount = in.readInt();
                    commentCount = in.readInt();
                    is_admiring = in.readInt();
                    is_goals = in.readInt();
                    is_inspiring = in.readInt();
                    videoThumbImage = in.readString();
                }

                public WorkoutMediaBean(String itemId,int expressionCount, int commentCount, int is_admiring, int is_goals, int is_inspiring) {
                    this.itemId = itemId;
                    this.expressionCount = expressionCount;
                    this.commentCount = commentCount;
                    this.is_admiring = is_admiring;
                    this.is_goals = is_goals;
                    this.is_inspiring = is_inspiring;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(itemId);
                    dest.writeString(homeFeedId);
                    dest.writeString(mediaType);
                    dest.writeString(url);
                    dest.writeInt(expressionCount);
                    dest.writeInt(commentCount);
                    dest.writeInt(is_admiring);
                    dest.writeInt(is_goals);
                    dest.writeInt(is_inspiring);
                    dest.writeString(videoThumbImage);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<WorkoutMediaBean> CREATOR = new Creator<WorkoutMediaBean>() {
                    @Override
                    public WorkoutMediaBean createFromParcel(Parcel in) {
                        return new WorkoutMediaBean(in);
                    }

                    @Override
                    public WorkoutMediaBean[] newArray(int size) {
                        return new WorkoutMediaBean[size];
                    }
                };

                public String getVideoThumbImage() {
                    return videoThumbImage;
                }

                public void setVideoThumbImage(String videoThumbImage) {
                    this.videoThumbImage = videoThumbImage;
                }

                public String getItemId() {
                    return itemId;
                }

                public void setItemId(String itemId) {
                    this.itemId = itemId;
                }

                public String getHomeFeedId() {
                    return homeFeedId;
                }

                public void setHomeFeedId(String homeFeedId) {
                    this.homeFeedId = homeFeedId;
                }

                public String getMediaType() {
                    return mediaType;
                }

                public void setMediaType(String mediaType) {
                    this.mediaType = mediaType;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getExpressionCount() {
                    return expressionCount;
                }

                public void setExpressionCount(int expressionCount) {
                    this.expressionCount = expressionCount;
                }

                public int getCommentCount() {
                    return commentCount;
                }

                public void setCommentCount(int commentCount) {
                    this.commentCount = commentCount;
                }

                public int getIs_admiring() {
                    return is_admiring;
                }

                public void setIs_admiring(int is_admiring) {
                    this.is_admiring = is_admiring;
                }

                public int getIs_goals() {
                    return is_goals;
                }

                public void setIs_goals(int is_goals) {
                    this.is_goals = is_goals;
                }

                public int getIs_inspiring() {
                    return is_inspiring;
                }

                public void setIs_inspiring(int is_inspiring) {
                    this.is_inspiring = is_inspiring;
                }
            }
        }

//        public static class WorkoutsBean implements Parcelable {
//            /**
//             * workoutId : 39
//             * workoutName : Ja
//             * workoutCategoryId : 8
//             * workoutCategoryName : Arms
//             * excerciseId : 0
//             * excerciseName : ha
//             * workoutDuration : {"Hour":8,"Min":45}
//             * workoutMedia : [{"itemId":"59","mediaType":"2","videoThumbImage":"","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/59.png"}]
//             */
//
//            private int workoutId;
//            private String workoutName;
//            private int workoutCategoryId;
//            private String workoutCategoryName;
//            private int excerciseId;
//            private String excerciseName;
//            private WorkoutDurationBean workoutDuration;
//            private List<WorkoutMediaBean> workoutMedia;
//
//            protected WorkoutsBean(Parcel in) {
//                workoutId = in.readInt();
//                workoutName = in.readString();
//                workoutCategoryId = in.readInt();
//                workoutCategoryName = in.readString();
//                excerciseId = in.readInt();
//                excerciseName = in.readString();
//                workoutDuration = in.readParcelable(WorkoutDurationBean.class.getClassLoader());
//                workoutMedia = in.createTypedArrayList(WorkoutMediaBean.CREATOR);
//            }
//
//            public static final Creator<WorkoutsBean> CREATOR = new Creator<WorkoutsBean>() {
//                @Override
//                public WorkoutsBean createFromParcel(Parcel in) {
//                    return new WorkoutsBean(in);
//                }
//
//                @Override
//                public WorkoutsBean[] newArray(int size) {
//                    return new WorkoutsBean[size];
//                }
//            };
//
//            public int getWorkoutId() {
//                return workoutId;
//            }
//
//            public void setWorkoutId(int workoutId) {
//                this.workoutId = workoutId;
//            }
//
//            public String getWorkoutName() {
//                return workoutName;
//            }
//
//            public void setWorkoutName(String workoutName) {
//                this.workoutName = workoutName;
//            }
//
//            public int getWorkoutCategoryId() {
//                return workoutCategoryId;
//            }
//
//            public void setWorkoutCategoryId(int workoutCategoryId) {
//                this.workoutCategoryId = workoutCategoryId;
//            }
//
//            public String getWorkoutCategoryName() {
//                return workoutCategoryName;
//            }
//
//            public void setWorkoutCategoryName(String workoutCategoryName) {
//                this.workoutCategoryName = workoutCategoryName;
//            }
//
//            public int getExcerciseId() {
//                return excerciseId;
//            }
//
//            public void setExcerciseId(int excerciseId) {
//                this.excerciseId = excerciseId;
//            }
//
//            public String getExcerciseName() {
//                return excerciseName;
//            }
//
//            public void setExcerciseName(String excerciseName) {
//                this.excerciseName = excerciseName;
//            }
//
//            public WorkoutDurationBean getWorkoutDuration() {
//                return workoutDuration;
//            }
//
//            public void setWorkoutDuration(WorkoutDurationBean workoutDuration) {
//                this.workoutDuration = workoutDuration;
//            }
//
//            public List<WorkoutMediaBean> getWorkoutMedia() {
//                return workoutMedia;
//            }
//
//            public void setWorkoutMedia(List<WorkoutMediaBean> workoutMedia) {
//                this.workoutMedia = workoutMedia;
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel parcel, int i) {
//                parcel.writeInt(workoutId);
//                parcel.writeString(workoutName);
//                parcel.writeInt(workoutCategoryId);
//                parcel.writeString(workoutCategoryName);
//                parcel.writeInt(excerciseId);
//                parcel.writeString(excerciseName);
//                parcel.writeParcelable(workoutDuration, i);
//                parcel.writeTypedList(workoutMedia);
//            }
//
//            public static class WorkoutDurationBean implements Parcelable {
//                /**
//                 * Hour : 8
//                 * Min : 45
//                 */
//
//                private int Hour;
//                private int Min;
//
//                protected WorkoutDurationBean(Parcel in) {
//                    Hour = in.readInt();
//                    Min = in.readInt();
//                }
//
//                @Override
//                public void writeToParcel(Parcel dest, int flags) {
//                    dest.writeInt(Hour);
//                    dest.writeInt(Min);
//                }
//
//                @Override
//                public int describeContents() {
//                    return 0;
//                }
//
//                public static final Creator<WorkoutDurationBean> CREATOR = new Creator<WorkoutDurationBean>() {
//                    @Override
//                    public WorkoutDurationBean createFromParcel(Parcel in) {
//                        return new WorkoutDurationBean(in);
//                    }
//
//                    @Override
//                    public WorkoutDurationBean[] newArray(int size) {
//                        return new WorkoutDurationBean[size];
//                    }
//                };
//
//                public int getHour() {
//                    return Hour;
//                }
//
//                public void setHour(int hour) {
//                    Hour = hour;
//                }
//
//                public int getMin() {
//                    return Min;
//                }
//
//                public void setMin(int min) {
//                    Min = min;
//                }
//            }
//
//            public static class WorkoutMediaBean implements Parcelable {
//
//                /**
//                 * itemId : 11
//                 * mediaType : 2
//                 * url : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/11.png
//                 * expressionCount : 0
//                 * commentCount : 0
//                 * is_admiring : 0
//                 * is_goals : 0
//                 * is_inspiring : 0
//                 */
//
//                private String itemId;
//                private String mediaType;
//                private String url;
//                private int expressionCount;
//                private int commentCount;
//                private int is_admiring;
//                private int is_goals;
//                private int is_inspiring;
//                private String videoThumbImage;
//
//                public String getVideoThumbImage() {
//                    return videoThumbImage;
//                }
//
//                public void setVideoThumbImage(String videoThumbImage) {
//                    this.videoThumbImage = videoThumbImage;
//                }
//
//                public WorkoutMediaBean(int expressionCount, int commentCount, int is_admiring, int is_goals, int is_inspiring) {
//                    this.expressionCount = expressionCount;
//                    this.commentCount = commentCount;
//                    this.is_admiring = is_admiring;
//                    this.is_goals = is_goals;
//                    this.is_inspiring = is_inspiring;
//                }
//
//                protected WorkoutMediaBean(Parcel in) {
//                    itemId = in.readString();
//                    mediaType = in.readString();
//                    url = in.readString();
//                    expressionCount = in.readInt();
//                    commentCount = in.readInt();
//                    is_admiring = in.readInt();
//                    is_goals = in.readInt();
//                    is_inspiring = in.readInt();
//                    videoThumbImage = in.readString();
//                }
//
//                public static final Creator<WorkoutMediaBean> CREATOR = new Creator<WorkoutMediaBean>() {
//                    @Override
//                    public WorkoutMediaBean createFromParcel(Parcel in) {
//                        return new WorkoutMediaBean(in);
//                    }
//
//                    @Override
//                    public WorkoutMediaBean[] newArray(int size) {
//                        return new WorkoutMediaBean[size];
//                    }
//                };
//
//                public String getItemId() {
//                    return itemId;
//                }
//
//                public void setItemId(String itemId) {
//                    this.itemId = itemId;
//                }
//
//                public String getMediaType() {
//                    return mediaType;
//                }
//
//                public void setMediaType(String mediaType) {
//                    this.mediaType = mediaType;
//                }
//
//                public String getUrl() {
//                    return url;
//                }
//
//                public void setUrl(String url) {
//                    this.url = url;
//                }
//
//                public int getExpressionCount() {
//                    return expressionCount;
//                }
//
//                public void setExpressionCount(int expressionCount) {
//                    this.expressionCount = expressionCount;
//                }
//
//                public int getCommentCount() {
//                    return commentCount;
//                }
//
//                public void setCommentCount(int commentCount) {
//                    this.commentCount = commentCount;
//                }
//
//                public int getIs_admiring() {
//                    return is_admiring;
//                }
//
//                public void setIs_admiring(int is_admiring) {
//                    this.is_admiring = is_admiring;
//                }
//
//                public int getIs_goals() {
//                    return is_goals;
//                }
//
//                public void setIs_goals(int is_goals) {
//                    this.is_goals = is_goals;
//                }
//
//                public int getIs_inspiring() {
//                    return is_inspiring;
//                }
//
//                public void setIs_inspiring(int is_inspiring) {
//                    this.is_inspiring = is_inspiring;
//                }
//
//                @Override
//                public int describeContents() {
//                    return 0;
//                }
//
//                @Override
//                public void writeToParcel(Parcel dest, int flags) {
//                    dest.writeString(itemId);
//                    dest.writeString(mediaType);
//                    dest.writeString(url);
//                    dest.writeInt(expressionCount);
//                    dest.writeInt(commentCount);
//                    dest.writeInt(is_admiring);
//                    dest.writeInt(is_goals);
//                    dest.writeInt(is_inspiring);
//                    dest.writeString(videoThumbImage);
//                }
//            }
//        }

        public static class MealsBreakfastBean {
            /**
             * mealId : 197
             * mealPhoto :
             * mealType : 2
             * foodName : Egg Twists
             * unit : roll
             * quantity : 66
             */

            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String unit;
            private String mealName;
            private String quantity;

            public MealsBreakfastBean(int mealId, String mealPhoto, int mealType, String foodName, String unit, String mealName, String quantity) {
                this.mealId = mealId;
                this.mealPhoto = mealPhoto;
                this.mealType = mealType;
                this.foodName = foodName;
                this.unit = unit;
                this.mealName = mealName;
                this.quantity = quantity;
            }

            public String getMealName() {
                return mealName;
            }

            public void setMealName(String mealName) {
                this.mealName = mealName;
            }

            public int getMealId() {
                return mealId;
            }

            public void setMealId(int mealId) {
                this.mealId = mealId;
            }

            public String getMealPhoto() {
                return mealPhoto;
            }

            public void setMealPhoto(String mealPhoto) {
                this.mealPhoto = mealPhoto;
            }

            public int getMealType() {
                return mealType;
            }

            public void setMealType(int mealType) {
                this.mealType = mealType;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }

        public static class MealsLunchBean {
            /**
             * mealId : 197
             * mealPhoto :
             * mealType : 2
             * foodName : Egg Twists
             * unit : roll
             * quantity : 66
             */

            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String unit;
            private String quantity;
            private String mealName;

            public MealsLunchBean(int mealId, String mealPhoto, int mealType, String foodName, String unit, String quantity, String mealName) {
                this.mealId = mealId;
                this.mealPhoto = mealPhoto;
                this.mealType = mealType;
                this.foodName = foodName;
                this.unit = unit;
                this.quantity = quantity;
                this.mealName = mealName;
            }

            public String getMealName() {
                return mealName;
            }

            public void setMealName(String mealName) {
                this.mealName = mealName;
            }

            public int getMealId() {
                return mealId;
            }

            public void setMealId(int mealId) {
                this.mealId = mealId;
            }

            public String getMealPhoto() {
                return mealPhoto;
            }

            public void setMealPhoto(String mealPhoto) {
                this.mealPhoto = mealPhoto;
            }

            public int getMealType() {
                return mealType;
            }

            public void setMealType(int mealType) {
                this.mealType = mealType;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }

        public static class MealsSnacksBean {
            /**
             * mealId : 197
             * mealPhoto :
             * mealType : 2
             * foodName : Egg Twists
             * unit : roll
             * quantity : 66
             */

            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String unit;
            private String quantity;
            private String mealName;

            public MealsSnacksBean(int mealId, String mealPhoto, int mealType, String foodName, String unit, String quantity, String mealName) {
                this.mealId = mealId;
                this.mealPhoto = mealPhoto;
                this.mealType = mealType;
                this.foodName = foodName;
                this.unit = unit;
                this.quantity = quantity;
                this.mealName = mealName;
            }

            public String getMealName() {
                return mealName;
            }

            public void setMealName(String mealName) {
                this.mealName = mealName;
            }

            public int getMealId() {
                return mealId;
            }

            public void setMealId(int mealId) {
                this.mealId = mealId;
            }

            public String getMealPhoto() {
                return mealPhoto;
            }

            public void setMealPhoto(String mealPhoto) {
                this.mealPhoto = mealPhoto;
            }

            public int getMealType() {
                return mealType;
            }

            public void setMealType(int mealType) {
                this.mealType = mealType;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }

        public static class MealsDinnerBean {
            /**
             * mealId : 197
             * mealPhoto :
             * mealType : 2
             * foodName : Egg Twists
             * unit : roll
             * quantity : 66
             */

            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String unit;
            private String quantity;
            private String mealName;

            public String getMealName() {
                return mealName;
            }

            public void setMealName(String mealName) {
                this.mealName = mealName;
            }

            public MealsDinnerBean(int mealId, String mealPhoto, int mealType, String foodName, String unit, String quantity, String mealName) {
                this.mealId = mealId;
                this.mealPhoto = mealPhoto;
                this.mealType = mealType;
                this.foodName = foodName;
                this.unit = unit;
                this.quantity = quantity;
                this.mealName = mealName;
            }

            public int getMealId() {
                return mealId;
            }

            public void setMealId(int mealId) {
                this.mealId = mealId;
            }

            public String getMealPhoto() {
                return mealPhoto;
            }

            public void setMealPhoto(String mealPhoto) {
                this.mealPhoto = mealPhoto;
            }

            public int getMealType() {
                return mealType;
            }

            public void setMealType(int mealType) {
                this.mealType = mealType;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }

        public static class OtherMealDataBean {
            /**
             * mealId : 7
             * mealPhoto :
             * mealType : 5
             * foodName : Chakri
             * mealName : optional one
             * unit : chakri
             * quantity : 55
             */

            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String mealName;
            private String unit;
            private String quantity;

            public int getMealId() {
                return mealId;
            }

            public void setMealId(int mealId) {
                this.mealId = mealId;
            }

            public String getMealPhoto() {
                return mealPhoto;
            }

            public void setMealPhoto(String mealPhoto) {
                this.mealPhoto = mealPhoto;
            }

            public int getMealType() {
                return mealType;
            }

            public void setMealType(int mealType) {
                this.mealType = mealType;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public String getMealName() {
                return mealName;
            }

            public void setMealName(String mealName) {
                this.mealName = mealName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }
    }
}
