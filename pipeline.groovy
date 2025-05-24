pipeline {
    agent any

    tools {
        maven 'Maven'     
        jdk 'JDK21'      
    }

    stages {
        stage('Get Code') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/EsraaElkheshen/Library-API.git'
            }
        }

        stage('Build & Run Tests') {
            steps {
                bat 'mvn clean test -Dallure.results.directory=allure-results'
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat 'allure generate allure-results --clean -o allure-report'
            }
        }

        stage('Archive Allure Report') {
            steps {
                archiveArtifacts artifacts: '**/allure-report/**/*.*', fingerprint: true
            }
        }

        stage('Archive Surefire Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // If Surefire plugin is used
        }
        success {
            echo "✅ Test passed. View reports in archived artifacts."
        }
        failure {
            echo "❌ Tests failed. Check logs and reports."
        }
    }
}
