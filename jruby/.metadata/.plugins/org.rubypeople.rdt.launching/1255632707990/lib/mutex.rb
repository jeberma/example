=begin
----------------------------------------------------------- Class: Mutex
     Mutex implements a simple semaphore that can be used to coordinate
     access to shared data from multiple concurrent threads.

     Example:

       require 'thread'
       semaphore = Mutex.new
     
       a = Thread.new {
         semaphore.synchronize {
           # access shared resource
         }
       }
     
       b = Thread.new {
         semaphore.synchronize {
           # access shared resource
         }
       }

------------------------------------------------------------------------


Class methods:
--------------
     new


Instance methods:
-----------------
     exclusive_unlock, lock, locked?, synchronize, try_lock, unlock

=end
class Mutex < Object

  # ------------------------------------------------------------- Mutex::new
  #      Mutex::new()
  # ------------------------------------------------------------------------
  #      Creates a new Mutex
  # 
  def self.new(arg0, arg1, *rest)
  end

  # ---------------------------------------------------------- Mutex#locked?
  #      locked?()
  # ------------------------------------------------------------------------
  #      Returns +true+ if this lock is currently held by some thread.
  # 
  def locked?
  end

  # ----------------------------------------------------------- Mutex#unlock
  #      unlock()
  # ------------------------------------------------------------------------
  #      Releases the lock. Returns +nil+ if ref wasn't locked.
  # 
  def unlock
  end

  # --------------------------------------------------------- Mutex#try_lock
  #      try_lock()
  # ------------------------------------------------------------------------
  #      Attempts to obtain the lock and returns immediately. Returns +true+
  #      if the lock was granted.
  # 
  def try_lock
  end

  # ------------------------------------------------------------- Mutex#lock
  #      lock()
  # ------------------------------------------------------------------------
  #      Attempts to grab the lock and waits if it isn't available.
  # 
  def lock
  end

  # ------------------------------------------------------ Mutex#synchronize
  #      synchronize() {|| ...}
  # ------------------------------------------------------------------------
  #      Obtains a lock, runs the block, and releases the lock when the
  #      block completes. See the example under Mutex.
  # 
  def synchronize
  end

end
