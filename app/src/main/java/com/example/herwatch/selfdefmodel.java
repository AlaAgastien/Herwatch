package com.example.herwatch;

public class selfdefmodel {
        String id,username,userphone,title,description,video;

        public selfdefmodel(String id, String username, String userphone, String title, String description, String video) {
                this.id = id;
                this.username = username;
                this.userphone = userphone;
                this.title = title;
                this.description = description;
                this.video = video;
        }

        public String getId() {
                return id;
        }

        public String getUsername() {
                return username;
        }

        public String getUserphone() {
                return userphone;
        }

        public String getTitle() {
                return title;
        }

        public String getDescription() {
                return description;
        }

        public String getVideo() {
                return video;
        }
}
