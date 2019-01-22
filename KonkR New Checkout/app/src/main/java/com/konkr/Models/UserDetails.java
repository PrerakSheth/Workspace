package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 7/6/2018.
 */

public class UserDetails implements Parcelable {

    /**
     * message : User details get successfully.
     * userDetails : {"firstName":"A","lastName":"A","nickName":"A","dob":"2004-07-25","age":14,"badge":"1","gender":0,"currentTrainingGoals":["Eeee","Loose","Mosan"],"bodyType":"Ectomorph","weight":"12.00","height":"12.00","bodyFat":"12.00","bio":"Vasvasv","followersCount":0,"followingCount":0,"isFollowing":0,"profilePic":"","socialLinks":{"FB":"","Instagram":"","Snapchat":"","Twitter":"","Youtube":"","LinkedIn":""},"supplements":[{"suppId":211,"suppName":"Sweet Soy Sauce","suppDetails":"Hi","suppPhoto":"http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/211.png"}],"mealsBreakfast":[{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}],"mealsLunch":[{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}],"mealsSnacks":[{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}],"mealsDinner":[{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}],"miTraining":[{"workoutId":38,"workoutName":"jj","workoutCategoryId":"8","workoutCategoryName":"Arms","excerciseId":"522","excerciseName":"Barbell Squat","workoutDuration":{"hours":"01","mins":"00"},"workoutMedia":{"videoThumbImage":"","id":"197","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/197.png"}},{"workoutId":39,"workoutName":"sdd","workoutCategoryId":"8","workoutCategoryName":"Arms","excerciseId":"38","excerciseName":"Bankdrücken Eng","workoutDuration":{"hours":"01","mins":"00"},"workoutMedia":{"videoThumbImage":"","id":"203","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/203.png"}}],"imageArray":{"photo1":"","photo2":"","photo3":"","photo4":"","photo5":"","photo6":"","photo7":"","photo8":"","photo9":""}}
     * status : 1
     */

    private String message;
    private UserDetailsBean userDetails;
    private int status;

    protected UserDetails(Parcel in) {
        message = in.readString();
        status = in.readInt();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetailsBean getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsBean userDetails) {
        this.userDetails = userDetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(status);
    }

    public static class UserDetailsBean implements Parcelable {
        /**
         * firstName : A
         * lastName : A
         * nickName : A
         * dob : 2004-07-25
         * age : 14
         * badge : 1
         * gender : 0
         * currentTrainingGoals : ["Eeee","Loose","Mosan"]
         * bodyType : Ectomorph
         * weight : 12.00
         * height : 12.00
         * bodyFat : 12.00
         * bio : Vasvasv
         * followersCount : 0
         * followingCount : 0
         * isFollowing : 0
         * profilePic :
         * socialLinks : {"FB":"","Instagram":"","Snapchat":"","Twitter":"","Youtube":"","LinkedIn":""}
         * supplements : [{"suppId":211,"suppName":"Sweet Soy Sauce","suppDetails":"Hi","suppPhoto":"http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/211.png"}]
         * mealsBreakfast : [{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}]
         * mealsLunch : [{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}]
         * mealsSnacks : [{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}]
         * mealsDinner : [{"mealId":1,"mealPhoto":"base64","mealType":1,"foodName":"egg","unit":"mg","quantity":5}]
         * miTraining : [{"workoutId":38,"workoutName":"jj","workoutCategoryId":"8","workoutCategoryName":"Arms","excerciseId":"522","excerciseName":"Barbell Squat","workoutDuration":{"hours":"01","mins":"00"},"workoutMedia":{"videoThumbImage":"","id":"197","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/197.png"}},{"workoutId":39,"workoutName":"sdd","workoutCategoryId":"8","workoutCategoryName":"Arms","excerciseId":"38","excerciseName":"Bankdrücken Eng","workoutDuration":{"hours":"01","mins":"00"},"workoutMedia":{"videoThumbImage":"","id":"203","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/203.png"}}]
         * imageArray : {"photo1":"","photo2":"","photo3":"","photo4":"","photo5":"","photo6":"","photo7":"","photo8":"","photo9":""}
         */

