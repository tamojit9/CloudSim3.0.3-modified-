/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataCenterBrokerModified;

import java.util.Comparator;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.Vm;

/**
 *
 * @author Tamojit9
 */
public class VmComparator extends Vm implements Comparator<VmComparator> {
    public int estimatedTime; //when this Vm will be free
    
    public VmComparator(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String vmm, CloudletScheduler cloudletScheduler) {
        super(id, userId, mips, numberOfPes, ram, bw, size, vmm, cloudletScheduler);
    }

    @Override
    public int compare(VmComparator t, VmComparator t1) {
        if(t.estimatedTime == t1.estimatedTime) { 
            return t.getMips() >= t1.getMips() ? -1: 1;
        }
        return t.estimatedTime <= t1.estimatedTime ? -1 : 1;
    }
    
}
