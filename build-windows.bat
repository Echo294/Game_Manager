@echo off
setlocal
if not exist out mkdir out
javac -d out Main.java Game.java PhysicalGame.java GameManager.java
if errorlevel 1 exit /b 1
jar --create --file GameVault.jar --main-class Main -C out .
if errorlevel 1 exit /b 1
jpackage --type app-image --name GameVault --input . --main-jar GameVault.jar --main-class Main --dest dist
if errorlevel 1 exit /b 1
echo App created in dist\GameVault
