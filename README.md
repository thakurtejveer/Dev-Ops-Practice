# Dev-Ops-Practice
This is to practice devops

docker run -d \
-p 9090:8080 \
-v jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
--name jenkins-docker \
jenkins-with-java-maven