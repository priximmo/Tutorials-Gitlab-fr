# Ansible Role: Docker

[![Build Status](https://travis-ci.org/ultransible/docker.svg?branch=master)](https://travis-ci.org/ultransible/docker)


It is an [Ansible](http://www.ansible.com/home) role to:

- Install [Docker](https://www.docker.com) on Linux.
- Install [Docker-Compose](https://docs.docker.com/compose/)
- Configure 1 or more users to run Docker.
- Configure the Docker daemon's options
- Configure proxy/http/https proxy
-Isolate containers with a user [namespace](https://docs.docker.com/engine/security/userns-remap/)



## Requirements

None.

## Role Variables


Available variables are listed below, along with default values (see `defaults/main.yml`):

```
 # Edition can be one of: 'ce' (Community Edition) or 'ee' (Enterprise Edition).
	docker_edition: 'ce'
	docker_package: "docker-{{ docker_edition }}"
	docker_package_state: present

 # Service options.
	docker_service_state: started
	docker_service_enabled: true

 # Do you want to also install Docker Compose? When set to False, Docker Compose
 # will not get installed or will be removed if it were installed previously.
 # Docker Compose options.
	docker_install_compose: true
	docker_compose_version: "1.22.0"
	docker_compose_path: /usr/local/bin/docker-compose
	docker_compose_url: https://github.com/docker/compose/releases/download/{{ docker_compose_version }}/docker-compose-Linux-x86_64

 # Used only for Debian/Ubuntu. Do you want to use the "stable", "edge", "testing" or "nightly" channels?
 # Add more than 1 channel by separating each one with a space.
	docker_apt_release_channel: stable
	docker_apt_arch: amd64
	docker_apt_repository: "deb [arch={{ docker_apt_arch }}] https://download.docker.com/linux/{{ ansible_distribution|lower }} {{ ansible_distribution_release }} {{ docker_apt_release_channel }}"
	docker_apt_ignore_key_error: true

 #Package dependencies
 docker_dependencies_Debian:
   - apt-transport-https
   - ca-certificates

 # Ensure old versions of Docker are not installed
 docker_old_versions_Debian:
   - docker
   - docker-engine

 # apt key url
	apt_key_url: "https://download.docker.com/linux/ubuntu/gpg"
	apt_key_id: "9DC858229FC7DD38854AE2D88D81803C0EBFCD88"
 
 # Used only for RedHat/CentOS/Fedora.
	docker_yum_repo_url: https://download.docker.com/linux/{{ (ansible_distribution == "Fedora") | ternary("fedora","centos") }}/docker-{{ docker_edition }}.repo
	docker_yum_repo_enable_edge: 0
	docker_yum_repo_enable_test: 0

 # Ensure old versions of Docker are not installed 
 docker_old_versions_RedHat:
   - docker
   - docker-common
   - docker-engine
 # rpm key
	rpm_key: "https://download.docker.com/linux/centos/gpg"
 # A list of users who will be added to the docker group.
 	docker_users: []

 # Configure proxy/http/https proxy

 docker_http_proxy: ""
 docker_no_proxy: "localhost,127.0.0.1"
 docker_dockerd_args: "-H fd://"

 # Isolate containers with a user namespace
	docker_namespace_group: "dockremap"
	docker_namespace_user: "dockremap"
	docker_id_remap: 296608

	docker_docker_conf:
	  userns-remap: "{{ docker_namespace_user }}:{{ docker_namespace_group }}"
```

## Example Playbook

```yaml
- hosts: all
  roles:
    - ultransible.docker
```

## License

MIT / BSD
