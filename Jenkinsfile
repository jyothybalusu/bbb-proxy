node {
    def image_name = "whitbreaddigital/test-automation-proxy:${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
    
    stage('Pull') {
        checkout scm
    }
    
    stage('Build') {
        docker.image("maven:3.3.9-jdk-8").inside {
            sh 'mvn package'
        }
    }

    stage('Dockerize') {
        def app = docker.build("${image_name}")
        app.push()
        app.push("${env.BRANCH_NAME}-latest")
    }
}
