package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

public class AuthorDetails {
        private String name;
        private String username;
        @SerializedName("avatar_path")
        private Object avatarPath;
        private Long rating;

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public String getUsername() { return username; }
        public void setUsername(String value) { this.username = value; }

        public Object getAvatarPath() { return avatarPath; }
        public void setAvatarPath(Object value) { this.avatarPath = value; }

        public Long getRating() { return rating; }
        public void setRating(Long value) { this.rating = value; }
}
