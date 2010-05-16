class HelloScheduler < BaseScheduler
  
  def initialize(cron_expression) 
    super
    @cron_expression = cron_expression
    @base_jobs_group = "HelloJobs" 
    @base_triggers_group = "HelloTriggers"
  end
end