package com.gt.planit.domain.weather.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MidTermApiResDto {
    private Response response;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Body {
        private String dataType;
        private Items items;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Item {
        private String regId;
        private Integer rnSt3Am;
        private Integer rnSt3Pm;
        private Integer rnSt4Am;
        private Integer rnSt4Pm;
        private Integer rnSt5Am;
        private Integer rnSt5Pm;
        private Integer rnSt6Am;
        private Integer rnSt6Pm;
        private Integer rnSt7Am;
        private Integer rnSt7Pm;
        private Integer rnSt8;
        private Integer rnSt9;
        private Integer rnSt10;
        private String wf3Am;
        private String wf3Pm;
        private String wf4Am;
        private String wf4Pm;
        private String wf5Am;
        private String wf5Pm;
        private String wf6Am;
        private String wf6Pm;
        private String wf7Am;
        private String wf7Pm;
        private String wf8;
        private String wf9;
        private String wf10;
    }
}
