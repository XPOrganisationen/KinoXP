## AsteriskXP
This project's raison d'être is to ease the pressure on ticket sales for a local movie theater - XPKino. The application binds to port 8080 on the host by default, but this can be changed in the compose file if that port is reserved on your machine. Change the first port in "app"->"ports". 

### Building and running the project
The easiest way to get started is to clone the project with `git clone` and navigate to the project folder. Then do `docker compose up <options>` and start selling tickets!
Alternatively, you may (docker) pull a prebuilt image from https://github.com/XPOrganisationen/asteriskXP/pkgs/container/asteriskxp, choosing either :latest or a specific hash. Then run the container as usual with `docker run <options> asteriskxp:<tag>`. To get the latest image, do
`docker pull ghcr.io/xporganisationen/asteriskxp:latest`
