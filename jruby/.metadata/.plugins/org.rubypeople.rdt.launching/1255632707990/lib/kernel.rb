=begin
---------------------------------------------------------- Class: Kernel
     +Object+ is the parent class of all classes in Ruby. Its methods
     are therefore available to all objects unless explicitly
     overridden.

     +Object+ mixes in the +Kernel+ module, making the built-in kernel
     functions globally accessible. Although the instance methods of
     +Object+ are defined by the +Kernel+ module, we have chosen to
     document them here for clarity.

     In the descriptions of Object's methods, the parameter _symbol_
     refers to a symbol, which is either a quoted string or a +Symbol+
     (such as +:name+).

------------------------------------------------------------------------


Instance methods:
-----------------
     Array, Float, Integer, Pathname, String, URI, `, abort, at_exit,
     autoload, autoload?, binding, block_given?, callcc, caller, catch,
     chomp, chomp!, chop, chop!, eval, exec, exit, exit!, fail, fork,
     format, getc, gets, global_variables, gsub, gsub!, iterator?,
     lambda, load, local_variables, loop, method_missing, open, open,
     open_uri_original_open, p, pp, pretty_inspect, print, printf, proc,
     putc, puts, raise, rand, readline, readlines, require, scan, scanf,
     select, set_trace_func, sleep, split, sprintf, srand, sub, sub!,
     syscall, system, test, throw, trace_var, trap, untrace_var, warn,
     warn, y

=end
module Kernel

  def self.caller(arg0, arg1, *rest)
  end

  def self.autoload(arg0, arg1)
  end

  def self.syscall(arg0, arg1, *rest)
  end

  def self.untrace_var(arg0, arg1, *rest)
  end

  def self.rand(arg0, arg1, *rest)
  end

  def self.p(arg0, arg1, *rest)
  end

  def self.putc(arg0)
  end

  def self.printf(arg0, arg1, *rest)
  end

  def self.raise(arg0, arg1, *rest)
  end

  def self.srand(arg0, arg1, *rest)
  end

  def self.eval(arg0, arg1, *rest)
  end

  def self.fork
  end

  def self.open(arg0, arg1, *rest)
  end

  def self.Array(arg0)
  end

  def self.singleton_method_undefined(arg0)
  end

  def self.scan(arg0)
  end

  def self.`(arg0)
  end

  def self.set_trace_func(arg0)
  end

  def self.sleep(arg0, arg1, *rest)
  end

  def self.URI(arg0, arg1, *rest)
  end

  def self.test(arg0, arg1, *rest)
  end

  def self.callcc
  end

  def self.fail(arg0, arg1, *rest)
  end

  def self.block_given?
  end

  def self.exit!(arg0, arg1, *rest)
  end

  def self.exec(arg0, arg1, *rest)
  end

  def self.throw(arg0, arg1, *rest)
  end

  def self.catch(arg0)
  end

  def self.readline(arg0, arg1, *rest)
  end

  def self.method_missing(arg0, arg1, *rest)
  end

  def self.split(arg0, arg1, *rest)
  end

  def self.select(arg0, arg1, *rest)
  end

  def self.Integer(arg0)
  end

  def self.load(arg0, arg1, *rest)
  end

  def self.trap(arg0, arg1, *rest)
  end

  def self.print(arg0, arg1, *rest)
  end

  def self.local_variables
  end

  def self.puts(arg0, arg1, *rest)
  end

  def self.binding
  end

  def self.format(arg0, arg1, *rest)
  end

  def self.readlines(arg0, arg1, *rest)
  end

  def self.sub(arg0, arg1, *rest)
  end

  def self.autoload?(arg0)
  end

  def self.gets(arg0, arg1, *rest)
  end

  def self.sprintf(arg0, arg1, *rest)
  end

  def self.trace_var(arg0, arg1, *rest)
  end

  def self.chomp!(arg0, arg1, *rest)
  end

  def self.gsub(arg0, arg1, *rest)
  end

  def self.system(arg0, arg1, *rest)
  end

  def self.at_exit
  end

  def self.chop!
  end

  def self.singleton_method_removed(arg0)
  end

  def self.String(arg0)
  end

  def self.abort(arg0, arg1, *rest)
  end

  def self.proc
  end

  def self.sub!(arg0, arg1, *rest)
  end

  def self.exit(arg0, arg1, *rest)
  end

  def self.chop
  end

  def self.Float(arg0)
  end

  def self.chomp(arg0, arg1, *rest)
  end

  def self.lambda
  end

  def self.gsub!(arg0, arg1, *rest)
  end

  def self.global_variables
  end

  def self.warn(arg0)
  end

  def self.loop
  end

  def self.require(arg0)
  end

  def self.getc
  end

  def self.singleton_method_added(arg0)
  end

  def self.iterator?
  end

  def object_id
  end

  def frozen?
  end

  def eql?(arg0)
  end

  def methods(arg0, arg1, *rest)
  end

  def =~(arg0)
  end

  def to_a
  end

  def tainted?
  end

  def javax
  end

  def taint
  end

  def public_methods(arg0, arg1, *rest)
  end

  def dup
  end

  # -------------------------------------------------- Kernel#method_missing
  #      obj.method_missing(symbol [, *args] )   => result
  # ------------------------------------------------------------------------
  #      Invoked by Ruby when _obj_ is sent a message it cannot handle.
  #      _symbol_ is the symbol for the method called, and _args_ are any
  #      arguments that were passed to it. By default, the interpreter
  #      raises an error when this method is called. However, it is possible
  #      to override the method to provide more dynamic behavior. The
  #      example below creates a class +Roman+, which responds to methods
  #      with names consisting of roman numerals, returning the
  #      corresponding integer values.
  # 
  #         class Roman
  #           def romanToInt(str)
  #             # ...
  #           end
  #           def method_missing(methId)
  #             str = methId.id2name
  #             romanToInt(str)
  #           end
  #         end
  #      
  #         r = Roman.new
  #         r.iv      #=> 4
  #         r.xxiii   #=> 23
  #         r.mm      #=> 2000
  # 
  def method(arg0)
  end

  def ==(arg0)
  end

  def singleton_methods(arg0, arg1, *rest)
  end

  def is_a?(arg0)
  end

  def instance_eval(arg0, arg1, *rest)
  end

  def equal?(arg0)
  end

  def instance_variable_get(arg0)
  end

  def com
  end

  def instance_of?(arg0)
  end

  def hash
  end

  # -------------------------------------------------- Kernel#pretty_inspect
  #      pretty_inspect()
  # ------------------------------------------------------------------------
  #      returns a pretty printed object as a string.
  # 
  def inspect
  end

  def java
  end

  def org
  end

  def __id__
  end

  def clone
  end

  def __send__(arg0, arg1, *rest)
  end

  def id
  end

  def instance_variable_set(arg0, arg1)
  end

  def send(arg0, arg1, *rest)
  end

  def untaint
  end

  def class
  end

  def protected_methods(arg0, arg1, *rest)
  end

  def kind_of?(arg0)
  end

  def instance_variable_defined?(arg0)
  end

  def instance_variables
  end

  def display(arg0, arg1, *rest)
  end

  def to_s
  end

  def extend(arg0, arg1, *rest)
  end

  def freeze
  end

  def private_methods(arg0, arg1, *rest)
  end

  def nil?
  end

  def type
  end

  def ===(arg0)
  end

  def instance_exec(arg0, arg1, *rest)
  end

  def respond_to?(arg0, arg1, *rest)
  end

end
