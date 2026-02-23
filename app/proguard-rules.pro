-keep class com.habit.tracker.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
