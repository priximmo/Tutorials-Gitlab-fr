%title: GITLAB
%author: xavki


# GITLAB : 32	- CI : MultiProject Pipeline - Build Java

<br>

* modification du runner :

```
/usr/local/share/ca-certificates/registry.crt:/etc/ssl/certs/registry.crt:ro
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java


<br>

* déclaration des variables

```
variables:
  VARIABLES_FILE: ./variables.txt
  POSTGRES_DB: postgres
  POSTGRES_USER: runner
  POSTGRES_PASSWORD: postgres
  POSTGRES_HOST_AUTH_METHOD: trust
  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository -Dstyle.color=always"
cache:
  paths:
    - .m2
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java


<br>

* création des stages

```
stages:
    - test
    - build
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java


<br>

* job de test

```
Test:
  stage: test
  image: maven:3.5.0-jdk-8
  services:
    - postgres
  script:
    - mvn ${MAVEN_OPTS} -B clean test
    - MVN_VERSION=$(mvn --non-recursive help:evaluate -Dexpression=project.version | grep -v '\[.*')
    - echo $MVN_VERSION
    - echo "export MVN_VERSION=$MVN_VERSION" > ${VARIABLES_FILE}
  artifacts:
    paths:
      - $VARIABLES_FILE
  tags:
  - docker
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java

<br>

* job de build java

```
Build_Jar:
  stage: build
  image: maven:3.5.0-jdk-8
  services:
    - postgres
  script:
    - 'mvn ${MAVEN_OPTS} -Drevision=x.y.z-SUFFIX -B clean install'
  artifacts:
    paths:
      - target/*.jar
    expire_in: 7 days
  tags:
  - docker
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java

<br>

* job de build docker - docker/docker in docker

```
Build_docker:
  needs:
    - Test
    - Build_Jar
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
```

-------------------------------------------------------------------------------------------

# GITLAB : 32	- CI : MultiProject Pipeline - Build Java

<br>

* job de build docker - build et push


```
  script:
      - source ${VARIABLES_FILE}
      - docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY
      - docker build  -t $CI_REGISTRY_IMAGE:$CI_COMMIT_SHORT_SHA
                      -t $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG
                      -t $CI_REGISTRY_IMAGE:${MVN_VERSION}
                      .
      - docker push   $CI_REGISTRY_IMAGE:$CI_COMMIT_SHORT_SHA
      - docker push   $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG
      - docker push   $CI_REGISTRY_IMAGE:${MVN_VERSION}
  tags:
      - docker
```
