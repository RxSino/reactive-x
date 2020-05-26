package com.example.reactivex.utils;

import com.example.reactivex.exceptions.MyEmptyException;
import com.example.reactivex.exceptions.MyErrorException;
import com.example.reactivex.laihuola.LoginResp;
import com.example.reactivex.resp.BaseResp;
import com.example.reactivex.resp.PageResp;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class MonoUtils {

    public static <T> Function<BaseResp<T>, Mono<T>> baseMapper() {
        return new Function<BaseResp<T>, Mono<T>>() {
            @Override
            public Mono<T> apply(BaseResp<T> tBaseResp) {
                if (tBaseResp.getCode() != 0) {
                    return Mono.error(new MyErrorException());
                }
                if (tBaseResp.getData() == null) {
                    return Mono.error(new MyEmptyException());
                }
                return Mono.just(tBaseResp.getData());
            }
        };
    }

    public static <T> Function<PageResp<T>, Mono<T>> listMapper() {
        return new Function<PageResp<T>, Mono<T>>() {
            @Override
            public Mono<T> apply(PageResp<T> tPageResp) {
                if (tPageResp.data == null) {
                    return Mono.error(new MyEmptyException());
                }
                return Mono.just(tPageResp.data);
            }
        };
    }

    public static <T> Function<BaseResp<PageResp<T>>, Mono<T>> pageMapper() {
        return new Function<BaseResp<PageResp<T>>, Mono<T>>() {
            @Override
            public Mono<T> apply(BaseResp<PageResp<T>> pageRespBaseResp) {
                if (pageRespBaseResp.getCode() != 0) {
                    return Mono.error(new MyErrorException());
                }
                if (pageRespBaseResp.getData() == null) {
                    return Mono.error(new MyEmptyException());
                }
                if (pageRespBaseResp.getData().getData() == null) {
                    return Mono.error(new MyEmptyException());
                }
                return Mono.just(pageRespBaseResp.getData().getData());
            }
        };
    }

    // transformer
    public static <T> Function<Mono<BaseResp<T>>, Mono<T>> baseTransformer() {
        return new Function<Mono<BaseResp<T>>, Mono<T>>() {
            @Override
            public Mono<T> apply(Mono<BaseResp<T>> baseRespMono) {
                return baseRespMono.flatMap(baseMapper());
            }
        };
    }

    // transformer
    public static <T> Function<Mono<BaseResp<PageResp<T>>>, Mono<T>> pageTransformer() {
        return new Function<Mono<BaseResp<PageResp<T>>>, Mono<T>>() {
            @Override
            public Mono<T> apply(Mono<BaseResp<PageResp<T>>> baseRespMono) {
                return baseRespMono.flatMap(pageMapper());
            }
        };
    }


    // login
    public static <T> Function<Mono<LoginResp<T>>, Mono<T>> loginTransformer() {
        return new Function<Mono<LoginResp<T>>, Mono<T>>() {
            @Override
            public Mono<T> apply(Mono<LoginResp<T>> baseRespMono) {
                return baseRespMono.flatMap(new Function<LoginResp<T>, Mono<? extends T>>() {
                    @Override
                    public Mono<? extends T> apply(LoginResp<T> tLoginResp) {
                        if (!tLoginResp.success) {
                            return Mono.error(new MyErrorException());
                        }
                        if (tLoginResp.data == null) {
                            return Mono.error(new MyEmptyException());
                        }
                        return Mono.just(tLoginResp.data);
                    }
                });
            }
        };
    }

}
