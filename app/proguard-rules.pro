# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# javascript interface function
-keepclassmembers class * {
	@android.webkit.JavascriptInterface <methods>;
}

-keepattributes Signature,InnerClasses
-keepattributes SourceFile,LineNumberTable
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes *Annotation*

-renamesourcefileattribute SourceFile
-dontskipnonpubliclibraryclassmembers
-dontusemixedcaseclassnames
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable,!class/unboxing/enum
-repackageclasses ''
-verbose

-keep class com.google.**
-dontwarn com.google.**
-keep class sun.misc.**
-dontwarn sun.misc.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**
-dontwarn android.support.v4.**

-dontwarn org.xbill.**
-dontwarn org.slf4j.**
-dontwarn org.xmlpull.v1.**

-dontwarn com.linecorp.line.protocol.thrift.search.** #temporally (I don't need shop)

# 사용하지 않는 androidx.renderscript 를 못찾아서 빌드 실패하므로, 무시한다.
-dontwarn jp.wasabeef.glide.transformations.**

# 사용하지 않는 class androidx.legacy.app.FragmentCompat 를 못찾아서 빌드 실패하므로, 무시한다.
-dontwarn net.quikkly.android.ui.ScanFragment

-keep public class * extends sun.misc.Unsafe
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService {*;}
-keep public class android.support.v4.** {*;}
-keep class org.xmlpull.v1.** { *; }

#http://stackoverflow.com/questions/20651575/android-release-apk-crash-with-java-lang-assertionerror-impossible-in-java-lang
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep class **.R$* {
    *;
}

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * extends android.webkit.WebChromeClient { *; }

# Google AdMob
-keep public class com.google.android.gms.ads.** {
   public *;
}

-keep public class com.google.ads.** {
   public *;
}

# For facebook sdk
-keep class com.facebook.** { *; }

# For RxJava:
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-useuniqueclassmembernames

-keep class com.campmobile.snowcamera.BuildConfig { *; }

-keep public class com.google.gson.JsonParser {*;}
-keep public class com.google.gson.JsonObject {*;}
-keep public class com.google.gson.Gson {*;}
-keep public class com.google.gson.GsonBuilder {*;}

# sonic-ndk
-keep class org.vinuxproject.sonic.** { *; }

-dontwarn com.google.gson.internal.**

# For CameraParam.to(Intent)

#--------------- begin : com-bumptech-glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#--------------- end : com-bumptech-glide  ----------

#--------------- begin : OverScroller uses removed (in 23) android api : android.util.FloatMath ----------
-dontwarn it.sephiroth.android.library.widget.**
-dontwarn com.nhncorp.nelo2.**
#--------------- end : OverScroller uses removed (in 23) android api : android.util.FloatMath ----------

-keepclassmembers class ** {
    public void onEvent(**);
}

#--------------- begin : butterknife ----------
-dontwarn butterknife.internal.**

# Retain generated class which implement ViewBinder.
-keep public class **$$ViewBinder { *; }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinder.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
#--------------- end : butterknife ----------

#--------------- begin : apt ----------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe
#--------------- end : apt ----------

##--------------- begin : SenseTime ----------
-keep class com.sensetime.** {*;}
-keep interface com.sensetime.** {*;}
-keep class com.sun.jna.** { *; }
-keep class java.lang.String {*;}
-keep class android.graphics.RectF {*;}
-keep class com.campmobile.nb.common.object.model.AbsStickerItem {*;}
-keep class com.campmobile.nb.common.object.model.VideoStickerItem {*;}
-keep class com.campmobile.nb.common.object.sticker.StickerDefinition {*;}

-dontwarn com.sun.jna.**
##--------------- end : SenseTime ----------

##--------------- begin : Moodelizer ----------
-keep class com.moodelizer.** {*;}
-keep interface com.moodelizer.** {*;}
##--------------- end : Moodelizer ----------

##--------------- begin : Parfait ----------
##--------------- end : Parfait ----------

#--------------- begin : netty ----------
# Get rid of warnings about unreachable but unused classes referred to by Netty
-dontwarn org.jboss.netty.**

#Some Factory that seemed to be pruned
-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdater {*;}
-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdaterImpl{*;}

#Some important internal fields that where removed
-keep class org.jboss.netty.channel.DefaultChannelPipeline{volatile <fields>;}

#A Factory which has a static factory implementation selector which is pruned
-keep class org.jboss.netty.util.internal.QueueFactory{static <fields>;}

