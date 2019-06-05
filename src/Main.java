import eduni.simjava.Sim_system;
import entities.cpu.CPU;
import entities.disk.Disk;
import entities.registry.Registry;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");

        Sim_system.initialise();
        Source source = new Source("SOURCE", 0.2, 0.00001);

        CPU apiServerCPU = new CPU("CPU_API_SERVER", 0.3, 0.00002, 1, new double[]{1.0});

        CPU schedulerCPU = new CPU("CPU_SCHEDULER", 0.3, 0.00002, 2, new double[]{0.3, 0.3, 0.4});
        Disk schedulerDisk = new Disk("DISK_SCHEDULER", 0.88, 0.00002, 1);

        CPU agentACPU = new CPU("CPU_NODE_AGENTA", 0.3, 0.00002, 3, new double[]{0.6, 0.2, 0.2});
        Registry agentARegistry = new Registry("REGISTRY_NODE_AGENTA", 0.001, 0.000000002);
        Disk agentADisk = new Disk("DISK_NODE_AGENTA", 0.88, 0.00002, 1);

        CPU agentBCPU = new CPU("CPU_NODE_AGENTB", 0.3, 0.00002, 3, new double[]{0.6, 0.2, 0.2});
        Registry agentBRegistry = new Registry("REGISTRY_NODE_AGENTB", 0.001, 0.000000002);
        Disk agentBDisk = new Disk("DISK_NODE_AGENTB", 0.88, 0.00002, 1);

        CPU webAppCPU = new CPU("CPU_WEBAPP", 0.3, 0.00002, 2, new double[]{});

        Sim_system.link_ports("SOURCE", "SOURCE_OUT_1", "CPU_API_SERVER", "CPU_API_SERVER_IN_1");

        // API Server -> Scheduler
        Sim_system.link_ports("CPU_API_SERVER", "CPU_API_SERVER_OUT_1", "CPU_SCHEDULER", "CPU_SCHEDULER_IN_1");

        // CPU Scheduler <-> Disk Scheduler
        Sim_system.link_ports("CPU_SCHEDULER", "CPU_SCHEDULER_OUT_3", "DISK_SCHEDULER", "DISK_SCHEDULER_IN_1");
        Sim_system.link_ports("DISK_SCHEDULER", "DISK_SCHEDULER_OUT_1", "CPU_SCHEDULER", "CPU_SCHEDULER_IN_2");

        // Scheduler -> Node Agent A
        Sim_system.link_ports("CPU_SCHEDULER", "CPU_SCHEDULER_OUT_1", "CPU_NODE_AGENTA", "CPU_NODE_AGENTA_IN_1");

        // Scheduler -> Node Agent B
        Sim_system.link_ports("CPU_SCHEDULER", "CPU_SCHEDULER_OUT_2", "CPU_NODE_AGENTB", "CPU_NODE_AGENTB_IN_1");

        // CPU Node Agent A <-> Registry Node Agent
        Sim_system.link_ports("CPU_NODE_AGENTA", "CPU_NODE_AGENTA_OUT_1", "REGISTRY_NODE_AGENTA", "REGISTRY_NODE_AGENTA_IN_1");
        Sim_system.link_ports("REGISTRY_NODE_AGENTA", "REGISTRY_NODE_AGENTA_OUT_1", "CPU_NODE_AGENTA", "CPU_NODE_AGENTA_IN_3");
        // CPU Node Agent A <-> Disk Node Agent
        Sim_system.link_ports("CPU_NODE_AGENTA", "CPU_NODE_AGENTA_OUT_2", "DISK_NODE_AGENTA", "DISK_NODE_AGENTA_IN_1");
        Sim_system.link_ports("DISK_NODE_AGENTA", "DISK_NODE_AGENTA_OUT_1", "CPU_NODE_AGENTA", "CPU_NODE_AGENTA_IN_2");

        // CPU Node Agent B <-> Registry Node Agent
        Sim_system.link_ports("CPU_NODE_AGENTB", "CPU_NODE_AGENTB_OUT_1", "REGISTRY_NODE_AGENTB", "REGISTRY_NODE_AGENTB_IN_1");
        Sim_system.link_ports("REGISTRY_NODE_AGENTB", "REGISTRY_NODE_AGENTB_OUT_1", "CPU_NODE_AGENTB", "CPU_NODE_AGENTB_IN_3");
        // CPU Node Agent B <-> Disk Node Agent
        Sim_system.link_ports("CPU_NODE_AGENTB", "CPU_NODE_AGENTB_OUT_2", "DISK_NODE_AGENTB", "DISK_NODE_AGENTB_IN_1");
        Sim_system.link_ports("DISK_NODE_AGENTB", "DISK_NODE_AGENTB_OUT_1", "CPU_NODE_AGENTB", "CPU_NODE_AGENTB_IN_2");

        // Node Agent A -> Web App
        Sim_system.link_ports("CPU_NODE_AGENTA", "CPU_NODE_AGENTA_OUT_3", "CPU_WEBAPP", "CPU_WEBAPP_IN_1");

        // Node Agent B -> Web App
        Sim_system.link_ports("CPU_NODE_AGENTB", "CPU_NODE_AGENTB_OUT_3", "CPU_WEBAPP", "CPU_WEBAPP_IN_2");

        Sim_system.run();
    }
}
