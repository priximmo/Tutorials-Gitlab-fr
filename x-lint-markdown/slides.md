%title: GITLAB
%author: xavki


# GITLAB : 06 - Premier Git Clone


<br>

stages:
  - linting

Linting:
  stage: linting
  image: registry.gitlab.com/pipeline-components/markdownlint:latest
  script:
    - mdl --style all --warnings .

