node {
  stage('scm'){
      git branch: 'master', credentialsId: 
'7e488afc-83f3-42cd-a8e8-a112e34657da', 
url:'ssh://git@bitbucket-eng-rtp1.cisco.com:7999/cis/nagios-api.git'
  }
  stage('build'){
     sh 'mvn clean package'
  }
}
