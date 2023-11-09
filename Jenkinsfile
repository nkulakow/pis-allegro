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
			pom = readMavenPom file: "pom.xml";
			filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
			echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
			artifactPath = filesByGlob[0].path;
			artifactExists = fileExists artifactPath;

			if(artifactExists) {
				nexusArtifactUploader(
					nexusVersion: 'nexus3',
					protocol: 'http',
					nexusUrl: NEXUS_URL,
					groupId: pom.groupId,
					version: '1.0.0',
					repository: NEXUS_REPO,
					credentialsId: 'NEXUS_CREDENTIAL',
					artifacts: [
						[artifactId: pom.artifactId,
						classifier: '',
						file: artifactPath,
						type: pom.packaging]
					]
				);
			} else {
				error "*** File: ${artifactPath}, could not be found";
			}
		}
        }
    }
}
