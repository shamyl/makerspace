node {
    def slackResponse = send("STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")

    stage('Syncing Code from GitHub') {
        checkout scm
    }

    stage('Build Backend Image') {
        sh label: 'build', script: 'docker build -t makerspace-backend:latest .'
        reply("BUILD SUCCESSFUL: <@UEWEG106N>: Backend build for makerspace-backend is successful.", slackResponse.threadId, color='#e33d3d')
    }

    stage('Stop Old Backend Build') {
        sh label: 'remove', script: 'docker stop makerspace-backend || true && docker rm makerspace-backend || true'
    }

    stage('Run Backend Container') {
        sh label: 'run', script: 'docker run -d --name makerspace-backend -p 7100:7100 makerspace-backend:latest'
    }
    

    stage('Remove Extra Images') {
        sh label: 'remove', script: 'docker image prune -f -a'
    }

    reply("JOB COMPLETED: <@UEWEG106N>, makerspace-backend is back online and accessible at http://localhost:7100.", slackResponse.threadId, color='#5ba50b')
}

def send(String message='Started', String color='#FFFF00') {
    return slackSend(channel: "softwareteam", message: message, color: color)
}

def reply(String message, String threadId, String color='#ffb85c') {
    slackSend(
        channel: threadId,
        replyBroadcast: true,
        message: message,
        color: color
    )
}
