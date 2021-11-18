%title: GITLAB
%author: xavki


# GITLAB : 10 - Wikis


<br>
import gitlab
import os

gl = gitlab.Gitlab(os.environ['CI_SERVER_URL'], private_token=os.environ['PRIVATE_TOKEN'])
project = gl.projects.get(os.environ['CI_PROJECT_ID'])

issue_details = {
'title': f'Validation failed in {os.environ["CI_PROJECT_NAME"]}',
'description': f'Pipeline: {os.environ["CI_PIPELINE_URL"]}',
'assignee_ids': [111, 222]
}
issue = project.issues.create(issue_details )
