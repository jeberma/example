if defined?(JRUBY_VERSION)
  for jar in %w(commons-collections-3.2.1 commons-logging-1.1.1 log4j-1.2.14 quartz-1.6.1)
    require File.join(RAILS_ROOT, 'lib', jar)
  end
  
  HelloScheduler.new("10 * * * * ?").schedule!(HelloJob)
end