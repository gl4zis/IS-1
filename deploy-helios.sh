#!/bin/zsh

PROJECT_PATH="$HOME/IdeaProjects/IS-lab1/"

SERVER_PATH="$PROJECT_PATH/server"
CLIENT_PATH="$PROJECT_PATH/client"

WAR_PATH="$SERVER_PATH/build/libs/ROOT.war"
FRONT_BUILD_PATH="$CLIENT_PATH/dist/client/browser"
DB_SCRIPT_PATH="$SERVER_PATH/src/main/resources"

cd "$SERVER_PATH"
gradle war

cd "$CLIENT_PATH"
ng build --base-href="/~s367370/is/"

scp "$WAR_PATH" helios:~/wildfly-27/standalone/deployments
scp -r "$FRONT_BUILD_PATH" helios:~/public_html/is
scp "$DB_SCRIPT_PATH/create.sql" helios:~/IS
scp "$DB_SCRIPT_PATH/insert.sql" helios:~/IS