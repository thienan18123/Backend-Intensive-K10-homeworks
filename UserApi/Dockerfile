FROM tomcat:11.0-jdk25


RUN rm -rf /usr/local/tomcat/webapps/*


COPY UserApi.war /usr/local/tomcat/webapps/UserApi.war

EXPOSE 8080
CMD ["catalina.sh", "run"]