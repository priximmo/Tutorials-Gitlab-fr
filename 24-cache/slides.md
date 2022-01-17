%title: GITLAB
%author: xavki


# GITLAB : 24 - Le CACHE


<br>

Deux modes de stockage pour gitlabci :

		* cache

		* artifacts

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

CACHE

		* stocker et partager des libs et des packages

		* au niveau du runner (utiliser le même pour les jobs)

	  * réutilisable (intérêt du tag)

		* organiser éventuellement en fonction de vos workflows

		* cache distribué via du stockage objet S3 (multi-runners)

		* utilisation de clef pour définir diff caches

		* CACHE_FALLBACK_KEY (variable) pour définir une clef par défaut

		* désactivable sur demande ( cache:[] )

		* utilisation des ancres possibles

		* définition de policy possibles

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple simple (avec/sans fichier)

cache:
  - key:
      #files:
      #  - xavki.txt
    paths:
      - .lib
stages:
  - step1
  - step2
j1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo toto > .lib/xavki.txt
  tags:
    - docker
j2:
  stage: step2
  script: cat .lib/xavki.txt
  tags:
    - docker

Rq : clear cache

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules


<br>

* si ajout d'une branche

```
cache
  - key: 
    paths:
      - .lib
stages:
  - step1
  - step2
j1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo toto > .lib/test.txt
  tags:
    - docker
  only:
    - main
j2:
  stage: step2
  script: cat .lib/test.txt
  tags:
    - docker
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* solution = utilisation des variables comme clefs

```
cache:
  - key: $CI_COMMIT_REF_SLUG
    paths:
      - .lib
stages:
  - step1
  - step2
j1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo $CI_COMMIT_REF_SLUG > .lib/$CI_COMMIT_REF_SLUG.txt
  tags:
    - docker
j2:
  stage: step2
  script: 
    - cat .lib/$CI_COMMIT_REF_SLUG.txt
    - ls .lib/
  tags:
    - docker
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* idem pour les stage

```
stages:
  - step1
  - step2
j1-1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo $CI_COMMIT_REF_SLUG-$CI_JOB_STAGE > .lib/$CI_COMMIT_REF_SLUG-$CI_JOB_STAGE.txt
  cache:
    - key: $CI_JOB_STAGE-$CI_COMMIT_REF_SLUG
      paths:
        - .lib
  tags:
    - docker
j1-2:
  stage: step1
  script:
    - cat .lib/$CI_COMMIT_REF_SLUG-$CI_JOB_STAGE.txt
    - ls .lib/
  cache:
    - key: $CI_JOB_STAGE-$CI_COMMIT_REF_SLUG
      paths:
        - .lib
  tags:
    - docker
...
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

Plus généralement :

* par branche cache

cache:
  key: $CI_COMMIT_REF_SLUG

* par job et branche

cache:
  key: "$CI_JOB_NAME-$CI_COMMIT_REF_SLUG"

* par stage et branche

cache:
  key: "$CI_JOB_STAGE-$CI_COMMIT_REF_SLUG"

* pour le partager entre le job de diff branches

cache:
  key: $CI_JOB_NAME

* pour le partager partout (branches, jobs...)

cache:
  key: one-key-to-rule-them-all


---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules


CLEAN

<br>

* repartir avec un cache vide > changer de key

sinon

    On the top bar, select Menu > Projects and find your project.
    On the left sidebar, select CI/CD > Pipelines page.
    In the top right, select Clear runner caches. 

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

POLICIES

<br>

* différentes policy


    pull : récupère le cache (début de job) mais le met pas à jour
    push : ne récupère pas mais pousse dans le cache (find e job)
    pull-push (default) 

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* désactivé la mise à jour du cache (clean avant pour tester)

```
stages:
  - step1
  - step2
j1-1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo $CI_COMMIT_REF_SLUG-$CI_JOB_STAGE > .lib/$CI_COMMIT_REF_SLUG-$CI_JOB_STAGE.txt
  cache:
    - key: $CI_JOB_STAGE-$CI_COMMIT_REF_SLUG
      paths:
        - .lib
      policy: pull
  tags:
    - docker
j1-2:
  stage: step1
  script:
    - cat .lib/$CI_COMMIT_REF_SLUG-$CI_JOB_STAGE.txt
    - ls .lib/
  cache:
    - key: $CI_JOB_STAGE-$CI_COMMIT_REF_SLUG
      paths:
        - .lib
  tags:
    - docker
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* cache masqué

```
stages:
  - step1
.dependencies_cache:
  cache:
    - key: $CI_COMMIT_SHA
      paths:
        - .lib
j1-1:
  stage: step1
  script:
    - mkdir -p .lib 
    - echo "toto" > .lib/test.txt
  extends: .dependencies_cache
  tags:
    - docker

j1-2:
  stage: step1
  script:
    - ls .lib 
    - cat .lib/test.txt
  extends: .dependencies_cache
  tags:
    - docker
```

---------------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple  service/cache

```
variables:
  VARIABLES_FILE: ./myapp1/variables.txt
  POSTGRES_DB: postgres
  POSTGRES_USER: runner
  POSTGRES_PASSWORD: postgres
  POSTGRES_HOST_AUTH_METHOD: trust
  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository -Dstyle.color=always"
cache:
  paths:
    - .m2
stages:
    - test
    - build_jar
Test:
  stage: test
  image: maven:3.5.0-jdk-8
  services:
    - postgres
  script:
    - mvn ${MAVEN_OPTS} -B clean test
    - MVN_VERSION=$(mvn --non-recursive help:evaluate -Dexpression=project.version | grep -v '\[.*')
    - echo $MVN_VERSION
    - echo "export MVN_VERSION=$MVN_VERSION" > variables.txt
  tags:
  - docker
Build_Jar:
  stage: build_jar
  image: maven:3.5.0-jdk-8
  services:
    - postgres
  script:
    - 'mvn ${MAVEN_OPTS} -Drevision=x.y.z-SUFFIX -B clean install ' 
  tags:
  - docker
```

