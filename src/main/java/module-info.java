module java1_2023_KUD0132{
        requires transitive javafx.controls;
        requires javafx.fxml;
        opens org.kudladev to javafx.fxml;
        exports org.kudladev;
}