# Tigüi

Tigüi is a Java-based desktop application designed for managing construction projects. It helps track project details, costs, progress, and generates payment certificates.

## Installation

To run this project, you will need to have Java Development Kit (JDK) 18 or higher installed. You will also need a MySQL database.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/nagasis3321/tpi-isaac.git](https://github.com/nagasis3321/tpi-isaac.git)
    ```
2.  **Configure the database:**
    * Create a MySQL database named `db_tpi_pa_ii`.
    * Update the database credentials in `TPI/src/META-INF/persistence.xml`:
        ```xml
        <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/db_tpi_pa_ii?zeroDateTimeBehavior=CONVERT_TO_NULL"/>
        <property name="javax.persistence.jdbc.user" value="root"/>
        <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
        <property name="javax.persistence.jdbc.password" value="tutesqlMy2022!"/>
        ```

3.  **Build and run the project:**
    You can build and run this project using an IDE like NetBeans or by using the included Ant build script.

    **Using NetBeans:**
    * Open the project in NetBeans.
    * Run the main class `Main.TPI`.

    **Using Ant:**
    ```bash
    ant jar
    java -jar dist/TPI.jar
    ```

## Usage

The application provides a graphical user interface for managing construction projects. Here's how you can use it:

* **Create a new company:** Go to `Modificar BD` > `Empresas` > `Crear` and fill in the company details.
* **Create a new project:** Go to `Modificar BD` > `Obras` > `Crear` and enter the project details, including the company's CUIT.
* **Add items to a project:** After creating a project, you can add items with their respective costs.
* **Generate reports:** The application allows you to generate various reports, including payment certificates, contract amounts, and remaining balances.

Here's a code snippet showing how to create a new company using the system:

```java
import Controlador.Sistema;

public class Main {
    public static void main(String[] args) {
        Sistema sys = new Sistema();
        // Example of creating a company
        boolean success = sys.crearEmpresa(123456789, "My Construction Company", "123 Main St", "John Doe", "Jane Smith");
        if (success) {
            System.out.println("Company created successfully!");
        } else {
            System.out.println("Failed to create company.");
        }
    }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
