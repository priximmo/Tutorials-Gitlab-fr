%title: GITLAB
%author: xavki


# GITLAB : 18 - Runner kubernetes


<br>

* installation d'un mini cluster kubernetes

```
sudo snap install microk8s --classic
sudo usermod -a -G microk8s vagrant
sudo chown -f -R vagrant ~/.kube
microk8s kubectl config view --raw > ~/.kube/config
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 18 - Runner kubernetes

* installation de kubectl

```
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee -a /etc/apt/sources.list.d/kubernetes.list
sudo apt update && sudo apt install -y kubectl
kubectl get nodes
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 18 - Runner kubernetes

* installation de helm

```
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
sudo chmod +x get_helm.sh
./get_helm.sh
helm list
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 18 - Runner kubernetes


* installation du dépôt de charts gitlab

```
sudo vim /etc/hosts
helm repo add gitlab https://charts.gitlab.io
helm repo update
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 18 - Runner kubernetes


* édit des valeurs

```
cat values.yaml (https://gitlab.com/gitlab-org/charts/gitlab-runner/blob/main/values.yaml)
gitlabUrl: "http://xavki.gitlab"
runnerRegistrationToken: "ra7jHgHxjKqF1HsU-ios"
metrics:
  enabled: false
runners:
  config: |
    [[runners]]
      pre_clone_script = "echo '192.168.12.40 xavki.gitlab' >> /etc/hosts"
      name = "kub1"
      executor = "kubernetes"
      [runners.kubernetes]
        namespace = "gitlab-runner"
        poll_timeout = 600
        cpu_request = "1"
        service_cpu_request = "200m"
        [[runners.kubernetes.volumes.host_path]]
            name = "docker"
            mount_path = "/var/run/docker.sock"
            host_path = "/var/run/docker.sock"
hostAliases:
  - ip: "192.168.12.40"
    hostnames:
    - "xavki.gitlab"
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 18 - Runner kubernetes

* installation

```
kubectl create ns gitlab-runner
helm install --namespace gitlab-runner gitlab-runner gitlab/gitlab-runner -f values.yaml
```

* test
