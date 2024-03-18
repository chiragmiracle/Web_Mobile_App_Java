# Web to Android Mobile App

## This is MiracleWebView application with Advance functionalities

# Screenshots

![ANDROID WEBVIEW](https://imgur.com/AhJesno.jpeg)

# Configurations

## Change Package name

- Change package name in  ```app/build.gradle```

```gradle
applicationId "com.miracle.webmobile"
```

- Replace with your package name

```gradle
applicationId "your.package.name"
```

## Replace Logo

- Replace logo **logo.png** in ```app/src/main/res/drawable/``` directory

## Website URL

- Change url variable in ```app/src/main/java/com/miracle/webmobile/MiracleWebActivity.java```

```java
private static String URL="https://www.theandroid-mania.com/";
```

## App Credentials

- Change App Name  ```app/src/main/res/values/strings.xml```

```xml

<string name="app_name">Web Mobile App</string>
```

## Push Notifications

- First thing you need to do is go to **(https://firebase.google.com/)** and make an account to gain
  access to their console. After you gain access to the console you can start by creating your first
  project.
  ![FIREBASE NEW PROJECTS](https://imgur.com/SwpWqod.png)

- Give the package name of your project (mine is **com.miracle.webmobile**) in which you are going
  to integrate the Firebase. Here the **google-services.json** file will be downloaded when you
  press add app button.
  ![FIREBASE ADD JSON](https://imgur.com/yycT5G4.png)

- Project Overview in open Project Settings.
  ![FIREBASE SETTING](https://imgur.com/WMSSdPg.png)

- Open services Account and click Generate new private key then Generate Key.
  ![FIREBASE GENERATE KEY](https://imgur.com/oGCuvoh.png)

- Paste the downloaded json file under Service Account json in Onesignal.
  ![ONESIGNAL ADD](https://imgur.com/pENV0Ux.png)

- Copy your App ID and `app\src\main\java\com\miracle\webmobile\MyApplication.java` paste ID in

```java
public static String ONESIGNAL_APP_ID="########-####-####-####-############";
```

![ONESIGNAL SET APP ID](https://imgur.com/LRhKDlm.png)



