require 'java'

class HelloJob < Jobs::BaseJob
  def perform_job
    java.lang.System.out.println 'HELLO'
  end
end