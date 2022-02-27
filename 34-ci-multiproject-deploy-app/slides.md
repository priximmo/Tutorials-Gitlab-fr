%title: GITLAB
%author: xavki


# GITLAB : 34	- CI : MultiProject Pipeline - Deploiement - part 1

<br>

Objectifs :

		* installation user

		* création variables de connexion ssh

		* role docker

		* deploiement test

		* role deploy

-------------------------------------------------------------------------------------------

# GITLAB : 34	- CI : MultiProject Pipeline - Deploiement - part 1


<br>

```
ssh-keygen -t ecdsa -b 521
sudo adduser
sudo usermod -aG sudo gitlab
sudo su - gitlab
mkdir .ssh
vim .ssh/authorized_keys
```

-------------------------------------------------------------------------------------------

# GITLAB : 34	- CI : MultiProject Pipeline - Deploiement - part 1

<br>

```
SSH_PRIVATE
SSH_TARGET
SSH_USER
SUDO_USER_PWD
```

-------------------------------------------------------------------------------------------

# GITLAB : 34	- CI : MultiProject Pipeline - Deploiement - part 1


<br>

```
openssl s_client -showcerts -connect registry.xavki.gitlab:443 < /dev/null | sed -ne '/-BEGIN CERT
IFICATE-/,/-END CERTIFICATE-/p' > /usr/local/share/ca-certificates/registry.crt
update-ca-certificates
```



