# example-webapp-jboss-jdbc
A simple web application example to be deployed on [Red Hat JBoss EAP](https://www.redhat.com/en/technologies/jboss-middleware/application-platform), using the [Holon JDBC Datastore](https://github.com/holon-platform/holon-datastore-jdbc) as data access layer and the [Holon Vaadin integration module](https://github.com/holon-platform/holon-vaadin) for the UI layer.

This application is built using the Holon Platform's Spring Boot support, and can be both runned in stad-alone mode or deployed in a web application server.

To create the deployable *war*, you can run:

```
mvn clean install
```

The [jboss-web.xml](https://github.com/holon-platform/example-webapp-jboss-jdbc/blob/master/src/main/webapp/WEB-INF/jboss-web.xml) file can be edited to change the application context path.
