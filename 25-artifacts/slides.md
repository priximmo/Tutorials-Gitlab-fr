%title: GITLAB
%author: xavki


# GITLAB : 25 - Les ARTIFACTS


<br>

Deux modes de stockage pour gitlabci :

		* cache : sur les runners (cf vidéos précédentes)

		* artifacts : sur l'instance gitlab

Artifacts : disponible une fois le pipeline terminé

		* pour les jobs

		* pour les pipelines (en fin) : coverage


Attention stockage :
		
		* gitlab.yml (suppression par défaut)

Changement de localisation :

```
artifacts:
  enabled: true
  path: /mnt/storage/artifacts
```

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

Options des artifacts :

		* expire_in : temps de conservation

		* untracked : ajout des fichiers untracked à votre artifact

		* exclude : exclure certains fichiers

		* paths : répertoire complet

		* expose_as : mettre à disposition dans les MR

		* name : nom associé (stratégie comme cache)


-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* simple exemple

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - file.txt
```

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* avec expiration

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - file.txt
    expire_in: 1m
```

Note : non supprimé si pas de nouveaux artifacts

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* définir un nom

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - file.txt
    expire_in: 1m
    name: "$CI_JOB_NAME"
```

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* exclure des fichiers ou répertoires

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - ./
    expire_in: 1m
    name: "$CI_JOB_NAME"
    exclude: 
      - "*.yml"
      - ".git/**"
```

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* download via pipeline

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - file.txt
    expire_in: 1m
    name: $CI_JOB_NAME
job2:
  script:
    - echo "toto" > file2.txt
  artifacts:
    paths:
      - file2.txt
    expire_in: 1m
    name: $CI_JOB_NAME
```


-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>


* joindre à une MR

```
job1:
  script:
    - echo "toto" > file.txt
  artifacts:
    paths:
      - file.txt
    expire_in: 1m
    name: $CI_JOB_NAME
    expose_as: file_job
```

-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* exemple pratique :

```
variables: 
  POSTGRES_DB: postgres
  POSTGRES_USER: runner
  POSTGRES_PASSWORD: postgres
  POSTGRES_HOST_AUTH_METHOD: trust
  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository -Dstyle.color=always"
Build_Jar:
  image: maven:3.5.0-jdk-8
  services:
    - postgres
  script:
    - cd myapp1/
    - 'mvn ${MAVEN_OPTS} -Drevision=x.y.z-SUFFIX -B clean install ' 
  tags:
  - docker
  artifacts:
    paths:
      - myapp1/target/*.jar
    expire_in: 7 days
```


-----------------------------------------------------------------------------------

# GITLAB : 25 - Les ARTIFACTS


<br>

* ne conserver que le dernier par défaut

    Menu > Projects
    Settings > CI/CD
    Expand Artifacts
    Clear the Keep artifacts from most recent successful jobs checkbox

<br>

* remove artifact

		aller sur la page de job
		en haut à droite au-dessus des logs
		Erase job log
