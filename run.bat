@echo off

if "%1"=="full" (
    echo Compilation et installation du projet...
    call mvnw.cmd clean install -DskipTests
    if %ERRORLEVEL% NEQ 0 (
        echo Erreur lors de la compilation
        exit /b %ERRORLEVEL%
    )
    echo.
)

echo Lancement de l'application...
java -jar target\excercice1-0.0.1-SNAPSHOT.jar
