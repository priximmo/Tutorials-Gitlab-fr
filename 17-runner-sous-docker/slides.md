%title: GITLAB
%author: xavki


# GITLAB : 17 - lancer un runner sous docker


<br>

Gitlab > settings > CI/CD > Runners

Specific/Shared runners

Doc : https://docs.gitlab.com/runner/install/index.html
Linux : https://docs.gitlab.com/runner/install/linux-manually.html

Note : dns vers l'instance gitlab

```
mkdir -p /data/
docker run -d  \
   --name gitlab-runner \
   --restart always \
   -v /var/run/docker.sock:/var/run/docker.sock \
   -v /data/gitlab-runner:/etc/gitlab-runner \
   gitlab/gitlab-runner:latest
```

-----------------------------------------------------------------------------------------

# GITLAB : 17 - lancer un runner sous docker


docker exec -it gitlab-runner gitlab-runner register

-----------------------------------------------------------------------------------------

# GITLAB : 17 - lancer un runner sous docker
Test:
  stage: test
  image: debian:latest
  script:
    - echo "Start..."
    - sleep 60
    - echo "ended !!"

