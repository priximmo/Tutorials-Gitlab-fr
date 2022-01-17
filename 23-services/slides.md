%title: GITLAB
%author: xavki


# GITLAB : 23 - Les Services


<br>

Doc : https://docs.gitlab.com/ee/ci/services/


* services nécessaires dans le cadre de la CI : build, test
		* base de données
		* réponse d'api...

* par défaut : mysql, postgres, redis, gitlab

-----------------------------------------------------------------------------------------------

# GITLAB : 23 - Les Services


<br>

* exemple postgresql

```
variables:
  POSTGRES_DB: postgres
  POSTGRES_USER: runner
  POSTGRES_PASSWORD: postgres
  POSTGRES_HOST_AUTH_METHOD: trust
stages:    
  - test
Testing:
  stage: test
  image: debian:latest
  services:
    - postgres
  script:
    - apt update && apt install -y postgresql-client
    - PGPASSWORD=$POSTGRES_PASSWORD psql -U $POSTGRES_USER -h postgres -d postgres -c "\l"
  tags:
  - docker
```

-----------------------------------------------------------------------------------------------

# GITLAB : 23 - Les Services


<br>


```
  services:
    - name: postgres:14.1-alpine
      alias: mydb
```

-----------------------------------------------------------------------------------------------

# GITLAB : 23 - Les Services


<br>


```
Testing:
  stage: test
  image: debian:latest
  services:
    - name: postgres:14.1-alpine
      alias: mydb1
    - name: postgres:10.19-stretch
      alias: mydb2
    - name: postgres:9-alpine3.14
      alias: mydb3
  script:
    - apt update && apt install -y postgresql-client
    - PGPASSWORD=$POSTGRES_PASSWORD psql -U $POSTGRES_USER -h mydb1 -d postgres -c "SELECT version();"
    - PGPASSWORD=$POSTGRES_PASSWORD psql -U $POSTGRES_USER -h mydb2 -d postgres -c "SELECT version();"
    - PGPASSWORD=$POSTGRES_PASSWORD psql -U $POSTGRES_USER -h mydb3 -d postgres -c "SELECT version();"
  tags:
  - docker
```

-----------------------------------------------------------------------------------------------

# GITLAB : 23 - Les Services


<br>

* pour mysql


```
variables:
  MYSQL_DATABASE: "db_name"
  MYSQL_ROOT_PASSWORD: "dbpass"
  MYSQL_USER: "username"
  MYSQL_PASSWORD: "dbpass"
  MYSQL_HOST: mysql
stages:    
  - test
Testing:
  image: debian:latest
  services:
    - mysql
  stage: test
  script:
    - apt update && apt install -y mariadb-client
    - echo "SHOW tables;" | mysql -u root -p"$MYSQL_ROOT_PASSWORD" -h mysql "${MYSQL_DATABASE}"
    - echo "CREATE TABLE toto (field1 int);" | mysql -u root -p"$MYSQL_ROOT_PASSWORD" -h mysql "${MYSQL_DATABASE}"
    - echo "SHOW tables;" | mysql -u root -p"$MYSQL_ROOT_PASSWORD" -h mysql "${MYSQL_DATABASE}"
  tags: 
    - docker
```

-----------------------------------------------------------------------------------------------

# GITLAB : 23 - Les Services


<br>

* autre image

```
Testing:
  stage: test
  image: debian:latest
  services:
    - name: nginx:latest
      alias: mynginx
  script:
    - apt update -qq 2>&1 >/dev/null && apt -qq install -y curl 2>&1 >/dev/null
    - curl mynginx
  tags:
  - docker
```
