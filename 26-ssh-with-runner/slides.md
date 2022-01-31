%title: GITLAB
%author: xavki


# GITLAB : 26 - SSH runner vers target


<br>

Pré-requis :

		* créer une paire de clefs

		* créer un user (avec les droits souhaités)

		* ajouter la clef publique sur le serveur cible


----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

* création du user

```
ssh-keygen -t ecdsa -b 521
sudo adduser
sudo usermod -aG sudo gitlab
sudo su - gitlab
mkdir .ssh
vim .ssh/authorized_keys
```

----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

PIPELINE

* création des variables

```
SSH_PRIVATE
SSH_TARGET
SSH_USER
SUDO_USER_PWD
```

----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

* configuration de SSH dans notre runner (docker ou non)

```
image: debian:latest
job1:
  before_script:
    - 'command -v ssh-agent >/dev/null || ( apt update && apt install -y openssh-client )' 
    - eval $(ssh-agent -s)
    - echo "$SSH_PRIVATE" | tr -d '\r' | ssh-add -
    - mkdir -p ~/.ssh
    - chmod 700 ~/.ssh
    - ssh-keyscan $SSH_TARGET >> ~/.ssh/known_hosts
    - chmod 644 ~/.ssh/known_hosts
```

----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

* test de ssh

```
  script:
  - ssh $SSH_USER@$SSH_TARGET "hostname"
  tags:
    - docker
```

----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

* et ansible ???

```
  script:
  - ansible -i "$SSH_TARGET," all -u $SSH_USER -m command -a uptime
```

----------------------------------------------------------------------------

# GITLAB : 26 - SSH runner vers target

<br>

* adaptation

```
...
        - echo "[all]" > inventory.ini
        - echo "$SSH_TARGET" >> inventory.ini
        - ANSIBLE_BECOME_PASS=$SUDO_USER_PWD ansible-playbook -i inventory.ini -l $SSH_TARGET -u $SSH_USER play.yml
```

```
-   name: my playbook
    hosts: all
    become: yes
    tasks:
    - name: t1
      apt: 
        name: nginx
        state: latest
```

