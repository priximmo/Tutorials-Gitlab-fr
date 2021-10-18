#!/bin/bash


IP=$(hostname -I | awk '{print $2}')
GITLABURL=$1

echo "START - install gitlab - "$IP

echo "[1]: install gitlab"
apt-get update -qq >/dev/null
apt-get install -qq -y vim git wget curl >/dev/null
curl -L "https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh" | sudo bash
apt-get update -qq >/dev/null
export LC_CTYPE=en_US.UTF-8
export LC_ALL=en_US.UTF-8
apt-get install -y gitlab-runner

echo "END - install gitlab"

curl -sL "https://github.com/docker/compose/releases/download/1.25.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose 
usermod -aG docker gitlab-runner
sed -i s/"gitlab"/"gitlab $1"/g /etc/hosts



#sudo dpkg-reconfigure locales
