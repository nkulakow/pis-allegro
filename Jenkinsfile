pipeline { 
    agent any

	tools {
		maven 'Jenkins-maven'
		jdk 'openjdk-19'
	}
	
	environment {
		NEXUS_URL = 'localhost:8001'
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
			pom = sh ‘mvn -f ./pom.xml help:evaluate -Dexpression=project -q -DforceStdout’
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
						file: '${pom.artifactId}-${pom.version}.${pom.packaging}',
						type: 'jar']
					]
				)
			}
		}
        }
}

