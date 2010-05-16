=begin
---------------------------------------------------------- Class: Object
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


Includes:
---------
     Kernel(Array, Float, Integer, Pathname, String, URI, `, abort,
     at_exit, autoload, autoload?, binding, block_given?, callcc,
     caller, catch, chomp, chomp!, chop, chop!, eval, exec, exit, exit!,
     fail, fork, format, getc, gets, global_variables, gsub, gsub!,
     iterator?, lambda, load, local_variables, loop, method_missing,
     open, open, open_uri_original_open, p, pp, pretty_inspect, print,
     printf, proc, putc, puts, raise, rand, readline, readlines,
     require, scan, scanf, select, set_trace_func, sleep, split,
     sprintf, srand, sub, sub!, syscall, system, test, throw, trace_var,
     trap, untrace_var, warn, warn, y), PP::ObjectMixin(pretty_print,
     pretty_print_cycle, pretty_print_inspect,
     pretty_print_instance_variables)


Constants:
----------
     TOPLEVEL_BINDING:  rb_f_binding(ruby_top_self)
     ENV:               envtbl
     ENV:               envtbl
     STDIN:             rb_stdin
     STDOUT:            rb_stdout
     STDERR:            rb_stderr
     ARGF:              argf
     NIL:               Qnil
     TRUE:              Qtrue
     FALSE:             Qfalse
     MatchingData:      rb_cMatch
     DATA:              f
     ARGV:              rb_argv
     RUBY_VERSION:      v
     RUBY_RELEASE_DATE: d
     RUBY_PLATFORM:     p
     RUBY_PATCHLEVEL:   INT2FIX(RUBY_PATCHLEVEL)
     VERSION:           v
     RELEASE_DATE:      d
     PLATFORM:          p
     IPsocket:          rb_cIPSocket
     TCPsocket:         rb_cTCPSocket
     SOCKSsocket:       rb_cSOCKSSocket
     TCPserver:         rb_cTCPServer
     UDPsocket:         rb_cUDPSocket
     UNIXsocket:        rb_cUNIXSocket
     UNIXserver:        rb_cUNIXServer


Class methods:
--------------
     new


Instance methods:
-----------------
     ==, ===, =~, __id__, __send__, class, clone, dclone, display, dup,
     enum_for, eql?, equal?, extend, freeze, frozen?, hash, id, inspect,
     instance_eval, instance_of?, instance_variable_defined?,
     instance_variable_get, instance_variable_get,
     instance_variable_set, instance_variable_set, instance_variables,
     is_a?, kind_of?, method, methods, nil?, object_id, private_methods,
     protected_methods, public_methods, remove_instance_variable,
     respond_to?, send, singleton_method_added,
     singleton_method_removed, singleton_method_undefined,
     singleton_methods, taint, tainted?, to_a, to_enum, to_s, to_yaml,
     to_yaml_properties, to_yaml_style, type, untaint

=end
class Object
  include Kernel

  def self.method_added(arg0)
  end

  def java_kind_of?(arg0)
  end

  def to_yaml_node(arg0)
  end

  # ---------------------------------------------- Object#to_yaml_properties
  #      to_yaml_properties()
  # ------------------------------------------------------------------------
  #      (no description...)
  def to_yaml_properties
  end

  def handle_different_imports(arg0, arg1, *rest)
  end

  def include_class(arg0)
  end

  def taguri
  end

  # --------------------------------------------------------- Object#to_yaml
  #      to_yaml( opts = {} )
  # ------------------------------------------------------------------------
  #      (no description...)
  def to_yaml(arg0, arg1, *rest)
  end

  # --------------------------------------------------- Object#to_yaml_style
  #      to_yaml_style()
  # ------------------------------------------------------------------------
  #      (no description...)
  def to_yaml_style
  end

end
