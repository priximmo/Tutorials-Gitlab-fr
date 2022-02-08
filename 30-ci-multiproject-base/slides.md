%title: GITLAB
%author: xavki


# GITLAB : 30 - CI : MultiProject Pipeline - Base

<br>

Objectif : 

		* à part d'un projet déclencher le pipeline d'un autre projet

		* transmettre une variable d'un gitlab-ci à un autre

Doc : https://docs.gitlab.com/ee/ci/pipelines/multi_project_pipelines.html


-------------------------------------------------------------------------------------------------

# GITLAB : 30 - CI : MultiProject Pipeline - Base


<br>

* job amont (déclenché) - projet : pipeline2


```
image: debian:latest
job1:
    except:
        changes:
            - ".gitlab-ci.*"
    only:
        - triggers
    script:
        - echo $VAR1
        - echo $VAR2
    tags:
        - docker
```

-------------------------------------------------------------------------------------------------

# GITLAB : 30 - CI : MultiProject Pipeline - Base


<br>

* job aval (déclencheur) - pojet : pipeline1

```
stages:
    - s1
    - s2
j1:
    stage: s1
    image: docker
    script:
        - echo "Mon premier job !!!"
    tags:
        - docker
    except:
        changes:
            - ".gitlab-ci.*"
j2:
    variables:
        VAR1: "variable1"
        VAR1: "variable1"
    stage: s2
    trigger:
        project: xavki/pipeline2
        branch: main
        strategy: depend
    except:
        changes:
            - ".gitlab-ci.*"
```

-------------------------------------------------------------------------------------------------

# GITLAB : 30 - CI : MultiProject Pipeline - Base


<br>

Prochaine vidéo :

		pipeline 1 > pipeline 2 (ansible db/user Postgres)
