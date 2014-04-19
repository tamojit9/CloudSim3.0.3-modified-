/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

/**
 *
 * @author Tamojit9
 */
public class Simulate {

    static List<Cloudlet> cloudlets = new ArrayList<>();
    static List<Vm> vms = new ArrayList<>();
    static List<Host> hosts = new ArrayList<>();
    static LinkedList<Storage> storageList = new LinkedList<>();
    static DatacenterCharacteristics characteristics;
    static private Datacenter datacenter;
    static double time_zone = 10.0; // time zone this resource located
    static double cost = 3.0; // the cost of using processing in this resource
    static double costPerMem = 0.05; // the cost of using memory in this resource
    static double costPerStorage = 0.001; // the cost of using storage in this
    static double costPerBw = 0.0; // the cost of using bw in this resource
    static int hostId = 0;
    static int ram = 2048; // host memory (MB)
    static long storage = 1000000; // host storage
    static int bw = 10000;
    static int mips = 1000;
    static DatacenterBroker broker = createBroker();

    static void addHost(int hostId, RamProvisioner rp, BwProvisioner bwp, int storage, List<Pe> peList, VmScheduler vm) {
        hosts.add(
                new Host(
                        hostId,
                        rp,
                        bwp,
                        storage,
                        peList,
                        vm
                )
        ); // This is our machine
    }

    static void iniDataCenter(String name, int cost, int costPerMem, int costPerStorage, int costPerBw) {
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        int time_zone = 10;
        characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hosts, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hosts), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addCloudlet(int id, int pesNumber, long length, long fileSize, long outputSize) {

        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet.setUserId(broker.getId());
        cloudlets.add(cloudlet);
    }

    static void addVm(int vmid, int mips, int size, int ram, int bw, int pesNumber, CloudletScheduler cs) {

        if(broker == null) {
            broker = createBroker();
        }
        String vmm = "Xen"; // VMM name
        Vm vm = new Vm(vmid, broker.getId(), mips, pesNumber, ram, bw, size, vmm, cs);
        // add the VM to the vmList
        vms.add(vm);
    }

    /**
     * Creates the datacenter.
     *
     * @param name the name
     *
     * @return the datacenter
     */
    private static Datacenter createDatacenter(String name) {
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";

        characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hosts, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hosts), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }
    
    /**
	 * Creates the broker.
	 *
	 * @return the datacenter broker
	 */
    private static DatacenterBroker createBroker() {
		try {
                        int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events
			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
			return new DatacenterBroker("Broker");
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * Prints the Cloudlet objects.
     *
     * @param list list of Cloudlets
     */
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                Log.print("SUCCESS");

                Log.printLine(indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getFinishTime()));
            }
        }
    }

    public static void simulate() {

        Log.printLine("Starting CloudSimExample1...");

        try {
			// First step: Initialize the CloudSim package. It should be called
            // before creating any entities.
            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // mean trace events

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
            // Datacenters are the resource providers in CloudSim. We need at
            // list one of them to run a CloudSim simulation
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Third step: Create Broker
            int brokerId = broker.getId();

            // Fourth step: Create one virtual machine
            // submit vm list to the broker
            broker.submitVmList(vms);

            // Fifth step: Create one Cloudlet

            // add the cloudlet to the list

            // submit cloudlet list to the broker
            broker.submitCloudletList(cloudlets);

            // Sixth step: Starts the simulation
            CloudSim.startSimulation();

            CloudSim.stopSimulation();

            //Final step: Print results when simulation is over
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

            Log.printLine("CloudSimExample1 finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }
}
