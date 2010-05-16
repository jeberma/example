=begin
---------------------------------------------- Class: SizedQueue < Queue
     This class represents queues of specified size capacity. The push
     operation may be blocked if the capacity is full.

     See Queue for an example of how a SizedQueue works.

------------------------------------------------------------------------
     This class represents queues of specified size capacity. The push
     operation may be blocked if the capacity is full.

     See Queue for an example of how a SizedQueue works.

------------------------------------------------------------------------


Class methods:
--------------
     new


Instance methods:
-----------------
     <<, clear, deq, empty?, enq, length, max, max=, num_waiting, pop,
     push, shift

=end
class SizedQueue < Queue

  def push
  end

  # --------------------------------------------------------- SizedQueue#deq
  #      deq(*args)
  # ------------------------------------------------------------------------
  #      Alias for #pop
  # 
  def deq
  end

  def max
  end

  # --------------------------------------------------------- SizedQueue#enq
  #      enq(obj)
  # ------------------------------------------------------------------------
  #      Alias for #push
  # 
  def enq
  end

  # ---------------------------------------------------------- SizedQueue#<<
  #      <<(obj)
  # ------------------------------------------------------------------------
  #      Alias for #push
  # 
  def <<
  end

  def max=
  end

  # ------------------------------------------------------- SizedQueue#shift
  #      shift(*args)
  # ------------------------------------------------------------------------
  #      Alias for #pop
  # 
  def shift
  end

  def num_waiting
  end

  def pop
  end

end
