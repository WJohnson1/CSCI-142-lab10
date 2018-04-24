package scheduling;

import backtracker.Configuration;
import java.lang.Object;
import java.util.*;

public class JobSchedulingConfig implements Configuration{
    List<Machine> machines = new ArrayList<>();
    private List<Job> jobs;
    public JobSchedulingConfig(List<Job> jobs, int time_limit, int num_machines) {
        for (int i = 0; i<num_machines;i++){
            Machine machine = new Machine(i,time_limit);
            machines.add(machine);
        }
        Job temp;
        for (int i = 1; i < jobs.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(jobs.get(j).getRank() < jobs.get(j-1).getRank()){
                    temp = jobs.get(j);
                    jobs.set(j,jobs.get(j-1));
                    jobs.set(j-1,temp);
                }
            }
        }
        this.jobs = jobs;

    }

    public void displayJobsAssignmentOrder() {

        int smallest = 0;
        while (jobs.size()>0){
            int count = 0;
            while (count<jobs.size()){
                if (jobs.get(count)!= null) {
                    if (jobs.get(count).getRank() == smallest) {
                        System.out.print(jobs.get(count).getName() + " ");
                        jobs.remove(count);
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
        int count;
        for (Machine machine: machines){
            count=0;
            while (machine.stillActive(jobs.get(count))){
                machine.addJob(jobs.get(count));
                count++;
            }

        }

        Collection<Configuration> configurations = new LinkedHashSet<>();
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
