%title: GITLAB
%author: xavki


# GITLAB : 27 - Les ANCRES pour gérer la répétition

<br>

Pour l'exemple :

		* duplication du serveur target1

		* user spécifique

		* ip spécifique

YAML > les ancres

----------------------------------------------------------------------

# GITLAB : 27 - Les ANCRES pour gérer la répétition

<br>

* définition d'un ancre

```
.ssh: &ssh
  - cmd1
  - cmd2
...
```

<br>

* utilisation d'un ancre

```
    before_script: *ssh
```


