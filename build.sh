#!/bin/bash
java \
-javaagent:elastic-apm-agent.jar \
-Delastic.apm.server_url="$SERVER_URL" \
-Delastic.apm.service_name=api-composer \
-Delastic.apm.secret_token="$SECRET_TOKEN" \
-Delastic.apm.environment=my-environment \
-Delastic.apm.application_packages=org.example \
-jar api-composer.jar