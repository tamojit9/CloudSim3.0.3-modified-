CloudSim3.0.3-modified-
=======================

This is a netbeans project that has many customized scheduling policies and data center brokers.like for example the famous algorithms like  :

      shortest cloudlet first, 
      shortest remainig time first,
      longest remaining time first,
      Improved Round Robin(in both time shared mode and space shared mode).


It also has various modified data center brokers like :

      ETC matrix broker(uses the ETC matrix for sceduling cloudlets to Vms), 
      min-max, 
      min-min(uses ETC matrix). 
      
      A conductance Datacenter Broker which uses a mapping algorithm named('CONDUCTANCE ALGORITHM') proposed by me.

      A real time Datacenter broker which tries to mimick the behaviour of real world Cloud Computing situations by  
      not sending all the cloudlets at once but at irregular intervals. It also has an online-scheduling policy to map 
      the cloudlets to the Vms in the most efficient manner as and when they arrive (but this broker lacks a good      
      example that shows it in action we are currently in the process of developing one).


It also has certain modified examples showing how to :

      make the process of cloudlet creationand vm creation automated. 
      an example where the simulation can be repeated as many no. of times u want and the no. of cloudlets and no. of  
      vms are taken randomly at runtime. This example also generates a file(location can be set) where every line 
      consists of two items, the first one is the number of cloudlets that are run and the next item corresponding to
      total execution time(for plottting of data or results comparisons).
      Th Example can also be modified a bit to run different scheduling policies at different times(iterations).

Some file names are annoyingly long and following abbreviations are used :
      
        TS --> Time Shared
        SS --> Space Shared
        SRTF --> Shorest Remainig Time First
        SJF --> Shorest Job First
        IRR --> Improved Round Robin
        
Note :- not all algorithms work as expected due to some of the limitations of the simulation environment and also the way cloudSim is desinged. For example the ETC matrix won't work as expected since the only parameter taken into consideration which is the Expected Finsih Time. So, all the cloudlets will find it suitable to run in the fastest Vm(having the highest MIPS).
