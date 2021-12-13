%title: GITLAB
%author: xavki


# GITLAB : 20 - Les Variables


<br>

* très importantes et utiles :
		* secrets
		* sha1 commit
		* taguer des images
		* partager entre les jobs

* différents types de variables :
		* les variables prédéfinies
		* du fichier gitlab-ci : local vs global (job)
		* du projet (settings > cicd)
		* du groupe
		* de l'instance

--------------------------------------------------------------------------

# GITLAB : 19 - Les Variables



VARIABLES PREDEFINIES


<br>

* liste : https://docs.gitlab.com/ee/ci/variables/predefined_variables.html

<br>

* sans stage (juste des jobs)

```
start-job: 
  tags:
    - shell     
  script:
    - echo "Start..."
    - echo "$CI_JOB_ID"
end-job:
  tags:
    - shell     
  script:
    - echo "ended !!"  
```

--------------------------------------------------------------------------

# GITLAB : 19 - Les Variables

VARIABLES GILAB-CI

<br>

* variables globales

```
variables:
  GLOBAL_VAR: "Hello"
start-job: 
  tags:
    - shell     
  script:
    - echo "Start..."
    - echo "$GLOBAL_VAR"
    - echo "ended !!"
```

--------------------------------------------------------------------------

# GITLAB : 19 - Les Variables

<br>

* variables locales (à un job)

```
variables:
  GLOBAL_VAR: "Hello"
start-job: 
  tags:
    - shell
  variables:
    LOCAL_VAR: "Hello Xavki !!" 
  script:
    - echo "Start..."
    - echo "$LOCAL_VAR"
    - echo "ended !!"
```

--------------------------------------------------------------------------

# GITLAB : 19 - Les Variables

<br>

* local vs global

```
variables:
  VAR: "Hello"
start-job: 
  tags:
    - shell
  variables:
    VAR: "Hello Xavki !!" 
  script:
    - echo "Start..."
    - echo "$VAR"
    - echo "ended !!"
```

--------------------------------------------------------------------------

# GITLAB : 19 - Les Variables

<br>

GROUP & PROJET & FORM

* settings > CICD > Variables

```
start-job: 
  tags:
    - shell
  script:
    - echo "Start..."
    - echo "$PIPELINE_VAR"
    - echo "ended !!"
```
