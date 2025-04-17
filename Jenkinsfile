pipeline {
    agent {
        docker {
            image 'maven:3.9.4-openjdk-17'
        }
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t springboot-app .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d -p 8080:8080 --name springboot-app springboot-app'
            }
        }
    }
}
