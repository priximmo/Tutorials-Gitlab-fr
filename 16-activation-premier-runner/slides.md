%title: GITLAB
%author: xavki


# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

Gitlab > settings > CI/CD > Runners

Specific/Shared runners

Doc : https://docs.gitlab.com/runner/install/index.html
Linux : https://docs.gitlab.com/runner/install/linux-manually.html

```
curl -LJO "https://gitlab-runner-downloads.s3.amazonaws.com/latest/deb/gitlab-runner_amd64.deb"
sudo dpkg -i gitlab-runner_amd64.deb
sudo gitlab-runner status
```

Rq : amd64/arm/arm64

----------------------------------------------------------------------------------------------------

# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

* Enregistrement du runner

```
export REGISTRATION_TOKEN="TPGHnuzJ3a4fyJ2RntRR"
gitlab-runner register --url http://xavki.gitlab/ --registration-token $REGISTRATION_TOKEN
```

* quelques arguments utiles

```
  --non-interactive
  --url "http://xavki.gitlab/"
  --registration-token "TPGHnuzJ3a4fyJ2RntRR"
  --executor "shell"
  --description "runner1"
  --tag-list "shell,runner"
  --run-untagged
  --locked="false"
```

----------------------------------------------------------------------------------------------------

# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

* Exemple de saisie

```
Runtime platform  arch=amd64 os=linux pid=12293 revision=de104fcd version=14.5.1
Running in system-mode.                            
Enter the GitLab instance URL (for example, https://gitlab.com/):
[http://xavki.gitlab/]: 
Enter the registration token:
[TPGHnuzJ3a4fyJ2RntRR]: 
Enter a description for the runner:
[runner1]: test
Enter tags for the runner (comma-separated):
simple,linux 
Registering runner... succeeded                     runner=TPGHnuzJ
Enter an executor: docker, ssh, virtualbox, docker+machine, custom, docker-ssh, parallels, shell, docker-ssh+machine, kubernetes:
shell
Runner registered successfully. Feel free to start it, but if it's running already the config should be automatically reloaded! 
```

----------------------------------------------------------------------------------------------------

# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

* Fichier de configuration de runner

```
cat /etc/gitlab-runner/config.toml 
concurrent = 1
check_interval = 0
[session_server]
  session_timeout = 1800
[[runners]]
  name = "test"
  url = "http://xavki.gitlab/"
  token = "ii7j5dugPta5Wzits7Cy"
  executor = "shell"
```

----------------------------------------------------------------------------------------------------

# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

* Acivation/Désactivation de gitlab-ci par défaut

```
vim gitlab.rb
gitlab_rails['gitlab_default_projects_features_builds'] = false
gitlab-ctl reconfigure
```

Rq : sinon par projet

----------------------------------------------------------------------------------------------------

# GITLAB : 16 - Activation & premier runner SHELL sur VM


<br>

* Premier .gitlab-ci.yaml

Project > CI/CD > Editor

Bloqué > run untag job

sinon utiliser les tags pour chaque jobs (liste)



https://docs.gitlab.com/runner/install/index.html
