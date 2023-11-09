pipeline { 
    agent any

	tools {
		maven 'Jenkins-maven'
		jdk 'openjdk-19'
	}
	
	environment {
		NEXUS_URL = 'http://localhost:8001'
		NEXUS_REPO = 'pis-central-repository'
	}
	
    stages { 
        stage ('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') { 
            steps { 
               sh 'mvn clean package' 
            }
	}
	stage('Publish to Nexus') {
		steps {
			nexusArtifactUploader(
				nexusVersion: 'nexus3',
				protocol: 'http',
				nexusUrl: NEXUS_URL,
				groupId: 'org.example',
				version: '1.0-SNAPSHOT',
				repository: NEXUS_REPO,
				credentialsId: 'NEXUS_CREDENTIAL',
				artifacts: [
					[artifactId: 'proba',
					classifier: '',
					file: 'my-service-' + version + '.jar',
					type: 'jar']
					]
				)
			}
		}
        }
}
