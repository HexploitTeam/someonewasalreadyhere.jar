@rem Gradle startup script for Windows
@rem Licensed under the Apache License, Version 2.0.

@if "%DEBUG%"=="" @echo off
@rem Set local scope for the variables
setlocal

set APP_HOME=%~dp0

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Resolve JAVA_HOME
set JAVACMD=java
if defined JAVA_HOME set JAVACMD=%JAVA_HOME%\bin\java.exe

@rem Execute Gradle
"%JAVACMD%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
endlocal
