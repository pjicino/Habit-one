# 每日纠偏手册 · Android App

## 功能说明

- 每日红绿灯打卡
- 三层最小动作追踪
- 自动触发纠偏机制
- 呼吸引导
- 历史记录

## 推送通知

| 时间 | 内容 |
|------|------|
| 每天 21:00 | 📋 今日打卡提醒 |
| 每周日 09:00 | 📊 本周信号回顾 |

重启手机后自动恢复提醒。

---

## 编译步骤（约5分钟）

### 第一步：安装 Android Studio
https://developer.android.com/studio
下载安装，首次启动会自动下载 Android SDK。

### 第二步：打开项目
1. 打开 Android Studio
2. 点击 `File → Open`
3. 选择本文件夹（HabitApp）
4. 等待 Gradle 同步完成（约2分钟，需要网络）

### 第三步：编译 APK
1. 菜单栏点击 `Build → Build Bundle(s) / APK(s) → Build APK(s)`
2. 等待编译完成
3. 点击右下角弹出的 `locate` 找到 APK 文件
4. 路径：`app/build/outputs/apk/debug/app-debug.apk`

### 第四步：安装到手机
- 用数据线连接手机，直接从 Android Studio 点 ▶️ 运行
- 或将 APK 文件传到手机，手机端安装（需开启"允许未知来源"）

---

## 常见问题

**Gradle 同步失败？**
确保网络畅通，或使用代理。Gradle 需要下载依赖约 50MB。

**提示 SDK 版本问题？**
在 Android Studio 的 SDK Manager 中安装 Android 14 (API 34)。

**手机安装提示"未知来源"？**
设置 → 安全 → 允许安装未知来源应用。

---

编译完成后即可使用，无需任何服务器或账号。
