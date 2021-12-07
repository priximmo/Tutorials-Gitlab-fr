%title: GITLAB
%author: xavki


# GITLAB : 12 - Flows


<br>

v<major>.<minor>.<patch>

Where

    major : is a version number where you introduced breaking modifications (modifications of your new version are NOT compatible with previous versions);
    minor : is a version number that is compatible with previous versions;
    patch : is an increment for a bug fix or a patch fix done on your software.

<br>

Branch :

		* ligne de développement d'un projet

		* features = temporaires le temps de développer

		* main / preprod / staging / development ...

		* organisation des branches en flow

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

<br>

Flow : 

		* comment faire évoluer une évolution vers la production

		* suivant le type d'évolution : fix / bug / développement...

		* gitflow, gitlab flow...

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

<br>

Tags :

		* représente une version 

		* se rapporte toujours au même objet

https://git-flow.readthedocs.io/fr/latest/presentation.html

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows



https://raw.githubusercontent.com/darpan-jain/git-cheat-sheet/master/Img/git-flow-commands-without-flow.png

* initialisation (histoire d'avoir un début)

```
git tag
git tag -a v0.1.0 -m "première release en production"
git push v0.1.0
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows


* création de la branche de developpement

```
git checkout main
git pull
git checkout -b develop
git push origin develop
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

```
git checkout -b xp-feature-file1
vim file1.txt
git status
git add file1.txt
git commit -m "ajout de fichier"
git push origin xp-feature-file1
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* hotfix

```
git checkout main
git pull
git checkout -b hotfix-v0.1.1
vim myfix.txt
git add myfix.txt
git commit -m "hotfix - résolution xxx"
git push -u origin hotfix-v0.1.1
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* merge main

```
git checkout main
git pull
git merge hotfix-v0.1.1
git tag -a v0.1.1 -m "hotfix - résolution xxx"
git push origin v0.1.1
git branch
git branch -d hotfix-v0.1.1
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* merge develop

```
git checkout develop
git pull
git merge hotfix-v0.1.1
git push -u origin develop
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* sync et merge de develop

```
git checkout xp-feature-file1
vim file2.txt
git add file2.txt
git commit -m "développement de la feature
git merge develop
vim file3.txt
git add file3.txt
git commit -m "développement de la feature - fin"
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* fin de dev de la feature on merge vers develop

```
git checkout develop
git pull
git merge xp-feature-file1
git push -u origin develop
```

-------------------------------------------------------------------------------------------

# GITLAB : 12 - Flows

* validation de l'env de developpement merge vers master et tag de version

```
git checkout main
git pull
git merge develop
git tag -a v1.0.0 -m "MVP"
git push --tags
```
