package com.bird.mm

import android.util.Log
import android.view.View
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import timber.log.Timber
import java.util.*

@Aspect
class AspectTest {

//    @Before("execution(* com.bird.mm.MainActivity.on**(..))")
//    fun method(joinPoint: JoinPoint) {
//        val methodSignature = joinPoint.signature as MethodSignature
//        val className = joinPoint.`this`.javaClass.simpleName
//        Timber.i("methodSignature ： $methodSignature")
//        Timber.i("methodSignature ： $className")
//    }
//
//
//    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
//    fun clickFilterHook( joinPoint: ProceedingJoinPoint) {
////        val methodSignature = joinPoint.signature as MethodSignature
//        val args = joinPoint.args
//        val thisObject = joinPoint.`this`
//        args.forEach {
//            Timber.i("clickFilterHook $it")
//            if (it is View){
//                val view = it as View
//                val viewName = view.context.resources.getResourceName(view.id)
//                Timber.i("clickFilterHook  viewName $viewName")
//            }
//        }
//        joinPoint.proceed()
//    }


}