module java1_2023_KUD0132{
        requires transitive javafx.controls;
        requires javafx.fxml;
        opens org.kudladev to javafx.fxml;
        exports org.kudladev;
    exports org.kudladev.platforms;
    opens org.kudladev.platforms to javafx.fxml;
    exports org.kudladev.utils;
    opens org.kudladev.utils to javafx.fxml;
}