# authguidance.mobilesample.webview

### Overview

* An Android Sample using OAuth 2.0 and Open Id Connect, referenced in my blog at https://authguidance.com
* **The goal of this sample is to integrate Secured Web Content into an Open Id Connect Secured Android App **

### Details

* See the **WebView Code Sample Overview** for a summary of behaviour
* See the **WebView Code Sample Instructions** for details on how to run the code

### Technologies

* Kotlin and Jetpack are used to develop a Single Activity App that uses web content from a Secured Cloud SPA

### Middleware Used

* The [AppAuth-Android Library](https://github.com/openid/AppAuth-Android) is used to implement the Authorization Code Flow (PKCE)
* Android Key Store + Shared Preferences are used to securely store a refresh token on the device after login
* AWS Cognito is used as a Cloud Authorization Server
* AWS API Gateway is used to host our sample OAuth 2.0 Secured API
