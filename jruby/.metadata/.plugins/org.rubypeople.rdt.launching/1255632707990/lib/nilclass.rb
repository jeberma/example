=begin
-------------------------------------------------------- Class: NilClass
     The class of the singleton object +nil+.

------------------------------------------------------------------------


Instance methods:
-----------------
     &, ^, inspect, nil?, to_a, to_f, to_i, to_s, to_yaml, |

=end
class NilClass < Object

  def self.yaml_tag_subclasses?
  end

  # ------------------------------------------------------- NilClass#inspect
  #      nil.inspect  => "nil"
  # ------------------------------------------------------------------------
  #      Always returns the string "nil".
  # 
  def inspect
  end

  def taguri
  end

  def taguri=(arg0, arg1, *rest)
  end

  # ------------------------------------------------------------- NilClass#^
  #      false ^ obj    => true or false
  #      nil   ^ obj    => true or false
  # ------------------------------------------------------------------------
  #      Exclusive Or---If _obj_ is +nil+ or +false+, returns +false+;
  #      otherwise, returns +true+.
  # 
  def ^(arg0)
  end

  # ---------------------------------------------------------- NilClass#to_i
  #      nil.to_i => 0
  # ------------------------------------------------------------------------
  #      Always returns zero.
  # 
  #         nil.to_i   #=> 0
  # 
  def to_i
  end

  # ------------------------------------------------------------- NilClass#|
  #      false | obj   =>   true or false
  #      nil   | obj   =>   true or false
  # ------------------------------------------------------------------------
  #      Or---Returns +false+ if _obj_ is +nil+ or +false+; +true+
  #      otherwise.
  # 
  def |(arg0)
  end

  def to_yaml_node(arg0)
  end

  def type
  end

  # ---------------------------------------------------------- NilClass#to_f
  #      nil.to_f    => 0.0
  # ------------------------------------------------------------------------
  #      Always returns zero.
  # 
  #         nil.to_f   #=> 0.0
  # 
  def to_f
  end

  # ------------------------------------------------------------- NilClass#&
  #      false & obj   => false
  #      nil & obj     => false
  # ------------------------------------------------------------------------
  #      And---Returns +false+. _obj_ is always evaluated as it is the
  #      argument to a method call---there is no short-circuit evaluation in
  #      this case.
  # 
  def &(arg0)
  end

  # ---------------------------------------------------------- NilClass#to_a
  #      nil.to_a    => []
  # ------------------------------------------------------------------------
  #      Always returns an empty array.
  # 
  #         nil.to_a   #=> []
  # 
  def to_a
  end

  # ---------------------------------------------------------- NilClass#to_s
  #      nil.to_s    => ""
  # ------------------------------------------------------------------------
  #      Always returns the empty string.
  # 
  #         nil.to_s   #=> ""
  # 
  def to_s
  end

  # ---------------------------------------------------------- NilClass#nil?
  #      nil?()
  # ------------------------------------------------------------------------
  #      call_seq:
  # 
  #        nil.nil?               => true
  # 
  #      Only the object _nil_ responds +true+ to +nil?+.
  # 
  def nil?
  end

end
