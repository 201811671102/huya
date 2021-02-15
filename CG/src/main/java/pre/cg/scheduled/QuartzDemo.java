package pre.cg.scheduled;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pre.cg.service.UserService;


/**
 * 定义任务类
 */
public class QuartzDemo implements Job {


    @Autowired
    private UserService userService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*System.out.println("123"+new Date());
        System.out.println(this.userService.selectAll().toString());*/
    }
}
