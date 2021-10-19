%title: GITLAB
%author: xavki


# GITLAB : INSTALLATION


<br>

Sans docker

```
apt-get update -qq
apt-get install -qq -y vim git wget curl >/dev/null
```

```
curl -sS https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.deb.sh | sudo bash
apt-get update -qq
export LC_CTYPE=en_US.UTF-8
export LC_ALL=en_US.UTF-8
```

```
apt install -y gitlab-ce
```

```
sed -i s/"gitlab.example.com"/"${1}"/g /etc/gitlab/gitlab.rb
gitlab-ctl reconfigure
```


-------------------------------------------------------------------------------------------------------

# GITLAB : INTRODUCTION

<br>

Avec docker & docker-compose :

cf fichier joint



