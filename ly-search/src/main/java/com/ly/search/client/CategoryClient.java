package com.ly.search.client;

import com.ly.api.CategoryAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by HXin on 2019/4/13.
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryAPI {
}
