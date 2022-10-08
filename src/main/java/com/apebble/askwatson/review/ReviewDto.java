package com.apebble.askwatson.review;

import com.apebble.askwatson.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class ReviewDto {

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private UserDto user=null;
        private double difficulty;
        private double timeTaken;
        private Integer usedHintNum;
        private double rating;
        private double deviceRatio;
        private double activity;
        private String content;

        public Response(Review entity) {
            this.id = entity.getId();
            if(!entity.isUserNull())
                this.user = new UserDto(entity.getUser());
            this.difficulty = entity.getDifficulty();
            this.timeTaken = entity.getTimeTaken();
            this.usedHintNum = entity.getUsedHintNum();
            this.rating = entity.getRating();
            this.deviceRatio = entity.getDeviceRatio();
            this.activity = entity.getActivity();
            this.content = entity.getContent();
        }
    }

    @Getter @NoArgsConstructor
    private static class UserDto {
        private Long id;
        private String userNickname;

        public UserDto(User entity) {
            this.id = entity.getId();
            this.userNickname = entity.getUserNickname();
        }
    }
}
