package com.apebble.askwatson.theme;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeNotion {
    private String object;
    private String id;
    private String created_time;
    private String last_edited_time;
    Created_by Created_byObject;
    Last_edited_by Last_edited_byObject;
    private String cover = null;
    private String icon = null;
    Parent ParentObject;
    private boolean archived;
    Properties PropertiesObject;
    private String url;

    @Getter
    @Setter
    public class Properties {
        Image_url Image_urlObject;
        Explanation ExplanationObject;
        Difficulty DifficultyObject;
        Category_id Category_idObject;
        Device_ratio Device_ratioObject;
        Time_limit Time_limitObject;
        Rating RatingObject;
        Id IdObject;
        Cafe_id Cafe_idObject;
        Name NameObject;

    }

    @Getter
    @Setter
    public class Name {
        private String id;
        private String type;
        ArrayList<Object> title = new ArrayList<Object>();
    }

    @Getter
    @Setter
    public class Cafe_id {
        private String id;
        private String type;
        private float number;

    }

    @Getter
    @Setter
    public class Id {
        private String id;
        private String type;
        private String number = null;

    }

    @Getter
    @Setter
    public class Rating {
        private String id;
        private String type;
        private float number;
    }

    @Getter
    @Setter
    public class Time_limit {
        private String id;
        private String type;
        private float number;

    }

    @Getter
    @Setter
    public class Device_ratio {
        private String id;
        private String type;
        private float number;

    }

    @Getter
    @Setter
    public class Category_id {
        private String id;
        private String type;
        private float number;

    }

    @Getter
    @Setter
    public class Difficulty {
        private String id;
        private String type;
        private float number;
    }

    @Getter
    @Setter
    public class Explanation {
        private String id;
        private String type;
        ArrayList<Object> rich_text = new ArrayList<Object>();
    }

    @Getter
    @Setter
    public class Image_url {
        private String id;
        private String type;
        private String url;

    }

    @Getter
    @Setter
    public class Parent {
        private String type;
        private String database_id;
    }

    @Getter
    @Setter
    public class Last_edited_by {
        private String object;
        private String id;

    }

    @Getter
    @Setter
    public class Created_by {
        private String object;
        private String id;

    }

}
