@echo off
cd src
javac ImageGUI.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)
move *.class ..\bin
jar cmf ..\bin\META-INF\MANIFEST.MF ProcessadorDeImagens.jar .\ ..\img ..\bin
move ProcessadorDeImagens.jar ..\bin
cd ..\bin
java -jar ProcessadorDeImagens.jar
cd ..