        private String firstName;
        private String lastName;
        private String nickName;
        private String dob;
        private int age;
        private String badge;
        private int gender;
        private String bodyType;
        private String weight;
        private String height;
        private String bodyFat;
        private String bio;
        private int followersCount;
        private int followingCount;
        private int isFollowing;
        private int isSubscribed;
        private String profilePic;
        private String videoThumb;
        private String spotifyUserId;
        private String videoThumbImg;
        private SocialLinksBean socialLinks;
        private List<String> currentTrainingGoals;
        private List<SupplementsBean> supplements;
        private ArrayList<Meal> mealsBreakfast;
        private ArrayList<Meal> mealsLunch;
        private ArrayList<Meal> mealsSnacks;
        private ArrayList<Meal> mealsDinner;
        private List<ImageArrayBean> imageArray;
        private int profilePicExpressionCount;
        private int profilePicCommentCount;
        private int is_goals;
        private int is_inspiring;
        private int is_admiring;
        private int subscriptionCount;
        private int isSubscribedForUser;

        public String getSpotifyUserId() {
            return spotifyUserId;
        }

        public void setSpotifyUserId(String spotifyUserId) {
            this.spotifyUserId = spotifyUserId;
        }

        protected UserDetailsBean(Parcel in) {
            firstName = in.readString();
            lastName = in.readString();
            nickName = in.readString();
            dob = in.readString();
            age = in.readInt();
            badge = in.readString();
            gender = in.readInt();
            bodyType = in.readString();
            weight = in.readString();
            height = in.readString();
            bodyFat = in.readString();
            bio = in.readString();
            followersCount = in.readInt();
            followingCount = in.readInt();
            isFollowing = in.readInt();
            isSubscribed = in.readInt();
            profilePic = in.readString();
            videoThumb = in.readString();
            videoThumbImg = in.readString();
            socialLinks = in.readParcelable(SocialLinksBean.class.getClassLoader());
            currentTrainingGoals = in.createStringArrayList();
            supplements = in.createTypedArrayList(SupplementsBean.CREATOR);
            mealsBreakfast = in.createTypedArrayList(Meal.CREATOR);
            mealsLunch = in.createTypedArrayList(Meal.CREATOR);
            mealsSnacks = in.createTypedArrayList(Meal.CREATOR);
            mealsDinner = in.createTypedArrayList(Meal.CREATOR);
            imageArray = in.createTypedArrayList(ImageArrayBean.CREATOR);
            profilePicExpressionCount = in.readInt();
            profilePicCommentCount = in.readInt();
            is_goals = in.readInt();
            is_inspiring = in.readInt();
            is_admiring = in.readInt();
            miTraining = in.createTypedArrayList(MiTrainingBean.CREATOR);
        }

        public static final Creator<UserDetailsBean> CREATOR = new Creator<UserDetailsBean>() {
            @Override
            public UserDetailsBean createFromParcel(Parcel in) {
                return new UserDetailsBean(in);
            }

            @Override
            public UserDetailsBean[] newArray(int size) {
                return new UserDetailsBean[size];
            }
        };

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

        public int getProfilePicExpressionCount() {
            return profilePicExpressionCount;
        }

        public void setProfilePicExpressionCount(int profilePicExpressionCount) {
            this.profilePicExpressionCount = profilePicExpressionCount;
        }

        public int getProfilePicCommentCount() {
            return profilePicCommentCount;
        }

        public void setProfilePicCommentCount(int profilePicCommentCount) {
            this.profilePicCommentCount = profilePicCommentCount;
        }

//        public static Creator<UserDetailsBean> getCREATOR() {
//            return CREATOR;
//        }
//
//        protected UserDetailsBean(Parcel in) {
//            firstName = in.readString();
//            lastName = in.readString();
//            nickName = in.readString();
//            dob = in.readString();
//            age = in.readInt();
//            badge = in.readString();
//            gender = in.readInt();
//            bodyType = in.readString();
//            weight = in.readString();
//            height = in.readString();
//            bodyFat = in.readString();
//            bio = in.readString();
//            followersCount = in.readInt();
//            followingCount = in.readInt();
//            isFollowing = in.readInt();
//            isSubscribed = in.readInt();
//            profilePic = in.readString();
//            videoThumb = in.readString();
//            videoThumbImg = in.readString();
//            socialLinks = in.readParcelable(SocialLinksBean.class.getClassLoader());
//            imageArray = in.readParcelable(ImageArrayBean.class.getClassLoader());
//            currentTrainingGoals = in.createStringArrayList();
//            supplements = in.createTypedArrayList(SupplementsBean.CREATOR);
//            mealsBreakfast = in.createTypedArrayList(Meal.CREATOR);
//            mealsLunch = in.createTypedArrayList(Meal.CREATOR);
//            mealsSnacks = in.createTypedArrayList(Meal.CREATOR);
//            mealsDinner = in.createTypedArrayList(Meal.CREATOR);
//            miTraining = in.createTypedArrayList(MiTrainingBean.CREATOR);
//        }
//
//        public static final Creator<UserDetailsBean> CREATOR = new Creator<UserDetailsBean>() {
//            @Override
//            public UserDetailsBean createFromParcel(Parcel in) {
//                return new UserDetailsBean(in);
//            }
//
//            @Override
//            public UserDetailsBean[] newArray(int size) {
//                return new UserDetailsBean[size];
//            }
//        };

