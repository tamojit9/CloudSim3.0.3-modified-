/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples;

import DataCenterBrokerModified.DatacenterBrokerModifiedConductance;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
 *
 * @author Tamojit9
 */
public class TEST_CONDUCTANCE {

    static boolean alt = false;
    static int maxMips, pesCount;
    static int delta = 1;
    static String toFile = "";
    static File storeTo = new File("D:\\My Projects and resources\\4th year project\\Conductance\\graph.txt");
    static void insertRow(double totalTime, int no_of_cloudlets) {
        //System.out.println(Integer.toString(totalTime) + "   " + Integer.toString(no_of_cloudlets));
        toFile += "\n" + Integer.toString(alt ? 2 : 1) + "  ";
        toFile += Double.toString(totalTime) + "   " + Integer.toString(no_of_cloudlets);
    }
    
    static void store() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(storeTo));
        bw.write(toFile);
        bw.close();
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

        int mips = 2000, N = vmlist.size() + 1;

        // 3. Create PEs and add these into a list.
        for (int i = 0; i < pesCount; i++) {
            peList.add(new Pe(i, new PeProvisionerSimple(maxMips))); // need to store Pe id and MIPS Rating
        }

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
        double totalTime = 0, no_of_cloudlets = list.size();
        String indent = "    ";
        /*Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                + "Start Time" + indent + "Finish Time");*/

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            //Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
              //  Log.print("SUCCESS");
                totalTime += cloudlet.getActualCPUTime();
               /* Log.printLine(indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getFinishTime()));*/
            }
        }
        insertRow(totalTime, (int) no_of_cloudlets);
    }

    /**
     * The cloudlet list.
     */
    private static List<Cloudlet> cloudletList;

    /**
     * The vmlist.
     */
    private static List<Vm> vmlist;

    static void addCloudlet(int id, int brokerId) {
        // Cloudlet properties
        int pesNumber = 1;
        long length = 100;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        Cloudlet cloudlet = new Cloudlet(id, length,
                pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet.setUserId(brokerId);
        cloudletList.add(cloudlet);
    }

    static void addVm(int brokerId, int vmid) {
        // VM description
        int mips = 100;
        long size = 10000; // image size (MB)
        int ram = 512; // vm memory (MB)
        long bw = 1000;
        int pesNumber = 1; // number of cpus
        String vmm = "Xen"; // VMM name
        int factor = delta;//(int) (Math.random() * 100.0);
        delta++;
        maxMips = Math.max(maxMips, factor * mips);
        ++pesCount;
        Vm vm = new Vm(vmid, brokerId, mips * factor, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
        vmlist.add(vm);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        Log.printLine("Starting CloudSimExample1...");
        int vms = 10, cloudlets = 100;
        int cloudletCount = 0;
        int vmCount = 0;
        int enter_no_iterations =  Integer.parseInt(JOptionPane.showInputDialog("Enter no. of iterations"));
        while(enter_no_iterations-- > 0) {
            //JOptionPane.showMessageDialog(null, enter_no_iterations + " ");
            try {
                // First step: Initialize the CloudSim package. It should be called
                // before creating any entities.
                int num_user = 1; // number of cloud users
                Calendar calendar = Calendar.getInstance();
                boolean trace_flag = false; // mean trace events

                // Initialize the CloudSim library
                CloudSim.init(num_user, calendar, trace_flag);

                // Third step: Create Broker
                //this has been modified to DatacenterBrokerModified
                DatacenterBrokerModifiedConductance broker = createBroker();
                int brokerId = broker.getId();
                // Fourth step: Create one virtual machine
                vmlist = new ArrayList<>();
                int pesNumber = 1; // number of cpus
                vmCount = 10 + (vmCount + vms) % 100;//Integer.parseInt(JOptionPane.showInputDialog("Please Enter no. of Vms : "));
                for (int i = 0; i < vmCount; i++) {
                    addVm(brokerId, i);
                }
                // submit vm list to the broker
                broker.submitVmList(vmlist);

                // Fifth step: Create one Cloudlet
                cloudletList = new ArrayList<>();
                cloudletCount = 100 + (cloudletCount + cloudlets)%1000;//Integer.parseInt(JOptionPane.showInputDialog("Please Enter no. of Cloudlets : "));
                for (int i = 0; i < cloudletCount; i++) {
                    addCloudlet(i, brokerId);
                }
                // submit cloudlet list to the broker
                broker.submitCloudletList(cloudletList);
                //System.out.println("\n\n\n\n" + cloudletList + "\n\n\n\n");
                // these two lines are extra and the name suggests their work....

                // last step: Create Datacenters
                // Datacenters are the resource providers in CloudSim. We need at
                // list one of them to run a CloudSim simulation(done so that the estimation of vms and pes can be done)
                Datacenter datacenter0 = createDatacenter("Datacenter_0");
                //do the binding by the conductance algo
                if(alt) broker.bindByLoadBalancer();
                alt = !alt;
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
            vmlist = null; cloudletList = null;
            maxMips = pesCount = 0;
            delta = 1;
        }
        store();
    }
}
