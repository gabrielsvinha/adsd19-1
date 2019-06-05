import eduni.simjava.Sim_system;
import entities.cpu.CPU;
import entities.disk.Disk;
import entities.registry.Registry;

public class Main {
    public static void main(String[] args) {
        System.out.println("Init!");

        Sim_system.initialise();
        Source source = new Source("SOURCE", 0.2, 0.00001);

        CPU apiServerCPU = new CPU("CPU_API_SERVER", 0.5, 0.00002, 1, new double[]{1.0});

        CPU schedulerCPU = new CPU("CPU_SCHEDULER", 0.5, 0.00002, 1, new double[]{0.6, 0.4});
        Disk schedulerDisk = new Disk("DISK_SCHEDULER", 0.88, 0.00002, 1);

        CPU agentCPU = new CPU("CPU_NODE_AGENT", 0.5, 0.00002, 3, new double[]{0.6, 0.2, 0.2});
        Registry agentRegistry = new Registry("REGISTRY_NODE_AGENT", 0.01, 0.000000002);
        Disk agentDisk = new Disk("DISK_NODE_AGENT", 0.88, 0.00002, 1);

        CPU webAppCPU = new CPU("CPU_WEBAPP", 0.5, 0.00002, 1, new double[]{1.0});

        Sim_system.link_ports("SOURCE", "SOURCE_OUT_1", "CPU_API_SERVER", "CPU_API_SERVER_IN_1");

        Sim_system.link_ports("CPU_API_SERVER", "CPU_API_SERVER_OUT_1", "CPU_SCHEDULER", "CPU_SCHEDULER_IN_1");
        Sim_system.link_ports("CPU_SCHEDULER", "CPU_SCHEDULER_OUT_1", "CPU_NODE_AGENT", "CPU_NODE_AGENT_IN_1");
        Sim_system.link_ports("CPU_SCHEDULER", "CPU_SCHEDULER_OUT_2", "DISK_SCHEDULER", "DISK_SCHEDULER_IN_1");

        Sim_system.link_ports("CPU_NODE_AGENT", "CPU_NODE_AGENT_OUT_1", "REGISTRY_NODE_AGENT", "REGISTRY_NODE_AGENT_IN_1");
        Sim_system.link_ports("CPU_NODE_AGENT", "CPU_NODE_AGENT_OUT_2", "DISK_NODE_AGENT", "DISK_NODE_AGENT_IN_1");
        Sim_system.link_ports("CPU_NODE_AGENT", "CPU_NODE_AGENT_OUT_3", "CPU_WEBAPP", "CPU_WEBAPP_IN_1");

        Sim_system.run();
    }
}
