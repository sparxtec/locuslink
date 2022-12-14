package com.locuslink.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;


// C.Sparks 5-24- trying to make AJAX work to keep the Context.

//Since the SecurityContext is thread-bound, if you want to do any background processing that calls secure methods
//(for example, with @Async), you need to ensure that the context is propagated.
//This boils down to wrapping the SecurityContext with the task (Runnable, Callable, and so on)
//that is executed in the background. Spring Security provides some helpers to make this easier,
//such as wrappers for Runnable and Callable. To propagate the SecurityContext to @Async methods,
//you need to supply an AsyncConfigurer and ensure the Executor is of the correct type:


// sparks 12-14-2022 not needed in LL ?

//@Configuration
//public class AsyncConfig extends AsyncConfigurerSupport {
//
//  @Override
//  public Executor getAsyncExecutor() {
//    return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(5));
//  }
//
//
//}