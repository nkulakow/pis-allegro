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
				nexusUrl: 'my.nexus.address',
				groupId: 'com.example',
				version: version,
				repository: 'RepositoryName',
				credentialsId: 'CredentialsId',
				artifacts: [
						[artifactId: 'proba',
						 classifier: '',
						 file: 'proba-v1-SNAPSHOT.jar',
						 type: 'jar']
					    ]
			)
		}
        }
    }
}
