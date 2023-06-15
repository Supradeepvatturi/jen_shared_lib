def call(String project, String hubUser) {
    sh "docker image build -t ${hubUser}/${project} ."
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}"
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:latest"
    withCredentials([usernamePassword(
            credentialsId: "docker",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
    }
    sh "docker image push ${hubUser}/${project}:${ImageTag}"
    sh "docker image push ${hubUser}/${project}:latest"
}