        public ArrayList<Meal> getMealsBreakfast() {
            return mealsBreakfast;
        }

        public void setMealsBreakfast(ArrayList<Meal> mealsBreakfast) {
            this.mealsBreakfast = mealsBreakfast;
        }

        public ArrayList<Meal> getMealsLunch() {
            return mealsLunch;
        }

        public void setMealsLunch(ArrayList<Meal> mealsLunch) {
            this.mealsLunch = mealsLunch;
        }

        public ArrayList<Meal> getMealsSnacks() {
            return mealsSnacks;
        }

        public void setMealsSnacks(ArrayList<Meal> mealsSnacks) {
            this.mealsSnacks = mealsSnacks;
        }

        public ArrayList<Meal> getMealsDinner() {
            return mealsDinner;
        }

        public void setMealsDinner(ArrayList<Meal> mealsDinner) {
            this.mealsDinner = mealsDinner;
        }

        public ArrayList<ArrayList<Meal>> getOtherMealData() {
            return otherMealData;
        }

        public void setOtherMealData(ArrayList<ArrayList<Meal>> otherMealData) {
            this.otherMealData = otherMealData;
        }

        private ArrayList<ArrayList<Meal>> otherMealData;
        private List<MiTrainingBean> miTraining;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getBodyType() {
            return bodyType;
        }

