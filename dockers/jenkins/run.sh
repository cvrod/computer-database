#!/bin/bash

# stop all docker
echo -e "\n\033[7;49;34mStopping Dockers....\033[0m"
docker stop $(docker ps -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

# remove containers
echo -e "\033[7;49;34mRemove containers...\033[0m"
docker rm $(docker ps -a -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

# remove networks
echo -e "\033[7;49;34mRemove networks...\033[0m"
docker network rm $(docker network ls -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

echo -e "\033[7;49;34mLaunching Jenkins test container...\033[0m"
docker run --name jenkins_container --rm -v jenkins-save:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -p 8080:8080 jenkins_image
echo -e "\033[30;48;5;82mDone !\033[0m\n"
