pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                // 从SCM获取代码
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // 使用Maven构建项目
                sh './gradlew build' // 如果你使用Gradle，使用 ./gradlew build
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // 针对每个模块构建Docker镜像
                    dir('service-router') {
                        docker.build("service-router:${env.BUILD_ID}")
                    }
                    dir('user-service') {
                        docker.build("user-service:${env.BUILD_ID}")
                    }
                     dir('service-consumer-dubbo') {
                         docker.build("service-consumer-dubbo:${env.BUILD_ID}")
                     }
                    dir('service-consumer-dubbo') {
                        docker.build("service-consumer-dubbo:${env.BUILD_ID}")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // 启动Docker Compose
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        success {
            echo '部署成功！'
        }
        failure {
            echo '部署失败！'
        }
    }
}
