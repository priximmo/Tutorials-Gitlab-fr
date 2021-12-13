%title: GITLAB
%author: xavki


# GITLAB : 21 - Les Architectures de pipelines


<br>

Doc Gitlab : https://docs.gitlab.com/ee/ci/pipelines/pipeline_architectures.html

<br>

3 archis :

		* Basic pipelines

		* Direct Acyclic Graph (DAG) pipelines

		* Parent/enfants pipelines : place à votre imagination...


-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

BASIC PIPELINE

<br>

		Etapes		Stage1		Stage2		Stage3
		Jobs
						Job1		Job3			Job5
						Job2		Job4			Job6

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

```
stages:
  - stage1
  - stage2
job1:
  stage: stage1
  script:
    - echo "stage1 - job1"
job2:
  stage: stage1
  script:
    - echo "stage1 - job2"
job3:
  stage: stage2
  script:
    - echo "stage2 - job3"
job4:
  stage: stage2
  script:
    - echo "stage2 - job4"
...
```

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

DAG PIPELINE

```
		Stage1	>		Stage2	>		Stage3		

		Job1		>		Job2		>		Job3

		Job3		>		Job5		>		Job6
```

Note : ordre des jobs mais dépendances respectées

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

```
stages:
  - stage1
  - stage2
  - stage3
job1:
  stage: stage1
  script:
    - echo "stage1 - job1"
    - exit 1
job4:
  stage: stage1
  script:
    - echo "stage1 - job4"
job2:
  stage: stage2
  needs: [job1]
  script:
    - echo "stage2 - job2"
job5:
  stage: stage2
  needs: [job4]
  script:
    - echo "stage2 - job5"
...
```

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

PARENT/ENFANTS PIPELINE

* découper en différent gitlab-ci
* gestion de déclenchement suivant des répertoires spécifiques
* gestion de blocs


Pipeline

		caseA : stage1	stage2	stage3
		caseB : stage21	stage22	stage23

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

```
stages:
    - stage1
    - stage2
    - stage3
job1:
    stage: stage1
    script:
        - echo "caseA - job1"
job2:
    stage: stage2
    needs: [job1]
    script:
        - echo "caseA - job2"
job3:
    stage: stage3
    needs: [job2]
    script:
        - echo "caseA - job3"
```


-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

```
stages:
  - triggers

caseAjob:
  stage: triggers
  trigger:
    include: caseA/.gitlab-ci.yaml


caseBjob:
  stage: triggers
  needs: [caseAjob]
  trigger:
    include: caseB/.gitlab-ci.yaml
```

-----------------------------------------------------------------------------------------------------------------

# GITLAB : 21 - Les Architectures de pipelines


<br>

* règles de trigger

```
  rules:
    - changes:
        - caseA/*
```