        public void setBodyType(String bodyType) {
            this.bodyType = bodyType;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getBodyFat() {
            return bodyFat;
        }

        public void setBodyFat(String bodyFat) {
            this.bodyFat = bodyFat;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getFollowersCount() {
            return followersCount;
        }

        public void setFollowersCount(int followersCount) {
            this.followersCount = followersCount;
        }

        public int getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(int followingCount) {
            this.followingCount = followingCount;
        }

        public int getIsFollowing() {
            return isFollowing;
        }

        public void setIsFollowing(int isFollowing) {
            this.isFollowing = isFollowing;
        }

        public int getIsSubscribed() {
            return isSubscribed;
        }

        public void setIsSubscribed(int isSubscribed) {
            this.isSubscribed = isSubscribed;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getVideoThumb() {
            return videoThumb;
        }

        public void setVideoThumb(String videoThumb) {
            this.videoThumb = videoThumb;
        }

        public String getVideoThumbImg() {
            return videoThumbImg;
        }

        public void setVideoThumbImg(String videoThumbImg) {
            this.videoThumbImg = videoThumbImg;
        }

        public SocialLinksBean getSocialLinks() {
            return socialLinks;
        }

        public void setSocialLinks(SocialLinksBean socialLinks) {
            this.socialLinks = socialLinks;
        }


        public List<String> getCurrentTrainingGoals() {
            return currentTrainingGoals;
        }

        public void setCurrentTrainingGoals(List<String> currentTrainingGoals) {
            this.currentTrainingGoals = currentTrainingGoals;
        }

        public List<SupplementsBean> getSupplements() {
            return supplements;
        }

        public void setSupplements(List<SupplementsBean> supplements) {
            this.supplements = supplements;
        }


        public List<MiTrainingBean> getMiTraining() {
            return miTraining;
        }

        public void setMiTraining(List<MiTrainingBean> miTraining) {
            this.miTraining = miTraining;
        }


        public List<ImageArrayBean> getImageArray() {
            return imageArray;
        }

        public void setImageArray(List<ImageArrayBean> imageArray) {
            this.imageArray = imageArray;
        }

        public int getSubscriptionCount() {
            return subscriptionCount;
        }

        public void setSubscriptionCount(int subscriptionCount) {
            this.subscriptionCount = subscriptionCount;
        }

        public int getIsSubscribedForUser() {
            return isSubscribedForUser;
        }

        public void setIsSubscribedForUser(int isSubscribedForUser) {
            this.isSubscribedForUser = isSubscribedForUser;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static class SocialLinksBean implements Parcelable {
            /**
             * FB :
             * Instagram :
             * Snapchat :
             * Twitter :
             * Youtube :
             * LinkedIn :
             */

            private String FB;
            private String Instagram;
            private String Snapchat;
            private String Twitter;
            private String Youtube;
            private String LinkedIn;

            protected SocialLinksBean(Parcel in) {
                FB = in.readString();
                Instagram = in.readString();
                Snapchat = in.readString();
                Twitter = in.readString();
                Youtube = in.readString();
                LinkedIn = in.readString();
            }

            public static final Creator<SocialLinksBean> CREATOR = new Creator<SocialLinksBean>() {
                @Override
                public SocialLinksBean createFromParcel(Parcel in) {
                    return new SocialLinksBean(in);
                }

                @Override
                public SocialLinksBean[] newArray(int size) {
                    return new SocialLinksBean[size];
                }
            };

            public String getFB() {
                return FB;
            }

            public void setFB(String FB) {
                this.FB = FB;
            }

            public String getInstagram() {
                return Instagram;
            }

            public void setInstagram(String Instagram) {
                this.Instagram = Instagram;
            }

            public String getSnapchat() {
                return Snapchat;
            }

            public void setSnapchat(String Snapchat) {
                this.Snapchat = Snapchat;
            }

            public String getTwitter() {
                return Twitter;
            }

            public void setTwitter(String Twitter) {
                this.Twitter = Twitter;
            }

            public String getYoutube() {
                return Youtube;
            }

            public void setYoutube(String Youtube) {
                this.Youtube = Youtube;
            }

            public String getLinkedIn() {
                return LinkedIn;
            }

            public void setLinkedIn(String LinkedIn) {
                this.LinkedIn = LinkedIn;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(FB);
                dest.writeString(Instagram);
                dest.writeString(Snapchat);
                dest.writeString(Twitter);
                dest.writeString(Youtube);
                dest.writeString(LinkedIn);
            }
        }

        public static class SupplementsBean implements Parcelable {
            /**
             * suppId : 211
             * suppName : Sweet Soy Sauce
             * suppDetails : Hi
             * suppPhoto : http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/211.png
             */

            private int suppId;
            private String suppName;
            private String suppDetails;
            private String suppPhoto;
            /**
             * expressionCount : 0
             * commentCount : 0
             * is_admiring : 0
             * is_inspiring : 0
             * is_goals : 0
             */

            private int expressionCount;
            private int commentCount;
            private int is_admiring;
            private int is_inspiring;
            private int is_goals;
            private String homeFeedId;

            protected SupplementsBean(Parcel in) {
                suppId = in.readInt();
                suppName = in.readString();
                suppDetails = in.readString();
                suppPhoto = in.readString();
                expressionCount = in.readInt();
                commentCount = in.readInt();
                is_admiring = in.readInt();
                is_inspiring = in.readInt();
                is_goals = in.readInt();
                homeFeedId = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(suppId);
                dest.writeString(suppName);
                dest.writeString(suppDetails);
                dest.writeString(suppPhoto);
                dest.writeInt(expressionCount);
                dest.writeInt(commentCount);
                dest.writeInt(is_admiring);
                dest.writeInt(is_inspiring);
                dest.writeInt(is_goals);
                dest.writeString(homeFeedId);
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

            public int getIs_inspiring() {
                return is_inspiring;
            }

            public void setIs_inspiring(int is_inspiring) {
                this.is_inspiring = is_inspiring;
            }

            public int getIs_goals() {
                return is_goals;
            }

            public void setIs_goals(int is_goals) {
                this.is_goals = is_goals;
            }

            public String getHomeFeedId() {
                return homeFeedId;
            }

            public void setHomeFeedId(String homeFeedId) {
                this.homeFeedId = homeFeedId;
            }
        }

        public static class Meal implements Parcelable {
            /**
             * mealId : 3
             * mealPhoto :
             * mealType : 1
             * foodName : Tandoori Chicken Samosa
             * mealName :
             * unit : pieces
             * quantity : 94
             */
            private int mealId;
            private String mealPhoto;
            private int mealType;
            private String foodName;
            private String mealName;
            private String unit;
            private String quantity;
            private String homeFeedId;
            int is_inspiring;
            int is_goals;
            int is_admiring;
            int expressionCount;
            int commentCount;

            public String getHomeFeedId() {
                return homeFeedId;
            }

            public void setHomeFeedId(String homeFeedId) {
                this.homeFeedId = homeFeedId;
            }

            public int getIs_inspiring() {
                return is_inspiring;
            }

            public void setIs_inspiring(int is_inspiring) {
                this.is_inspiring = is_inspiring;
            }

            public int getIs_goals() {
                return is_goals;
            }

            public void setIs_goals(int is_goals) {
                this.is_goals = is_goals;
            }

            public int getIs_admiring() {
                return is_admiring;
            }

            public void setIs_admiring(int is_admiring) {
                this.is_admiring = is_admiring;
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

            public static Creator<Meal> getCREATOR() {
                return CREATOR;
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


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.mealId);
                dest.writeString(this.mealPhoto);
                dest.writeInt(this.mealType);
                dest.writeString(this.foodName);
                dest.writeString(this.mealName);
                dest.writeString(this.unit);
                dest.writeString(this.quantity);
                dest.writeString(this.homeFeedId);
                dest.writeInt(this.is_inspiring);
                dest.writeInt(this.is_goals);
                dest.writeInt(this.is_admiring);
                dest.writeInt(this.expressionCount);
                dest.writeInt(this.commentCount);
            }

            public Meal() {
            }

            protected Meal(Parcel in) {
                this.mealId = in.readInt();
                this.mealPhoto = in.readString();
                this.mealType = in.readInt();
                this.foodName = in.readString();
                this.mealName = in.readString();
                this.unit = in.readString();
                this.quantity = in.readString();
                this.homeFeedId = in.readString();
                this.is_inspiring = in.readInt();
                this.is_goals = in.readInt();
                this.is_admiring = in.readInt();
                this.expressionCount = in.readInt();
                this.commentCount = in.readInt();
            }

            public static final Creator<Meal> CREATOR = new Creator<Meal>() {
                @Override
                public Meal createFromParcel(Parcel source) {
                    return new Meal(source);
                }

                @Override
                public Meal[] newArray(int size) {
                    return new Meal[size];
                }
            };
        }

        public static class MiTrainingBean implements Parcelable {
            /**
             * workoutId : 38
             * workoutName : jj
             * workoutCategoryId : 8
             * workoutCategoryName : Arms
             * excerciseId : 522
             * excerciseName : Barbell Squat
             * workoutDuration : {"hours":"01","mins":"00"}
             * workoutMedia : {"videoThumbImage":"","id":"197","mediaType":"2","url":"http://dev2.ifuturz.com/core/konkr/assets/upload/workout/197.png"}
             */

            private int workoutId;
            private String workoutName;
            private String workoutCategoryId;
            private String workoutCategoryName;
            private String excerciseId;
            private String excerciseName;
            private WorkoutDurationBean workoutDuration;
            private WorkoutMediaBean workoutMedia;

            public int getWorkoutId() {
                return workoutId;
            }

            public void setWorkoutId(int workoutId) {
                this.workoutId = workoutId;
            }

            public String getWorkoutName() {
                return workoutName;
            }

            public void setWorkoutName(String workoutName) {
                this.workoutName = workoutName;
            }

            public String getWorkoutCategoryId() {
                return workoutCategoryId;
            }

            public void setWorkoutCategoryId(String workoutCategoryId) {
                this.workoutCategoryId = workoutCategoryId;
            }

            public String getWorkoutCategoryName() {
                return workoutCategoryName;
            }

            public void setWorkoutCategoryName(String workoutCategoryName) {
                this.workoutCategoryName = workoutCategoryName;
            }

            public String getExcerciseId() {
                return excerciseId;
            }

            public void setExcerciseId(String excerciseId) {
                this.excerciseId = excerciseId;
            }

            public String getExcerciseName() {
                return excerciseName;
            }

            public void setExcerciseName(String excerciseName) {
                this.excerciseName = excerciseName;
            }

            public WorkoutDurationBean getWorkoutDuration() {
                return workoutDuration;
            }

            public void setWorkoutDuration(WorkoutDurationBean workoutDuration) {
                this.workoutDuration = workoutDuration;
            }

            public WorkoutMediaBean getWorkoutMedia() {
                return workoutMedia;
            }

            public void setWorkoutMedia(WorkoutMediaBean workoutMedia) {
                this.workoutMedia = workoutMedia;
            }

            public static class WorkoutDurationBean implements Parcelable {
                /**
                 * hours : 01
                 * mins : 00
                 */

                private String hours;
                private String mins;

                protected WorkoutDurationBean(Parcel in) {
                    hours = in.readString();
                    mins = in.readString();
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

                public String getHours() {
                    return hours;
                }

                public void setHours(String hours) {
                    this.hours = hours;
                }

                public String getMins() {
                    return mins;
                }

                public void setMins(String mins) {
                    this.mins = mins;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(hours);
                    dest.writeString(mins);
                }
            }

            public static class WorkoutMediaBean implements Parcelable {
                /**
                 * videoThumbImage :
                 * id : 197
                 * mediaType : 2
                 * url : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/197.png
                 */

                private String videoThumbImage;
                private String id;
                private String mediaType;
                private String url;

                protected WorkoutMediaBean(Parcel in) {
                    videoThumbImage = in.readString();
                    id = in.readString();
                    mediaType = in.readString();
                    url = in.readString();
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

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(videoThumbImage);
                    dest.writeString(id);
                    dest.writeString(mediaType);
                    dest.writeString(url);
                }
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.workoutId);
                dest.writeString(this.workoutName);
                dest.writeString(this.workoutCategoryId);
                dest.writeString(this.workoutCategoryName);
                dest.writeString(this.excerciseId);
                dest.writeString(this.excerciseName);
                dest.writeParcelable(this.workoutDuration, flags);
                dest.writeParcelable(this.workoutMedia, flags);
            }

            public MiTrainingBean() {
            }

            protected MiTrainingBean(Parcel in) {
                this.workoutId = in.readInt();
                this.workoutName = in.readString();
                this.workoutCategoryId = in.readString();
                this.workoutCategoryName = in.readString();
                this.excerciseId = in.readString();
                this.excerciseName = in.readString();
                this.workoutDuration = in.readParcelable(WorkoutDurationBean.class.getClassLoader());
                this.workoutMedia = in.readParcelable(WorkoutMediaBean.class.getClassLoader());
            }

            public static final Creator<MiTrainingBean> CREATOR = new Creator<MiTrainingBean>() {
                @Override
                public MiTrainingBean createFromParcel(Parcel source) {
                    return new MiTrainingBean(source);
                }

                @Override
                public MiTrainingBean[] newArray(int size) {
                    return new MiTrainingBean[size];
                }
            };
        }

        public static class ImageArrayBean implements Parcelable {
            /**
             * image : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/4/4_1.png
             * mediaId : 1
             * commentCount : 2
             * expressionCount : 1
             * is_goals : 1
             * is_inspiring : 0
             * is_admiring : 0
             */

            private String image;
            private String mediaId;
            private int commentCount;
            private int expressionCount;
            private int is_goals;
            private int is_inspiring;
            private int is_admiring;

            protected ImageArrayBean(Parcel in) {
                image = in.readString();
                mediaId = in.readString();
                commentCount = in.readInt();
                expressionCount = in.readInt();
                is_goals = in.readInt();
                is_inspiring = in.readInt();
                is_admiring = in.readInt();
            }

            public static final Creator<ImageArrayBean> CREATOR = new Creator<ImageArrayBean>() {
                @Override
                public ImageArrayBean createFromParcel(Parcel in) {
                    return new ImageArrayBean(in);
                }

                @Override
                public ImageArrayBean[] newArray(int size) {
                    return new ImageArrayBean[size];
                }
            };

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMediaId() {
                return mediaId;
            }

            public void setMediaId(String mediaId) {
                this.mediaId = mediaId;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public int getExpressionCount() {
                return expressionCount;
            }

            public void setExpressionCount(int expressionCount) {
                this.expressionCount = expressionCount;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(image);
                dest.writeString(mediaId);
                dest.writeInt(commentCount);
                dest.writeInt(expressionCount);
                dest.writeInt(is_goals);
                dest.writeInt(is_inspiring);
                dest.writeInt(is_admiring);
            }
        }
    }
}
