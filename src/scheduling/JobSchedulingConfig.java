package scheduling;

import backtracker.Configuration;
import java.util.*;

/**
 * This class for the job scheduling configuration. This will place the appropriate
 * jobs into the number of machine so that the jobs will not exceed the time limit
 * @authors William Johnson
 * @emails wcj7833
 */
public class JobSchedulingConfig implements Configuration{
    List<Machine> machines = new ArrayList<>();
    private Map<String,Job> jobs;
    private Job current;
    private TreeSet<Job> sortedjobs;
    private int time_limit;
    private int num_machines;

    /**
     * The constructor for the Job Scheduling configuration
     * @param jobs The jobs that will be scheduled
     * @param time_limit the time limit for the scheduling configuration
     * @param num_machines the number of machines
     */
    public JobSchedulingConfig(Map<String,Job> jobs, int time_limit, int num_machines) {
        this.jobs = jobs;
        this.time_limit = time_limit;
        this.num_machines = num_machines;
        for (int i = 0; i<this.num_machines;i++){
            Machine machine = new Machine(i,time_limit);
            machines.add(machine);
        }
        this.sortedjobs = new TreeSet<>((j1,j2) -> {
           int diff = j1.getRank() - j2.getRank();
           if (diff!=0){
               return diff;
           }
           else {
               return j1.getName().compareTo(j2.getName());
           }
        });
        this.current =null;


    }

    /**
     * Creates a duplicate JobSchedulingConfig based on the duplicated version
     * @param duplicate the JobSchedulingConfig that will be duplicated
     */
    private JobSchedulingConfig(JobSchedulingConfig duplicate){
        this.current = duplicate.current;
        this.time_limit = duplicate.time_limit;
        this.sortedjobs = new TreeSet<>(duplicate.sortedjobs);
        this.jobs = new HashMap<>(duplicate.jobs);
        this.machines = new ArrayList<>(duplicate.machines);
    }

    /**
     * Displays the Job Order for how th emachine will be assessed
     */
    public void displayJobsAssignmentOrder() {

        Job[] joblist = new Job[jobs.values().size()];
        joblist =jobs.values().toArray(joblist);
        ArrayList<Job> j = new ArrayList<>(Arrays.asList(joblist));
        int smallest = 0;
        while (j.size()>0){
            int count = 0;
            while (count<j.size()){
                if (j.get(count)!= null) {
                    if (j.get(count).getRank() == smallest) {
                        System.out.print(j.get(count).getName() + " ");
                        j.remove(count);
                    } else {
                        count++;
                    }
                }
                else{
                    count++;
                }
            }
            smallest++;
        }
        System.out.println();
    }
    /**
     * Get the collection of successors from the current one.
     *
     * @return All successors, valid and invalid
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        Collection<Configuration> configurations = new ArrayList<>();
        this.current = this.sortedjobs.first();
        this.sortedjobs.remove(this.current);
        for (int i = 0; i<this.num_machines;i++){
            JobSchedulingConfig a = new JobSchedulingConfig(this);
            a.machines.get(i).addJob(this.current,jobs);
            configurations.add(a);
        }
        return configurations;
    }

    /**
     * Is the current configuration valid or not?
     *
     * @return true if valid; false otherwise
     */
    @Override
    public boolean isValid() {
        for (Machine machine:machines){
            if (machine.getCurrent_time()>machine.getTime_limit()){
                return false;
            }
        }
        return true;
    }

    /**
     * Is the current configuration a goal?
     *
     * @return true if goal; false otherwise
     */
    @Override
    public boolean isGoal() {
        return isValid();
    }
}
