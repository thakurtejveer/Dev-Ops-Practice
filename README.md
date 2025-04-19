# Dev-Ops-Practice
This is to practice devops

#TO- DO 
below is the dump of documentation, needs to be formatted properly


docker run -d \
-p 9090:8080 \
-v jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
--name jenkins-docker \
jenkins-with-java-maven



Spring Boot App with Jenkins, Docker, and Postman Testing - Complete Guide
1. Install Required Tools:
   Before you begin, make sure you have the following tools installed:

Homebrew (for macOS package management)

Java JDK 11+

Maven

Docker

Postman

ngrok

Jenkins (Local installation)

Install Homebrew (If not installed):
If Homebrew is not installed, you can install it using this command:

bash
Copy
Edit
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
Install Java (JDK 11+ via Homebrew):
Install Java with the following:

bash
Copy
Edit
brew install openjdk@11
Then, set the environment variables:

bash
Copy
Edit
export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
Install Maven:
bash
Copy
Edit
brew install maven
Install Docker:
Download and install Docker Desktop from Docker's official website.

Install Postman:
Download and install Postman from Postman’s official website.

2. Create a Spring Boot Application:
   Generate Spring Boot Application: You can either generate the Spring Boot project using Spring Initializr or manually. For simplicity, let’s use Spring Initializr:

Go to Spring Initializr

Choose the following settings:

Project: Maven Project

Language: Java

Spring Boot: 2.x.x (Latest stable version)

Dependencies: Spring Web, Spring Boot DevTools, Spring Data JPA, H2 Database (for testing purposes)

Create Controller and Service: Inside your Spring Boot app, create a basic HelloController.java to return a "Hello World" message.

HelloController.java:

java
Copy
Edit
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
Create Test Cases: Write a simple test case to verify the application functionality.

HelloControllerTest.java:

java
Copy
Edit
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/hello"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string("Hello, World!"));
    }
}
3. Dockerize the Spring Boot Application:
   Create Dockerfile:
   Inside the root of your project directory, create a Dockerfile.

Dockerfile
Copy
Edit
FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/devops-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
Build the JAR:
Run the following Maven command to create the JAR file:

bash
Copy
Edit
mvn clean package
Build the Docker Image:
Create a Docker image from the generated JAR file.

bash
Copy
Edit
docker build -t devops-app .
Run the Docker Container:
Now, run the container:

bash
Copy
Edit
docker run -d -p 8080:8080 devops-app
Verify the Application:
Test the application by visiting http://localhost:8080/hello in your browser or use Postman to send a GET request.

4. Set Up Jenkins:
   Install Jenkins (Local Installation):
   If Docker installation didn’t work for Jenkins, you can install it locally via Homebrew.

bash
Copy
Edit
brew install jenkins-lts
After installation, start Jenkins:

bash
Copy
Edit
brew services start jenkins-lts
Jenkins will be available at http://localhost:8080.

Unlock Jenkins:
Open the URL http://localhost:8080 and you'll be asked to unlock Jenkins.

Retrieve the unlock key with the following command:

bash
Copy
Edit
cat /Users/your_username/.jenkins/secrets/initialAdminPassword
Paste the key into the Jenkins unlock page.

5. Set Up Jenkins Pipeline:
   Create a New Pipeline in Jenkins:
   Login to Jenkins: Go to http://localhost:8080 and log in with the credentials.

Create a New Job:

Click on New Item.

Enter a name (e.g., devops-pipeline), and choose Pipeline.

Click OK.

Configure Pipeline:

Under the Pipeline section, select Pipeline script from SCM.

Choose Git for SCM, and provide the repository URL (e.g., https://github.com/your-repo/devops).

Set the Branch Specifier to the branch you want (e.g., */main).

Add Jenkinsfile:
Create a Jenkinsfile in the root of your repository.

groovy
Copy
Edit
pipeline {
agent any

    stages {
        stage('Build and Test') {
            steps {
                script {
                    sh 'mvn clean verify'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    sh 'docker build -t devops-app .'
                }
            }
        }

        stage('Deploy to Docker') {
            steps {
                script {
                    sh 'docker run -d -p 8080:8080 devops-app'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
Run the Pipeline:
Save the Jenkins pipeline, then trigger the job by clicking Build Now.

Jenkins will execute the stages: build, test, dockerize, and deploy.

6. Auto Deployment with Webhook and Ngrok:
   Install ngrok:
   Download and install ngrok from ngrok website.

Authenticate ngrok by signing up and using the command:

bash
Copy
Edit
ngrok authtoken YOUR_NGROK_AUTH_TOKEN
Expose Jenkins with Ngrok:
You can use ngrok to expose your Jenkins server publicly.

bash
Copy
Edit
ngrok http 8080
Ngrok will generate a public URL like http://abcd1234.ngrok.io, which you can use to trigger webhooks or access Jenkins externally.

7. Troubleshooting Tips:
   If Jenkins is not accessible: Ensure Jenkins is running properly by checking the status with:

bash
Copy
Edit
brew services list
If it's not running, start it with:

bash
Copy
Edit
brew services start jenkins-lts
If Docker image build fails: Check the Dockerfile for any errors. Also, verify if the JAR file exists in the target directory (target/devops-0.0.1-SNAPSHOT.jar).

If Jenkins pipeline fails: Check the pipeline logs for errors. Ensure the repository is correctly linked to Jenkins and that all dependencies are available.

8. Final Testing Using Postman:
   Test the API:

Open Postman and create a new GET request to http://localhost:8080/hello.

You should see the response: Hello, World!.

Conclusion:
This guide covered the end-to-end process, including creating a Spring Boot application, Dockerizing it, setting up Jenkins with a pipeline, testing with Postman, and deploying using Docker. It also includes setup for ngrok to expose the Jenkins server for external access and webhook triggering.