#Some fields whose names need to be maintained because they are accessed using inflection
-keepclassmembernames class org.jboss.netty.util.internal.**{*;}
#--------------- end : netty ----------


#--------------- begin : wechat ----------
-keep class com.tencent.mm.sdk.** {*;}
-keep class com.campmobile.snowcamera.Util{*;}
#--------------- end : wechat ----------

#--------------- begin : meipai ----------
-keep class com.meitu.meipaimv.sdk.modelmsg.** {*;}
-keep class com.meitu.meipaimv.sdk.modelbase.** {*;}
-keep class com.meitu.meipaimv.sdk.openapi.** {*;}
#--------------- end : meipai ----------

#--------------- begin : talkingdata ----------
-keep class com.talkingdata.sdk.** {*;}
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.** {  public protected *;}

-dontwarn com.tendcloud.tenddata.**
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**
#--------------- end : talkingdata ----------

#--------------- begin : retrofit ----------
-dontwarn okio.**
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Signature
-keepattributes Exceptions
# Retain Request Body
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
#--------------- end : retrofit ----------

#--------------- begin : QQ ----------
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
#--------------- end : QQ ----------

#--------------- begin : gson ----------
# Application classes that will be serialized/deserialized over Gson

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: gson  ----------

#--------------- Begin : kuru ----------
##-------------- End : kuru  ----------

#--------------- Begin : weibo ----------
-keep class com.sina.weibo.sdk.** { *; }
##-------------- End : weibo  ----------

#--------------- Begin : appsflyer ----------
-dontwarn com.appsflyer.**
-dontwarn com.android.installreferrer
-keep public class com.google.firebase.iid.FirebaseInstanceId {
  public *;
}
##-------------- End : appsflyer  ----------


#--------------- Begin : jackson ----------
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *;
}
##-------------- End : jackson  ----------

#--------------- Begin : Android architecture components ----------
# Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}

-keep class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclassmembers class android.arch.** { *; }
-keep class android.arch.** { *; }
-dontwarn android.arch.**
#--------------- End : Android architecture components ----------

#--------------- Begin : Tensorflow -----------
-keep class org.tensorflow.** { *; }
#--------------- End : Tensorflow -----------

#--------------- Begin : quikkly -----------
-dontwarn com.caverock.androidsvg.SVG
-dontwarn android.support.v13.app.FragmentCompat
-keep class net.quikkly.** { *; }
#--------------- End : quikkly -----------

#--------------- Begin : okhttp3 -----------
# https://github.com/square/okhttp/blob/master/okhttp/src/main/resources/META-INF/proguard/okhttp3.pro

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
#--------------- End : okhttp3 -----------

#--------------- Begin : firebase -----------
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
#--------------- End: firebase -----------

#--------------- Begin : GeTui -----------
-dontwarn com.getui.**
-keep class com.getui.** { *; }
-keep class com.igexin.** { *; }
-keep class com.coloros.** { *; }
-keep class com.vivo.** { *; }
-keep class com.hianalytics.** { *; }
-keep class com.huawei.** { *; }
-keep class com.meizu.** { *; }
-keep class com.xiaomi.** { *; }
#--------------- End : GeTui -----------

#--------------- Begin : Json Parse -----------
#--------------- End : Json Parse -----------


#--------------- Begin : samsung camera sdk -----------
-keep class com.samsung.android.sdk.camera.** {*;}
#--------------- End : samsung camera sdk -----------

#--------------- Begin : androidx -----------
-keep class androidx.core.content.ContextCompat { *; }
#--------------- End : androidx -----------

#--------------- Begin : chaopai -----------
-keep class com.campmobile.chaopai.bean.** {*;}
-keep class com.campmobile.chaopai.business.kaji.bean.** {*;}
-keep class com.campmobile.chaopai.business.kaji.view.** {*;}
-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
           <init>(android.view.View);
}
#--------------- End : chaopai -----------

#--------------- Begin : union ad --------
-keep class com.qq.e.**{*;}
-keep class com.unionad.library.** {*;}
-keep class com.infinite.downloader.** {*;}
-keep class com.iflytek.** {* ;}
-keep class android.support.v4.**{public * ;}
#--------------- End : union ad --------

#-------------- begin: msa -------------
-keep class com.bun.miitmdid.core.** {*;}
#-------------- end: mas ---------------

#--------------- Begin : samsung foldable sdk  --------
-keep class com.samsung.android.sdk.foldstate.** {*;}
#--------------- End : samsung foldable sdk  --------
