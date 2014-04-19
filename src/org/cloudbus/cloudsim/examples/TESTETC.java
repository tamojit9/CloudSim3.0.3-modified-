/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */
//import Cloudlet_Scheduler_Space_Shared.CloudletSchedulerSSImprovedRoundRobin;
import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSRoundRobin;
import DataCenterBrokerModified.DatacenterBrokerModifiedConductance;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * A simple example showing how to create a datacenter with one host and run one
 * cloudlet on it.
 */
public class TESTETC {

    /**
     * The cloudlet list.
     */
    private static List<Cloudlet> cloudletList;
    static int maxMips, pesCount;

    /**
     * The vmlist.
     */
    private static List<Vm> vmlist;

    static void addVm(int vmid, int brokerId) {
        // VM description
        int mips = 100;
        long size = 10000; // image size (MB)
        int ram = 512; // vm memory (MB)
        long bw = 1000;
        int pesNumber = 1; // number of cpus
        String vmm = "Xen"; // VMM name
        ++pesCount;
        int factor = (int) (Math.random()*100);
        maxMips = Math.max(maxMips, factor * mips);
        Vm vm = new Vm(vmid, brokerId, mips * factor, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTSRoundRobin());
        vmlist.add(vm);
    }

    static void addCloudlet(int id, int brokerId) {
        // Cloudlet properties
        int pesNumber = 1;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        Cloudlet cloudlet = new Cloudlet(id, (long) ((Math.random()*100000)), 
                pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet.setUserId(brokerId);
        cloudletList.add(cloudlet);
    }
    
    static void staticAllocationVm(int brokerId) {
        // VM description
            int vmid = 0;
            int mips = 100;
            long size = 10000; // image size (MB)
            int ram = 512; // vm memory (MB)
            long bw = 1000;
            int pesNumber = 1; // number of cpus
            String vmm = "Xen"; // VMM name
            Vm vm = new Vm(vmid, brokerId, mips * 4, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

            vmid++;
            Vm vm1 = new Vm(vmid, brokerId, mips * 2, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

            vmid++;
            Vm vm2 = new Vm(vmid, brokerId, mips * 3, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

            vmid++;
            Vm vm3 = new Vm(vmid, brokerId, mips * 6, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
            // add the VM to the vmList
            vmlist.add(vm);
            vmlist.add(vm1);
            vmlist.add(vm2);
            vmlist.add(vm3);
    }
    
    static void dynamicAllocationOfVm(int id) {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Enter No. of Vms ??"));
        while(n-- > 0) addVm(n, id);
    }
    
    static void dynamicAllocationOfCloudlet(int id) {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Enter No. of Cloudlets ??"));
        while(n-- > 0) addCloudlet(n, id);
    }
    
    static void staticAllocationCloudlet(int pesNumber, int brokerId) {
        
            // Cloudlet properties
            int id = 0;
            long length = 400000;
            long fileSize = 300;
            long outputSize = 300;
            UtilizationModel utilizationModel = new UtilizationModelFull();

            Cloudlet cloudlet = new Cloudlet(id, length * 3, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet.setUserId(brokerId);
			//cloudlet.setVmId(vmid);

            id++;
            Cloudlet cloudlet1 = new Cloudlet(id, length * 8, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet1.setUserId(brokerId);
			//cloudlet1.setVmId(vmid);

            id++;
            Cloudlet cloudlet2 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet2.setUserId(brokerId);
			//cloudlet2.setVmId(vmid);

            id++;
            Cloudlet cloudlet3 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet3.setUserId(brokerId);
			//cloudlet3.setVmId(vmid);

            id++;
            Cloudlet cloudlet4 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet4.setUserId(brokerId);
			//cloudlet4.setVmId(vmid);

            id++;
            Cloudlet cloudlet5 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet5.setUserId(brokerId);
			//cloudlet5.setVmId(vmid);

            id++;
            Cloudlet cloudlet6 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet6.setUserId(brokerId);
			//cloudlet6.setVmId(vmid);

            id++;
            Cloudlet cloudlet7 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet7.setUserId(brokerId);
			//cloudlet7.setVmId(vmid);

            id++;
            Cloudlet cloudlet8 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet8.setUserId(brokerId);
			//cloudlet8.setVmId(vmid);

            id++;
            Cloudlet cloudlet9 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet9.setUserId(brokerId);
			//cloudlet9.setVmId(vmid);

            id++;
            Cloudlet cloudlet10 = new Cloudlet(id, length * 6, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet10.setUserId(brokerId);
			//cloudlet10.setVmId(vmid);

            // add the cloudlet to the list
            cloudletList.add(cloudlet);
            cloudletList.add(cloudlet1);
            cloudletList.add(cloudlet2);
            cloudletList.add(cloudlet3);
            cloudletList.add(cloudlet4);
            cloudletList.add(cloudlet5);
            cloudletList.add(cloudlet6);
            cloudletList.add(cloudlet7);
            cloudletList.add(cloudlet8);
            cloudletList.add(cloudlet9);
            cloudletList.add(cloudlet10);
    }
    
    /**
     * Creates main() to run this example.
     *
     * @param args the args
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {

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

			// Third step: Create Broker
            //this has been modified to DatacenterBrokerModified
            DatacenterBrokerModifiedConductance broker = createBroker();
            int brokerId = broker.getId();

            // Fourth step: Create one virtual machine
            vmlist = new ArrayList<Vm>();
            int pesNumber = 1; // number of cpus
            dynamicAllocationOfVm(brokerId);

            // submit vm list to the broker
            broker.submitVmList(vmlist);

            // Fifth step: Create one Cloudlet
            cloudletList = new ArrayList<>();
            dynamicAllocationOfCloudlet(brokerId);
            // submit cloudlet list to the broker
            broker.submitCloudletList(cloudletList);
			// these two lines are extra and the name suggests their work....
            Datacenter datacenter0 = createDatacenter("Datacenter_0");
            broker.bindByLoadBalancer();
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

    /**
     * Creates the datacenter.
     *
     * @param name the name
     *
     * @return the datacenter
     */
    private static Datacenter createDatacenter(String name) {

		// Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
        // our machine
        List<Host> hostList = new ArrayList<>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
        List<Pe> peList = new ArrayList<>();
        
        for (int i = 0; i < pesCount; i++) {
            peList.add(new Pe(i, new PeProvisionerSimple(maxMips))); // need to store Pe id and MIPS Rating
        }
        int mips = 2000, N = vmlist.size() + 1;

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		// 4. Create Host with its id and list of PEs and add them to the list
        // of machines
        int hostId = 0;
        int ram = 2048 * N; // host memory (MB)
        long storage = 1000000 * cloudletList.size(); // host storage
        int bw = 10000 * N;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        ); // This is our machine

		// 5. Create a DatacenterCharacteristics object that stores the
        // properties of a data center: architecture, OS, list of
        // Machines, allocation policy: time- or space-shared, time zone
        // and its price (G$/Pe time unit).
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this
        // resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
        // devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

	// We strongly encourage users to develop their own broker policies, to
    // submit vms and cloudlets according
    // to the specific rules of the simulated scenario
    /**
     * Creates the broker.
     *
     * @return the datacenter broker
     */
    private static DatacenterBrokerModifiedConductance createBroker() {
        DatacenterBrokerModifiedConductance broker = null;
        try {
            broker = new DatacenterBrokerModifiedConductance("Broker");
        } catch (Exception e) {
            return null;
        }
        return broker;
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

}
