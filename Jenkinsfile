pipeline {
    agent(node)
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
    
    stages {
        stage('package') {
            steps {
                sh './gradlew clean build'
                archiveArtifacts 'build/libs/*.jar'
            }
        }
    }
}
