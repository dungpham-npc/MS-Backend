pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        script {
          // Checkout the code
          checkout scm

          // Extract Jira issue key from the latest commit message
          def commitMessage = sh(script: 'git log -1 --pretty=%B', returnStdout: true).trim()
          def jiraIssueKey = commitMessage.find(/NJM-\d+/) // Adjust the regex to match your Jira issue key pattern and replace PROJECT to PROJECT KEY

          if (!jiraIssueKey) {
            error "Jira issue key not found in the commit message."
          }
          env.JIRA_ISSUE_KEY = jiraIssueKey
        }
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Update Jira') {
      steps {
        script {
          def testResults = junit 'target/surefire-reports/*.xml'
          def jiraIssueKey = env.JIRA_ISSUE_KEY
          def jiraAuth = "Basic " + "dungpase183097@fpt.edu.vn:ATATT3xFfGF0DNDgpp_IPzbKc97CHbvM2wP5E--YPRqE5oHmoiOwzen-BHWHb2U1teQem555bHQ2FXlpT0FR6mSRFWUe8T1xwhf5YmsnGSR4_zdlV8SC51bBYA-VYeJJntX2Oas0OhjgI3mHnAWMYk3xpWArBAykHFTJbsiM5C5ndBgSxbmFHfI=5E9A53F9".bytes.encodeBase64().toString()
          def status = testResults.failCount == 0 ? "Pass" : "Fail"
          def attachment = "target/surefire-reports/testng-results.xml"
          // Update the custom field "Testcase Result" on Jira
          httpRequest(
              url: "https://your-domain.atlassian.net/rest/api/2/issue/${jiraIssueKey}/transitions",
              httpMode: 'POST',
              customHeaders: [
                  [name: 'Authorization', value: jiraAuth],
                  [name: 'Content-Type', value: 'application/json']
              ],
              requestBody: """
              {
                "fields": {
                  "10038": "${status}"
                }
              }
              """
          )
          // Attach test result file to Jira issue
          httpRequest(
              url: "https://cook-swp.atlassian.net/rest/api/2/issue/${jiraIssueKey}/attachments",
              httpMode: 'POST',
              customHeaders: [
                  [name: 'Authorization', value: jiraAuth],
                  [name: 'X-Atlassian-Token', value: 'no-check']
              ],
              uploadFile: attachment
          )
        }
      }
    }
  }
}
