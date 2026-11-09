@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  app startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and APP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\app.jar;%APP_HOME%\lib\cxf-rt-frontend-jaxws-4.1.3.jar;%APP_HOME%\lib\cxf-rt-transports-http-jetty-4.1.3.jar;%APP_HOME%\lib\cxf-rt-transports-http-4.1.3.jar;%APP_HOME%\lib\cxf-rt-features-logging-4.1.3.jar;%APP_HOME%\lib\cxf-rt-frontend-simple-4.1.3.jar;%APP_HOME%\lib\cxf-rt-ws-addr-4.1.3.jar;%APP_HOME%\lib\cxf-rt-bindings-soap-4.1.3.jar;%APP_HOME%\lib\cxf-rt-bindings-xml-4.1.3.jar;%APP_HOME%\lib\cxf-rt-databinding-jaxb-4.1.3.jar;%APP_HOME%\lib\cxf-rt-wsdl-4.1.3.jar;%APP_HOME%\lib\cxf-rt-ws-policy-4.1.3.jar;%APP_HOME%\lib\cxf-core-4.1.3.jar;%APP_HOME%\lib\logback-classic-1.5.18.jar;%APP_HOME%\lib\logback-core-1.5.18.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\jaxb-runtime-4.0.5.jar;%APP_HOME%\lib\woodstox-core-7.1.1.jar;%APP_HOME%\lib\xmlschema-core-2.3.1.jar;%APP_HOME%\lib\jaxb-core-4.0.5.jar;%APP_HOME%\lib\angus-mail-2.0.3.jar;%APP_HOME%\lib\angus-activation-2.0.2.jar;%APP_HOME%\lib\jakarta.xml.ws-api-4.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.2.jar;%APP_HOME%\lib\xml-resolver-1.2.jar;%APP_HOME%\lib\asm-9.8.jar;%APP_HOME%\lib\jetty-ee10-servlet-12.0.23.jar;%APP_HOME%\lib\jetty-session-12.0.23.jar;%APP_HOME%\lib\jetty-security-12.0.23.jar;%APP_HOME%\lib\jetty-server-12.0.23.jar;%APP_HOME%\lib\jetty-http-12.0.23.jar;%APP_HOME%\lib\jetty-io-12.0.23.jar;%APP_HOME%\lib\jetty-util-12.0.23.jar;%APP_HOME%\lib\slf4j-api-2.0.17.jar;%APP_HOME%\lib\jakarta.servlet-api-6.0.0.jar;%APP_HOME%\lib\stax2-api-4.2.2.jar;%APP_HOME%\lib\jakarta.xml.soap-api-3.0.2.jar;%APP_HOME%\lib\jakarta.mail-api-2.1.3.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.3.jar;%APP_HOME%\lib\txw2-4.0.5.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.2.jar;%APP_HOME%\lib\wsdl4j-1.6.3.jar;%APP_HOME%\lib\neethi-3.2.1.jar


@rem Execute app
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %APP_OPTS%  -classpath "%CLASSPATH%" org.example.ServidorSOAP %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable APP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%APP_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
