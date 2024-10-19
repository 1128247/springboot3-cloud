pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                // 从SCM获取代码
                checkout scm
            }
        }
         stage('Set Permissions') {
                    steps {
                        sh 'chmod +x gradlew'
                    }
                }
     stage('Build') {
         steps {
             script {
                 dir('service-router') {
                     sh './gradlew build'
                 }
                 dir('user-service') {
                     sh './gradlew build'
                 }
                 dir('service-consumer-dubbo') {
                     sh './gradlew build'
                 }
                   dir('service-provider-dubbo') {
                                      sh './gradlew build'
                                  }
             }
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
                    dir('service-provider-dubbo') {
                        docker.build("service-provider-dubbo:${env.BUILD_ID}")
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
