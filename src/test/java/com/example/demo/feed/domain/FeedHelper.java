package com.example.demo.feed.domain;

public class FeedHelper {// 좋은 녀석이다.
    private FeedHelper(){}

    public static Feed create(Long id, Writer writer, Title title, Content content ){
        Feed feed = Feed.create(writer, title, content);
        feed.setId(id);

        return feed;
    }

    // 그루비 ->
    /*


    private 접근 가능
     -> 테스트 코드에만 사용[위험해서]




     */
}
