@echo off

SET CLASSPATH=lib\iglu-1.0.3.jar
SET CLASSPATH=%CLASSPATH%;lib\iglu-util-0.9.1.jar
SET CLASSPATH=%CLASSPATH%;lib\iglu-common-0.9.2.jar
SET CLASSPATH=%CLASSPATH%;lib\iglu-telnet-server-1.0.1.jar

@echo on
java -classpath %CLASSPATH% -Xms64m -Xmx256m org.ijsberg.iglu.samples.telnet.StandaloneServerConfiguration

pause
