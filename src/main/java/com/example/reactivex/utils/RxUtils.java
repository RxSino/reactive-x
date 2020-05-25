package com.example.reactivex.utils;

import com.example.reactivex.resp.BaseResp;
import com.example.reactivex.resp.PageResp;
//import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class RxUtils {

    public static <T> Function<T, Mono<? extends BaseResp<T>>> baseRespToMono() {
        return t -> Mono.just(BaseResp.success(t));
    }

    public static <T> Function<T, Mono<? extends PageResp<T>>> listRespToMono() {
        return t -> Mono.just(PageResp.create(t));
    }

//    public static <T> Function<Page<T>, Mono<? extends PageResp<List<T>>>> pageRespToMono() {
//        return tPage -> {
//            PageResp<List<T>> pageResp = PageResp.create(tPage.getContent());
//            pageResp.number = tPage.getNumber();
//            pageResp.numberOfElements = tPage.getNumberOfElements();
//            pageResp.size = tPage.getSize();
//            pageResp.isLast = tPage.isLast();
//            pageResp.totalPages = tPage.getTotalPages();
//            pageResp.totalElements = tPage.getTotalElements();
//            return Mono.just(pageResp);
//        };
//    }


}
