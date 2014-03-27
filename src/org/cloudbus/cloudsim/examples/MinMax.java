/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cloudbus.cloudsim.examples;

import DataCenterBrokerModified.DatacenterBrokerModifiedConductance;
import java.awt.HeadlessException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
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
class VmComparator extends Vm implements Comparable<Vm>{

    public VmComparator(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String vmm, CloudletScheduler cloudletScheduler) {
        super(id, userId, mips, numberOfPes, ram, bw, size, vmm, cloudletScheduler);
    }

    @Override
    public int compareTo(Vm t) {
        return getMips() >= t.getMips() ? -1 : 1;
    }
    
}

public class MinMax {
        /** The cloudlet list. */
	private static List<CloudletComparator> cloudletList;
        
        /** The vmlist. */
	private static List<VmComparator> vmlist;
        
        static String output = "";
        
        static int id = -1;
        
        private static Datacenter createDatacenter(String name) {

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		// our machine
		List<Host> hostList = new ArrayList<>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<>();

		int mips = 1000000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(1, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(2, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(3, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(4, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(5, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
                peList.add(new Pe(6, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		// 4. Create Host with its id and list of PEs and add them to the list
		// of machines
		int hostId = 0;
		int ram = 2048000; // host memory (MB)
		long storage = 1000000000; // host storage
		int bw = 100000000;

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
        
        private static DatacenterBrokerModifiedConductance createBroker() {
		DatacenterBrokerModifiedConductance broker = null;
		try {
			broker = new DatacenterBrokerModifiedConductance("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
        
        private static void printCloudletList(List<CloudletComparator> list) {
		int size = list.size();
		CloudletComparator cloudlet;

		String indent = "    ";
		Log.printLine();
		output += "\n" +  ("========== OUTPUT ==========");
		output += "\n" +  ("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
				+ "Start Time" + indent + "Finish Time" + indent + "Time Taken");

		DecimalFormat dft = new DecimalFormat("###.##");
                double time = 0;
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			output += "\n    " + (indent + cloudlet.getCloudletId() + indent + indent);
			if (cloudlet.getCloudletStatus() == CloudletComparator.SUCCESS) {
				output += ("    SUCCESS         ");

				output +=   (indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime()) + indent + dft.format(cloudlet.getActualCPUTime()));
                                time += cloudlet.getActualCPUTime();
			}
		}
                System.out.println(indent + "Make Span : " + time);
	}
        
        private static void iniCloudSim3_0_3(int num_user) {
            output += "\n" +  ("Starting CloudSimExample1...");
            
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // mean trace events
            
            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);
        }
        
        //Sir, in this case this is also the function that i have modified.
        private static void iniVmList(int brokerId) {
            // Fourth step: Create one virtual machine
            vmlist = new ArrayList<>();

            // VM description
            int vmid = 0;
            int mips = 1000; //processor speed
            long size = 10000; // image size (MB)
            int ram = 512; // vm memory (MB)
            long bw = 1000;
            int pesNumber = 1; // number of cpus
            String vmm = "Xen"; // VMM name
            
            String no_of_cloudlets = null, difference = null;
            
            while(no_of_cloudlets == null) {
                no_of_cloudlets = (JOptionPane.showInputDialog("Enter No. of Vms???"));
            }
            
            while(difference == null) {
                difference = (JOptionPane.showInputDialog("Variation in lengths of Cloudlets ???\n 1> Huge\n 2> Medium\n 3> Low"));
            }
            int deviation ;
            
            switch(difference) {
                case "1" :
                    deviation = 4000;
                    break;
                case "2" :
                    deviation = 2000;
                    break;
                default:
                    deviation = 1000;
            }
            
            for (int i = 0; i < Integer.parseInt(no_of_cloudlets); i++) {
                // create VM
                VmComparator vm = new VmComparator(vmid, brokerId, mips + Math.random()*deviation, (int) (pesNumber + 4*Math.random()), (int) (ram + 512*Math.random()), (long) (bw + 1024*Math.random()), size, vmm, new CloudletSchedulerSpaceShared());

                // add the VM to the vmList
                vmlist.add(vm);
            }
            //Collections.sort(vmlist);
        }
        
        private static void setVmId(CloudletComparator cc) {
            id = (id+1)%vmlist.size();
            cc.setVmId(vmlist.get(id).getId());
        }
        
        //Sir, this is the function i modified.
        private static void iniCloudletList(int pesNumber, int vmid, int brokerId) {
            // Fifth step: Create one CloudletComparator
            cloudletList = new ArrayList<>();

            // CloudletComparator properties
            int id = 0;
            long length = 400000;
            long fileSize = 300;
            long outputSize = 300;
            UtilizationModel utilizationModel = new UtilizationModelFull();
            
            String no_of_cloudlets = null, difference = null;
            
            while(no_of_cloudlets == null) {
                no_of_cloudlets = (JOptionPane.showInputDialog("Enter No. of Cloudlets ???"));
            }
            
            while(difference == null) {
                difference = (JOptionPane.showInputDialog("Variation in lengths of Cloudlets ???\n 1> Huge\n 2> Medium\n 3> Low"));
            }
            int deviation ;
            
            switch(difference) {
                case "1" :
                    deviation = 4000000;
                case "2" :
                    deviation = 2000000;
                default:
                    deviation = 1000000;
            }
            
            for (int i = 0; i < Integer.parseInt(no_of_cloudlets); i++) {
                CloudletComparator cloudlet = new CloudletComparator(id, (long) (length + deviation*Math.random()), (int) (pesNumber + 4*Math.random()), (long) (fileSize + 700*Math.random()), (long) (outputSize + 700*Math.random()), utilizationModel, utilizationModel, utilizationModel);
                cloudlet.setUserId(brokerId);
                setVmId(cloudlet);
            
                // add the cloudlet to the list
                cloudletList.add(cloudlet);
            }
            Collections.sort(cloudletList);
        }
        
        public static void main(String[] argv) {
            try {
                int num_user = 1;
                iniCloudSim3_0_3(num_user);
                
                Datacenter datacenter0 = createDatacenter("Datacenter_0");
                
                DatacenterBrokerModifiedConductance broker = createBroker();
                int brokerId = broker.getId();
                
                iniVmList(brokerId);
                broker.submitVmList(vmlist);
                
                iniCloudletList(vmlist.get(0).getNumberOfPes(), vmlist.get(0).getId(), brokerId);

                // submit cloudlet list to the broker
                broker.submitCloudletList(cloudletList);
                try {
                    broker.bindByLoadBalancer();
                } catch (Exception ex) {
                    Logger.getLogger(MinMax.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Sixth step: Starts the simulation
                CloudSim.startSimulation();
                CloudSim.stopSimulation();
                
                //Final step: Print results when simulation is over
                List<CloudletComparator> newList = broker.getCloudletReceivedList();
                printCloudletList(newList);

                output += "\n" +  ("CloudSimExample1 finished!");
                
                JOptionPane.showMessageDialog(null, output);
            } catch(NullPointerException | HeadlessException e) {
            }
        }
}

