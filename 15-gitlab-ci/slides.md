%title: GITLAB
%author: xavki


# GITLAB : 15 - GitLab-Ci : Présentation


<br>

Continuous Integration :

		* action sur push du nouveau code

		* aider les développeurs dans la phase de développement (code quality...)

		* création de l'applicatif final (binaire...)

		* vérification du code (tests unitaires...) pour trouver d'éventuelles régression
				* découverte le plus tôt possible

<br>

Continuous Delivery :

		* tests complémentaires dans un environnement proche de la production

		* permet de releaser (versionner) avant mise en production

------------------------------------------------------------------------------------------------------------------

# GITLAB : 15 - GitLab-Ci : Présentation


<br>

Continuous deployment :

		* déploiement automatisé en production

<br>

		Pipeline = moyen technique pour dérouler ces étapes (et pas seulement)

		https://docs.gitlab.com/ee/ci/introduction/

------------------------------------------------------------------------------------------------------------------

# GITLAB : 15 - GitLab-Ci : Présentation

<br>

	Runners :

			* ressources/serveurs/conteneurs

			* permet de lancer les jobs

			* applicatifs nécessaires à l'exécution (docker, ansible, maven...)

			* recommandé d'utiliser une machine différente du host Gitlab

			* GO langage

			* dépendance de la version de Gitlab

			* nécessité de l'enregistrer

			* scope d'un runner (shared, group, specific)

			* tag sur les runners (langages, builder...)

			* gitlab.com : runner publique limité en temps d'utilisation (2000min/pipeline/mois)
			
			* doc : https://docs.gitlab.com/runner/

------------------------------------------------------------------------------------------------------------------

# GITLAB : 15 - GitLab-Ci : Présentation


<br>

	Executor :

			* type de runner adapté au job

			* SSH, shell, powershell, docker, kubernetes, virtualbox...
				https://docs.gitlab.com/runner/executors/index.html#selecting-the-executor


<br>

	Pipeline :

			* déclenché par trigger ou par tâcehs récurrentes (cron)

			* suite de tâches à réaliser en vue d'une action finale

			* découpé en stages (étapes)

			* décrit dans un fichier yaml à la racine : .gitlab-ci.yaml

------------------------------------------------------------------------------------------------------------------

# GITLAB : 15 - GitLab-Ci : Présentation


<br>

	Stage :

			* étapes d'un pipeline

			* constitué de 1 à plusieurs jobs

			* regroupement logique

<br>

	Job :

			* tâche précise à réaliser dans un environnement spécifique à celle-ci

			* possibilité de fixer des contraintes : environnement, conditions...

			* consitué d'un script : les commandes réalisées
					before/after possibles

<br>

Simplement :

		Pipeline > Stage(s) > Job(s) > Script

https://about.gitlab.com/blog/2018/01/22/a-beginners-guide-to-continuous-integration/

