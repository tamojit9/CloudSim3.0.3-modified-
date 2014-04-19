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
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cloudbus.cloudsim.examples;

import Cloudlet_Scheduler_Time_Shared.CloudletSchedulerTSImprovedRoundRobin;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import DataCenterBrokerModified.DatacenterBrokerModifiedETCMatrix;
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
class CloudletComparator extends Cloudlet implements Comparable<Cloudlet> {

    public CloudletComparator(int cloudletId, long cloudletLength, int pesNumber, long cloudletFileSize, long cloudletOutputSize, UtilizationModel utilizationModelCpu, UtilizationModel utilizationModelRam, UtilizationModel utilizationModelBw) {
        super(cloudletId, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize, utilizationModelCpu, utilizationModelRam, utilizationModelBw);
    }

    @Override
    public int compareTo(Cloudlet t) {
        return this.getCloudletLength() <= t.getCloudletLength() ? -1 : 1;
    }
}

public class ShortestCloudletFirst {
        /** The cloudlet list. */
	private static List<CloudletComparator> cloudletList;
        
        /** The vmlist. */
	private static List<Vm> vmlist;
        
        long totalMips = 0;
        
        static String output = "";
        
        private static Datacenter createDatacenter(String name) {

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		// our machine
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		// 4. Create Host with its id and list of PEs and add them to the list
		// of machines
		int hostId = 0;
		int ram = 2048; // host memory (MB)
		long storage = 1000000; // host storage
		int bw = 10000;

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
        
        private static DatacenterBrokerModifiedETCMatrix createBroker() {
		DatacenterBrokerModifiedETCMatrix broker = null;
		try {
			broker = new DatacenterBrokerModifiedETCMatrix("Broker");
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
				+ "Data center ID" + indent + "VM ID" + indent + "Host ID" + indent +"Time" + indent
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
                                                + indent + indent + vmlist.get(cloudlet.getVmId()).getHost().getId()
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
        
        private static void iniClouSim3_0_3(int num_user) {
            output += "\n" +  ("Starting CloudSimExample1...");
            
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // mean trace events
            
            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);
        }
        
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

            // create VM
            //Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTSImprovedRoundRobin());
            // add the VM to the vmList
            vmlist.add(vm);
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
                difference = (JOptionPane.showInputDialog("Variation in lengths of Cloudlets ???\n 1> Huge\n 2> Medium\n 3> Small"));
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
                cloudlet.setVmId(vmid);
                // add the cloudlet to the list
                cloudletList.add(cloudlet);
            }
            //Collections.sort(cloudletList);
        }
        
        public static void main(String[] argv) {
            try {
                int num_user = 1;
                iniClouSim3_0_3(num_user);
                
                Datacenter datacenter0 = createDatacenter("Datacenter_0");
                
                DatacenterBrokerModifiedETCMatrix broker = createBroker();
                int brokerId = broker.getId();
                
                iniVmList(brokerId);
                broker.submitVmList(vmlist);
                
                iniCloudletList(vmlist.get(0).getNumberOfPes(), vmlist.get(0).getId(), brokerId);

                // submit cloudlet list to the broker
                broker.submitCloudletList(cloudletList);
                broker.bindByMaxMin();
                
                // Sixth step: Starts the simulation
                CloudSim.startSimulation();
                CloudSim.stopSimulation();
                
                //Final step: Print results when simulation is over
                List<CloudletComparator> newList = broker.getCloudletReceivedList();
                printCloudletList(newList);

                output += "\n" +  ("CloudSimExample1 finished!");
                
                JOptionPane.showMessageDialog(null, output);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
}
