pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'shakeebmd9375'  // Your Docker Hub username
    }

    stages {
        stage('Checkout Code') {
            steps {
       git credentialsId: 'github-cred', url: 'https://github.com/mudassir552/SongsApp.git', branch: 'main'

            }
        }

        stage('Generate Version Tag') {
            steps {
                script {
                    def version = readFile('VERSION.txt').trim().toInteger()
                    def newVersion = version + 1
                    writeFile file: 'VERSION.txt', text: "${newVersion}\n"
                    env.IMAGE_TAG = "v${newVersion}"
                }
            }
        }

        stage('Build and Push Docker Images with Jib') {
            steps {
                script {
                    def services = [
                        [name: 'UserService', configId: 'userservice-dev'],
                        [name: 'Songs', configId: 'songservice-dev']
                    ]

                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        services.each { service ->
                            def imageName = "${DOCKER_REGISTRY}/${service.name.toLowerCase()}:${env.IMAGE_TAG}"
                            echo "📦 Building and pushing image: ${imageName}"

                            // Inject the service config file
                            withCredentials([file(credentialsId: service.configId, variable: 'CONFIG_FILE')]) {
                                sh """
                                    cp \$CONFIG_FILE ${service.name}/src/main/resources/application.properties
                                """
                            }

                            // Build and push Docker image
                            sh """
                                cd ${service.name}
                                mvn clean compile com.google.cloud.tools:jib-maven-plugin:3.4.0:build \
                                    -DskipTests \
                                    -Dimage=${imageName} \
                                    -Djib.to.auth.username=$DOCKER_USER \
                                    -Djib.to.auth.password=$DOCKER_PASS
                                cd ..
                            """
                        }
                    }
                }
            }
        }

        stage('Commit New Version') {
            steps {
                script {
                    sh """
                        git config user.email "shakeebmd9@gmail.com"
                        git config user.name "mudassir552"
                        git add VERSION.txt
                        git commit -m " Bump image version to ${env.IMAGE_TAG}"
                        git push origin main
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ All services built and Docker images pushed with tag ${env.IMAGE_TAG}!"
        }
        failure {
            echo "❌ Build failed. See logs above for details."
        }
    }
}
