package com.example.hystrixdemo.util;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author yuanzhijian
 */
@Slf4j
public class HystrixCommandUtil {

	/**
	 * 客户端参数异常时将抛出HystrixBadRequestException
	 *
	 * @param groupKey
	 * @param commandKey
	 * @param call
	 * @param fallback
	 * @param <T>
	 * @return
	 * @throws HystrixBadRequestException
	 */
	public static <T> T execute(String groupKey, String commandKey, Call<T> call, HystrixFallback<T> fallback) throws HystrixBadRequestException {
		if (groupKey == null) {
			throw new IllegalArgumentException("groupKey 不能为空");
		}
		if (commandKey == null) {
			throw new IllegalArgumentException("CommandKey 不能为空");
		}
		if (call == null) {
			throw new IllegalArgumentException("call 不能为空");
		}
		if (fallback == null) {
			throw new IllegalArgumentException("fallback 不能为空");
		}

		return new HystrixCommand<T>(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
			.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))) {

			@Override
			protected T run() throws Exception {
				Response<T> response = call.execute();
				if (response != null) {
					if (response.code() >= 200 && response.code() < 300) {
						return response.body();
					} else if (response.code() >= 400 && response.code() < 500) {
						if (response.errorBody() != null) {
							throw new HystrixBadRequestException(response.errorBody().string());
						} else {
							throw new HystrixBadRequestException("客户端参数非法");
						}
					} else {
						if (response.errorBody() != null) {
							throw new RuntimeException(response.errorBody().string());
						} else {
							throw new RuntimeException("服务端未知异常");
						}
					}
				} else {
					throw new RuntimeException("未知异常");
				}
			}

			@Override
			protected T getFallback() {
				return fallback.fallback(getExecutionException());
			}

		}.execute();
	}
}
