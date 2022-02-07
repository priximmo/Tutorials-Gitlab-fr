%title: GITLAB
%author: xavki


# GITLAB : 29 - CI : build + push

<br>

Build d'une image 

		* utilisation de docker in docker

		* attention au certificat de la registry

---------------------------------------------------------------------------------------------

# GITLAB : 29 - CI : build + push

<br>

* ajout du certificat sur la vm du runner

```
openssl s_client -showcerts -connect registry.xavki.gitlab:443 < /dev/null | sed -ne '/-BEGIN CERT
IFICATE-/,/-END CERTIFICATE-/p' > /usr/local/share/ca-certificates/registry.crt
update-ca-certificates
```

<br>

* changement de la conf du runner

```
    volumes = ["/cache", "/etc/ssl/certs:/etc/ssl/certs:ro"]
```

```
docker restart gitlab-runner
```

---------------------------------------------------------------------------------------------

# GITLAB : 29 - CI : build + push

<br>

````
stages:
    - build
build image:
    stage: build
    image: docker
    services:
        - name: docker:dind
          alias: docker
    variables:
        DOCKER_BUILDKIT: "1"
        DOCKER_DRIVER: overlay2
        DOCKER_HOST: tcp://docker:2375
        DOCKER_TLS_CERTDIR: ""
    script:
        - docker build  -t $CI_REGISTRY_IMAGE:$CI_COMMIT_SHORT_SHA
                        -t $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG
                        .
        - docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY
        - docker push   $CI_REGISTRY_IMAGE:$CI_COMMIT_SHORT_SHA
        - docker push   $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG
    tags:
        - docker
```
