pipeline {
    agent any

    environment {
        IMAGE_NAME = 'springboot-app'
        CONTAINER_NAME = 'springboot-container'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo "🌍 Checkout the code from Git repository"
                checkout scm
            }
        }

        stage('Print Environment') {
            steps {
                echo "📋 Printing environment variables"
                sh '''
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "PATH: $PATH"
                    echo "Docker Version: $(docker --version)"
                    echo "Docker Info: $(docker info)"
                '''
            }
        }

        stage('Build') {
            steps {
                echo "⚙️ Starting Maven build..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image: $IMAGE_NAME"
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy Docker Container') {
            steps {
                echo "🚀 Deploying container $CONTAINER_NAME"
                sh '''
                    echo "Stopping old container if running..."
                    docker stop $CONTAINER_NAME || true

                    echo "Removing old container if exists..."
                    docker rm $CONTAINER_NAME || true

                    echo "Running new container on port 8080"
                    docker run -d -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and deploy successful!'
        }
        failure {
            echo '❌ Build failed! Please check logs above.'
        }
    }
}