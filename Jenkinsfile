pipeline {
  agent {
    kubernetes {
      yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins-role
  restartPolicy: Never
  containers:
  - name: docker
    image: artifactory.cloud.cms.gov/docker/docker:19.03.1
    command:
    - sleep
    args:
    - 99d
    env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
  - name: jfrogcli
    image: docker.bintray.io/jfrog/jfrog-cli-go
    command: ['cat']
    tty: true
    env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
  - name: sonarcli
    image: sonarsource/sonar-scanner-cli
    command: ['cat']
    tty: true
  - name: awscli
    image: amazon/aws-cli
    command: ['cat']
    tty: true
  - name: node16
    image: artifactory.cloud.cms.gov/docker/node:16-alpine
    command: ['cat']
    tty: true
  - name: docker-daemon
    image: artifactory.cloud.cms.gov/docker/docker:19.03.1-dind
    securityContext:
      privileged: true
    env:
      - name: DOCKER_TLS_CERTDIR
        value: ""
      - name: DOCKER_DRIVER
        value: overlay2
      - name: AWS_WEB_IDENTITY_TOKEN_FILE
        value: "/var/run/secrets/eks.amazonaws.com/serviceaccount/token"
      - name: AWS_ROLE_ARN
        value: arn:aws:iam::478919403635:role/cbc-${env.APP_NAME}
  - name: node
    image: artifactory.cloud.cms.gov/docker/node:16-alpine
    command:
    - cat
    tty: true
"""
    }
  }
  environment {
    ENV_PROD="master"
    ENV_IMPL="impl"
    ENV_DEV="develop"
    ENV_TEST="test"

    PROD_ENVIRONMENT="prod"
    IMPL_ENVIRONMENT="impl"
    DEV_ENVIRONMENT="dev"
    TEST_ENVIRONMENT="test"

    PROD_ACCOUNT=""
    IMPL_ACCOUNT=""
    DEV_ACCOUNT=credentials('DEV_ACCOUNT')
    TEST_ACCOUNT=""

    DOCKER_TAG="latest"
  }


  stages {
    stage('check branch') {
      steps {
        script {
          println "Branch: ${env.GIT_BRANCH}"
          ENVIRONMENT=""
          if("${env.GIT_BRANCH}" == "${env.ENV_DEV}") {
            ENVIRONMENT="${env.DEV_ENVIRONMENT}"
            ACCOUNT="${env.DEV_ACCOUNT}"
            APP_DB_URL="${env.DEV_APP_DB_URL}"
          } else if("${env.GIT_BRANCH}" == "${env.ENV_TEST}") {
            ENVIRONMENT="${env.TEST_ENVIRONMENT}"
            ACCOUNT="${env.TEST_ACCOUNT}"
            APP_DB_URL="${env.TEST_APP_DB_URL}"
          } else if("${env.GIT_BRANCH}" == "${env.ENV_IMPL}") {
            ENVIRONMENT="${env.IMPL_ENVIRONMENT}"
            ACCOUNT="${env.IMPL_ACCOUNT}"
            APP_DB_URL="${env.IMPL_APP_DB_URL}"
          } else if("${env.GIT_BRANCH}" == "${env.ENV_PROD}") {
            ENVIRONMENT="${env.PROD_ENVIRONMENT}"
            ACCOUNT="${env.PROD_ACCOUNT}"
            APP_DB_URL="${env.PROD_APP_DB_URL}"
          }


          // jfrog_project_key is also calculated in main.tf of all infra envs
          PROJECT_KEY="${env.APP_NAME}".replaceAll('[^a-zA-Z0-9]','').take(10).toLowerCase()
          DOCKER_IMAGE="artifactory.cloud.cms.gov/${PROJECT_KEY}-docker-local/${env.APP_NAME}-${ENVIRONMENT}-app:${DOCKER_TAG}"


          println "Sparks PROJECT_KEY: ${PROJECT_KEY}"
          println "Sparks DOCKER_IMAGE: ${DOCKER_IMAGE}"

        }
      }
    }



    stage('sonarqube analysis') {
      steps {
        container('sonarcli') {
          withSonarQubeEnv('sonarqube') {
            sh """
            sonar-scanner -X \
              -Dsonar.java.binaries=./src  \
              -Dsonar.projectKey=\"${env.APP_NAME}-project\" \
              -Dsonar.sources=./src/main/java \
              -Dsonar.host.url=\"${env.SONAR_HOST_URL}\"
            """
          }
        }
      }
    }
    stage("SonarQube Quality Gate") {
      steps {
        sleep(5)
        //timeout(time: 10, unit: 'MINUTES') {
        //  waitForQualityGate abortPipeline: true
        //}
      }
    }

    stage('build docker img') {
      when {
        expression {
          "${ENVIRONMENT}" != ""
        }
      }
      steps {
        container('docker') {
          sh """

            docker build -f Dockerfile -t ${DOCKER_IMAGE} .

          """
        }
      }
    }
    stage('jfrogpush') {
      when {
        expression {
          "${ENVIRONMENT}" != ""
        }
      }
      steps {
        container('jfrogcli') {
          withCredentials([usernamePassword(credentialsId: 'JFROG', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            sh """
              apk add docker

              docker login artifactory.cloud.cms.gov -u '${USER}' -p '${PASS}'
              docker push '${DOCKER_IMAGE}'
              export CI=true

              jfrog config add artifactory --url=https://artifactory.cloud.cms.gov --user '${USER}' --password '${PASS}'
              jfrog config show
              jfrog rt docker-push ${DOCKER_IMAGE} docker --build-name=${env.APP_NAME}-app --build-number=${BUILD_NUMBER} --project=${PROJECT_KEY}
              jfrog rt build-publish ${env.APP_NAME}-app ${BUILD_NUMBER}  --project=${PROJECT_KEY}
              jfrog rt build-scan ${env.APP_NAME}-app ${BUILD_NUMBER} --fail=false  --project=${PROJECT_KEY}
            """
          }
        }
      }
    }

    stage('Push to Fargate ECS'){
      when {
        expression {
          "${ENVIRONMENT}" != ""
        }
      }
      steps{
        container('awscli') {
          script {
            LB_DNS = sh (
              script: """
                aws sts assume-role \\
                  --role-arn \"arn:aws:iam::${ACCOUNT}:role/delegatedadmin/developer/jenkins-role\" \\
                  --role-session-name session \\
                  --output text \\
                  --query Credentials \\
                  > /tmp/role-creds.txt
                cat > .aws-creds <<EOF
[default]
aws_access_key_id=\$(cut -f1 /tmp/role-creds.txt)
aws_secret_access_key = \$(cut -f3 /tmp/role-creds.txt)
aws_session_token = \$(cut -f4 /tmp/role-creds.txt)
EOF
                mkdir -p \$HOME/.aws
                cp .aws-creds \$HOME/.aws/credentials
                unset AWS_WEB_IDENTITY_TOKEN_FILE

                aws ecs update-service \
                  --cluster \"${env.APP_NAME}app-${ENVIRONMENT}\" \\
                  --service \"${env.APP_NAME}app-node-react-${ENVIRONMENT}\" \\
                  --force-new-deployment > /dev/null

                aws elbv2 describe-load-balancers \\
                  --names \"${APP_NAME}app-${ENVIRONMENT}-internal-lb\" \\
                  --query 'LoadBalancers[0].DNSName'
              """,
              returnStdout: true
            ).trim()
          }
        }
      }
    }



  }
}
