pipeline {
    agent any

    tools {
        maven 'Maven-3.8.8'   // change if your Maven installation name is different
        jdk 'Java-11'         // change if your JDK label is different
    }

    environment {
        SONARQUBE = credentials('sonarqube-token')
        NEXUS_CREDENTIALS = credentials('nexus-credentials')
        TOMCAT_CREDENTIALS = credentials('tomcat-credentials')
        RECIPIENT_EMAIL = credentials('recipient-email')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev',
                    credentialsId: 'Github-token',
                    url: 'https://github.com/Hajixhayjhay/NumberGuessGame.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Upload to Nexus') {
            steps {
                sh 'mvn deploy -Dnexus.username=${NEXUS_CREDENTIALS_USR} -Dnexus.password=${NEXUS_CREDENTIALS_PSW}'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'tomcat-key', keyFileVariable: 'SSH_KEY')]) {
                    sh '''
                        scp -i $SSH_KEY target/*.war ec2-user@<TOMCAT_PUBLIC_IP>:/opt/tomcat/webapps/
                    '''
                }
            }
        }
    }

    post {
        always {
            mail to: "${RECIPIENT_EMAIL}",
                 subject: "Pipeline ${currentBuild.currentResult}: Job ${env.JOB_NAME} Build #${env.BUILD_NUMBER}",
                 body: "Build finished with status: ${currentBuild.currentResult}\nCheck Jenkins for details."
        }
    }
}