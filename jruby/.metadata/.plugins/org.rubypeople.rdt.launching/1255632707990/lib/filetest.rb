=begin
-------------------------------------------------------- Class: FileTest
     +FileTest+ implements file test operations similar to those used in
     +File::Stat+. It exists as a standalone module, and its methods are
     also insinuated into the +File+ class. (Note that this is not done
     by inclusion: the interpreter cheats).

------------------------------------------------------------------------


Instance methods:
-----------------
     blockdev?, chardev?, directory?, executable?, executable_real?,
     exist?, exists?, file?, grpowned?, identical?, owned?, pipe?,
     readable?, readable_real?, setgid?, setuid?, size, size?, socket?,
     sticky?, symlink?, writable?, writable_real?, zero?

=end
module FileTest

  def self.executable_real?(arg0)
  end

  def self.readable_real?(arg0)
  end

  def self.symlink?(arg0)
  end

  def self.setgid?(arg0)
  end

  def self.socket?(arg0)
  end

  def self.setuid?(arg0)
  end

  def self.blockdev?(arg0)
  end

  def self.writable?(arg0)
  end

  def self.readable?(arg0)
  end

  def self.grpowned?(arg0)
  end

  def self.zero?(arg0)
  end

  def self.identical?(arg0, arg1)
  end

  def self.file?(arg0)
  end

  def self.exist?(arg0)
  end

  def self.size?(arg0)
  end

  def self.owned?(arg0)
  end

  def self.pipe?(arg0)
  end

  def self.sticky?(arg0)
  end

  def self.writable_real?(arg0)
  end

  def self.executable?(arg0)
  end

  def self.chardev?(arg0)
  end

  def self.size(arg0)
  end

  def self.exists?(arg0)
  end

  def self.directory?(arg0)
  end

  # ---------------------------------------------- FileTest#executable_real?
  #      File.executable_real?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is executable by the real user id
  #      of this process.
  # 
  def executable_real?(arg0)
  end

  # ------------------------------------------------ FileTest#readable_real?
  #      File.readable_real?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is readable by the real user id of
  #      this process.
  # 
  def readable_real?(arg0)
  end

  # ------------------------------------------------------ FileTest#symlink?
  #      File.symlink?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a symbolic link.
  # 
  def symlink?(arg0)
  end

  # ------------------------------------------------------- FileTest#setgid?
  #      File.setgid?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file has the setgid bit set.
  # 
  def setgid?(arg0)
  end

  # ------------------------------------------------------- FileTest#socket?
  #      File.socket?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a socket.
  # 
  def socket?(arg0)
  end

  # ------------------------------------------------------- FileTest#setuid?
  #      File.setuid?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file has the setuid bit set.
  # 
  def setuid?(arg0)
  end

  # ----------------------------------------------------- FileTest#blockdev?
  #      File.blockdev?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a block device.
  # 
  def blockdev?(arg0)
  end

  # ----------------------------------------------------- FileTest#writable?
  #      File.writable?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is writable by the effective user
  #      id of this process.
  # 
  def writable?(arg0)
  end

  # ----------------------------------------------------- FileTest#readable?
  #      File.readable?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is readable by the effective user
  #      id of this process.
  # 
  def readable?(arg0)
  end

  # ----------------------------------------------------- FileTest#grpowned?
  #      File.grpowned?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file exists and the effective group id
  #      of the calling process is the owner of the file. Returns +false+ on
  #      Windows.
  # 
  def grpowned?(arg0)
  end

  # --------------------------------------------------------- FileTest#zero?
  #      File.zero?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file exists and has a zero size.
  # 
  def zero?(arg0)
  end

  # ---------------------------------------------------- FileTest#identical?
  #      File.identical?(file_1, file_2)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named files are identical.
  # 
  #          open("a", "w") {}
  #          p File.identical?("a", "a")      #=> true
  #          p File.identical?("a", "./a")    #=> true
  #          File.link("a", "b")
  #          p File.identical?("a", "b")      #=> true
  #          File.symlink("a", "c")
  #          p File.identical?("a", "c")      #=> true
  #          open("d", "w") {}
  #          p File.identical?("a", "d")      #=> false
  # 
  def identical?(arg0, arg1)
  end

  # --------------------------------------------------------- FileTest#file?
  #      File.file?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file exists and is a regular file.
  # 
  def file?(arg0)
  end

  # -------------------------------------------------------- FileTest#exist?
  #      File.exist?(file_name)    =>  true or false
  #      File.exists?(file_name)   =>  true or false    (obsolete)
  # ------------------------------------------------------------------------
  #      Return +true+ if the named file exists.
  # 
  def exist?(arg0)
  end

  # --------------------------------------------------------- FileTest#size?
  #      File.size?(file_name)   => Integer or nil
  # ------------------------------------------------------------------------
  #      Returns +nil+ if +file_name+ doesn't exist or has zero size, the
  #      size of the file otherwise.
  # 
  def size?(arg0)
  end

  # -------------------------------------------------------- FileTest#owned?
  #      File.owned?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file exists and the effective used id
  #      of the calling process is the owner of the file.
  # 
  def owned?(arg0)
  end

  # --------------------------------------------------------- FileTest#pipe?
  #      File.pipe?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a pipe.
  # 
  def pipe?(arg0)
  end

  # ------------------------------------------------------- FileTest#sticky?
  #      File.sticky?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file has the sticky bit set.
  # 
  def sticky?(arg0)
  end

  # ------------------------------------------------ FileTest#writable_real?
  #      File.writable_real?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is writable by the real user id of
  #      this process.
  # 
  def writable_real?(arg0)
  end

  # --------------------------------------------------- FileTest#executable?
  #      File.executable?(file_name)   => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is executable by the effective
  #      user id of this process.
  # 
  def executable?(arg0)
  end

  # ------------------------------------------------------ FileTest#chardev?
  #      File.chardev?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a character device.
  # 
  def chardev?(arg0)
  end

  # ---------------------------------------------------------- FileTest#size
  #      File.size(file_name)   => integer
  # ------------------------------------------------------------------------
  #      Returns the size of +file_name+.
  # 
  def size(arg0)
  end

  # ------------------------------------------------------- FileTest#exists?
  #      File.exist?(file_name)    =>  true or false
  #      File.exists?(file_name)   =>  true or false    (obsolete)
  # ------------------------------------------------------------------------
  #      Return +true+ if the named file exists.
  # 
  def exists?(arg0)
  end

  # ---------------------------------------------------- FileTest#directory?
  #      File.directory?(file_name)   =>  true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if the named file is a directory, +false+ otherwise.
  # 
  #         File.directory?(".")
  # 
  def directory?(arg0)
  end

end
