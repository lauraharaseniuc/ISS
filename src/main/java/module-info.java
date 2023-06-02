module com.example.lab7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab7 to javafx.fxml;
    exports com.example.lab7;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    exports com.example.lab7.hibernate.dialect to org.hibernate.orm.core;
    opens com.example.lab7.model to org.hibernate.orm.core, javafx.base;
    exports com.example.lab7.controllers to javafx.fxml;
    opens com.example.lab7.controllers to javafx.fxml;
}