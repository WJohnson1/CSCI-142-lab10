package scheduling;

import java.util.LinkedList;

public class Machine {
    private LinkedList<Job> jobs;
    private int id;
    private int time_limit;
    private int current_time = 0;
    public Machine(int id, int time_limit){
        this.id = id;
        this.time_limit = time_limit;
    }
    public void addJob(Job job){
        if (job.getCosts()[this.id]+current_time<=time_limit){
            jobs.addLast(job);
            current_time+=job.getCosts()[this.id];
        }
    }
    public boolean stillActive(Job job){
        if (job.getCosts()[this.id]<(time_limit-current_time)) {
            return true;
        }
        else{
            return false;
        }
    }

    public int getCurrent_time() {
        return current_time;
    }

    public int getTime_limit() {
        return time_limit;
    }
}
