%title: GITLAB
%author: xavki


# GITLAB : 28 - La REGISTRY Docker

<br>

* problématique :

		* activer la registry

		* bénéficier d'une registry vérified via https

		* génération d'un certificat auto-signé

------------------------------------------------------------------------------

# GITLAB : 28 - La REGISTRY Docker


<br>

* configuration de gitlab

```
gitlab_rails['gitlab_default_projects_features_container_registry'] = true

registry_external_url 'https://registry.xavki.gitlab'

gitlab_rails['registry_enabled'] = true
gitlab_rails['registry_host'] = "registry.xavki.gitlab"


registry_nginx['enable'] = true
registry_nginx['listen_port'] = 443
registry_nginx['ssl_certificate'] = "/etc/gitlab/registry.xavki.gitlab.cert"
registry_nginx['ssl_certificate_key'] = "/etc/gitlab/registry.xavki.gitlab.key"
```

------------------------------------------------------------------------------

# GITLAB : 28 - La REGISTRY Docker


<br>

* génération du certificat auto-signé

```
sudo openssl req   -newkey rsa:4096 -nodes -sha256 -keyout registry.xavki.gitlab/registry.xavki.gitlab.key  -addext "subjectAltName = DNS:registry.xavki.gitlab" -x509 -days 365 -out registry.xavki.gitlab/registry.xavki.gitlab.cert
```

<br>

* reconfigure de gitlab

```
gitlab-ctl reconfigure
```

------------------------------------------------------------------------------

# GITLAB : 28 - La REGISTRY Docker


<br>

* gestion du cert auto-signé pour un client

```
docker login registry.xavki.gitlab
openssl s_client -showcerts -connect registry.xavki.gitlab:443 < /dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > ca.crt
cat ca.crt
sudo cp ca.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates
systemctl restart docker
docker login registry.xavki.gitlab
```
