How to deploy a war-file in Apache Tomcat on localhost:

1. Build project with maven in STS4: mvn clean install --> It should generate back-end.war

2. Copy back-end.war inside webapps folder of Tomcat:
	From: /Users/giovannisuter/dev/projects/cms/back-end/workspace/cms-backend/target
	To  : /Users/giovannisuter/dev/tools/apache-tomcat-10.1.33/webapps

3. Stop Tomcat (use alias stop-tomcat)

4. Start Tomcat (use alias start-tomcat)




war-file name: cms-backend.war
-------------

URL: 
===
localhost: http://localhost:8080/cms/contacts
---------
http://localhost:8080/cms-backend/contacts does not longer work anymore. Check pom.xml to understand why.


devprojects.ch: https://devprojects.ch/cms-backend/contacts
--------------
