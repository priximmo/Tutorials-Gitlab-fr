def project_token = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEF'
def branchName= env.BRANCH_NAME
def buildNum = env.BUILD_NUMBER 


properties([
    gitLabConnection('your-gitlab-connection-name'),
    pipelineTriggers([
        [
            $class: 'GitLabPushTrigger',
            branchFilterType: 'All',
            triggerOnPush: true,
            triggerOnMergeRequest: true,
            triggerOpenMergeRequestOnPush: "never",
            triggerOnNoteRequest: true,
            noteRegex: "Jenkins please retry a build",
            skipWorkInProgressMergeRequest: true,
            secretToken: project_token,
            ciSkip: false,
            setBuildDescription: true,
            addNoteOnMergeRequest: true,
            addCiMessage: true,
            addVoteOnMergeRequest: true,
            acceptMergeRequestOnSuccess: true,
            branchFilterType: "NameBasedFilter",
            includeBranchesSpec: "${branchName}",
            excludeBranchesSpec: "",
        ]
    ])
])

node(){
  try{

    stage('Env - clone generator'){
      git "http://gitlab.example.com/mypipeline/generator.git"
    }

    stage('Env - run postgres'){
			sh "./generator.sh --postgres pipeline${buildNum}${branchName}"
      sh "docker ps -a"
    }
			def ipPostgres = sh returnStdout: true, script: "./generator.sh -i | grep postgrespipeline${buildNum}${branchName} | awk '{print \$1}' | tr -d '\n'" 


    /* Récupération du dépôt git applicatif */
    stage('SERVICE - Git checkout'){
      git branch: branchName, url: "http://gitlab.example.com/mypipeline/myapp1.git"
    }

    /* déterminer l'extension */
    if (branchName == "dev" ){
      extension = "-SNAPSHOT"
    }
    if (branchName == "stage" ){
      extension = "-RC"
    }
    if (branchName == "master" ){
      extension = ""
    }

    /* Récupération du commitID long */
    def commitIdLong = sh returnStdout: true, script: 'git rev-parse HEAD'

    /* Récupération du commitID court */
    def commitId = commitIdLong.take(7)

    /* Modification de la version dans le pom.xml */
    sh "sed -i s/'-XXX'/${extension}/g pom.xml"

    /* Ajout du nom du conteneur dans application properties */
    sh "sed -i s/'XXX'/${ipPostgres}/g src/main/resources/application.properties"

    /* Récupération de la version du pom.xml après modification */
    def version = sh returnStdout: true, script: "cat pom.xml | grep -A1 '<artifactId>myapp1' | tail -1 |perl -nle 'm{.*<version>(.*)</version>.*};print \$1' | tr -d '\n'"

     print """
     #################################################
        BanchName: $branchName
        CommitID: $commitId
        AppVersion: $version
        JobNumber: $buildNum
     #################################################
        """
    /* Maven - tests */
    stage('SERVICE - Tests unitaires'){
      sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.3-jdk-8 mvn -B clean test'
    }

    /* Maven - build */
    stage('SERVICE - Jar'){
      sh 'docker run --rm --name maven-${commitIdLong} -v /var/lib/jenkins/maven/:/root/.m2 -v "$(pwd)":/usr/src/mymaven --network generator_generator -w /usr/src/mymaven maven:3.3-jdk-8 mvn -B clean install'
    }

    /* Docker - build & push */
    /* Attention Credentials */
    def imageName='192.168.5.5:5000/myapp'

    stage('DOCKER - Build/Push registry'){
      docker.withRegistry('http://192.168.5.5:5000', 'myregistry_login') {
         def customImage = docker.build("$imageName:${version}-${commitId}")
         customImage.push()
      }
      sh "docker rmi $imageName:${version}-${commitId}"
    }

    /* Docker - test */
    stage('DOCKER - check registry'){
      withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'myregistry_login',usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
      sh 'curl -sk --user $USERNAME:$PASSWORD https://192.168.5.5:5000/v2/myapp/tags/list'
      }
    }
 
    stage('ANSIBLE - Deploy'){
        git branch: 'master', url: 'http://gitlab.example.com/mypipeline/deploy-ansible.git'
        sh "mkdir -p roles"
        sh "ansible-galaxy install --roles-path roles -r requirements.yml"
        ansiblePlaybook (
              colorized: true,
              playbook: "install-myapp1.yml",
              hostKeyChecking: false,
              inventory: "env/${branchName}/hosts",
              extras: "-u vagrant -e 'image=$imageName:${version}-${commitId}' -e 'version=${version}'"
              )
    }

    if (branchName == "dev" ){

      stage('JMETER - test'){
          sh '/usr/bin/jmeter/apache-jmeter-5.2.1/bin/jmeter -n -t test/final_plan.jmx -l results.jtl'
          sh 'cat results.jtl'
	      	perfReport errorFailedThreshold: 20, errorUnstableThreshold: 20, filterRegex: '', sourceDataFiles: 'results.jtl'
      }
    }

    git "http://gitlab.example.com/mypipeline/generator.git"
		sh "./generator.sh --clean postgrespipeline${buildNum}${branchName}"


  } finally {
    cleanWs()
  }
}



