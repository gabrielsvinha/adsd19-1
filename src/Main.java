import eduni.simjava.Sim_system;
import entities.application.Application;
import entities.database.Database;
import entities.web.Web;

public class Main {
    public static void main(String[] args) {
        System.out.println("Init!");

        Sim_system.initialise();
//        Source source = new Source("Source", 10);
        Web web = new Web("Web", 0, 0);
        Application app = new Application("Application", 0, 0);
        Database database = new Database("Database", 0, 0);

        // Web and application feedback
        Sim_system.link_ports("Web", "OUT", "Application", "IN");
        Sim_system.link_ports("Application", "OUT", "Web", "IN");

        // Application and database feedback
        Sim_system.link_ports("Application", "OUT", "Database", "IN");
        Sim_system.link_ports("Database", "OUT", "Application", "IN");

        // Web output
        Sim_system.link_ports("Web", "OUT", "Source", "IN");

        Sim_system.set_trace_detail(false, true, false);

        Sim_system.run();
    }
}
