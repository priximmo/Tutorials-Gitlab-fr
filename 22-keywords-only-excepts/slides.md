%title: GITLAB
%author: xavki


# GITLAB : 22 - KeyWords : only, excepts & rules


<br>

Doc : https://docs.gitlab.com/ee/ci/yaml/#only--except

* Keyword de jobs

* only : quand un job doit être lancé

* except : quand un job NE DOIT PAS être lancé

```
only and except are not being actively developed. 
rules is the preferred keyword to control when to add jobs to pipelines.
```

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

Applicable à 4 sous keywords :

		* refs

		* variables

		* changes

		* kubernetes 

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>


REFS

* nom de branches (nom ou regex) ou type de pipeline (merge_requests, api, schedules...)

* 2 types d'écriture :

```
  only:
    refs:
      - branches
```

ou

```
  only:
    - branches
```

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple :

```
stages:
    - build
    - test
    - deploy
build:
    stage: build
    script:
        - echo "build"
    only:
        refs:
            - tags
test:
    stage: test
    script:
        - echo "test"
    except:
        refs:
            - tags
deploy:
    stage: deploy
    script:
        - echo "deploy"
    except:
        refs:
            - /^xp-.*$/
```

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>


VARIABLES

* utilisation de variables définies et leurs expressions dans la CI/CD

```
$VARIABLE == "some value"		# à une valuer
$VARIABLE_1 == $VARIABLE_2	# à une autre variable
$VARIABLE == null						# si la variable est définie
$VARIABLE 									# si la variable existe
$VARIABLE =~ /^content.*/		# un pattern
$VARIABLE1 =~ /^content.*/ || $VARIABLE2 =~ /thing$/ && $VARIABLE3 # combinaison
$CI_COMMIT_BRANCH == "my-branch" || (($VARIABLE1 == "thing" || $VARIABLE2 == "thing") && $VARIABLE3)
```

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple

```
build:
    stage: build
    script:
        - echo "build"
    only:
        variables:
            - $VARIABLE1 == "xavki" && $VARIABLE2 == "xavki"
```


-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple

```
build:
    stage: build
    script:
        - echo "build"
    only:
        variables:
            - $VARIABLE1 == "xavki"
        refs:
            - /^xp-.*$/
```


-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

CHANGES

* observation une partie spécifique du dépôt (répertoires, fichiers

* prend des valeurs :
		* chemin de fichiers
		* avec wildcard case1/*/*
		* avec du glob case1/*/*.{yaml,yml}

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>

* exemple

```
    except:
        changes:
            - ".gitlab-ci.*"
```

-----------------------------------------------------------------------------------------------------

# GITLAB : 22 - KeyWords : only, excepts & rules

<br>


KUBERNETES

* si le service kubernetes de gitlab est activé

```
deploy:
  only:
    kubernetes: active
```
