#!/bin/bash

# stop all docker
echo -e "\n\033[7;49;34mStopping Dockers....\033[0m"
docker stop $(docker ps -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

# remove networks
echo -e "\033[7;49;34mRemove networks...\033[0m"
docker network rm $(docker network ls -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

# remove containers
echo -e "\033[7;49;34mRemove containers...\033[0m"
docker rm $(docker ps -a -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"

# remove images
echo -e "\033[7;49;34mRemove images...\033[0m"
docker rmi $(docker images -q)
echo -e "\033[30;48;5;82mDone !\033[0m\n"
