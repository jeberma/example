=begin
------------------------------------------------------------ Class: Time
     Implements the extensions to the Time class that are described in
     the documentation for the time.rb library.

------------------------------------------------------------------------


Includes:
---------
     Comparable(<, <=, ==, >, >=, between?)


Constants:
----------
     ZoneOffset:          {       'UTC' => 0,       # ISO 8601       'Z'
                          => 0,       # RFC 822       'UT' => 0, 'GMT'
                          => 0,       'EST' => -5, 'EDT' => -4,      
                          'CST' => -6, 'CDT' => -5,       'MST' => -7,
                          'MDT' => -6,       'PST' => -8, 'PDT' => -7,  
                              # Following definition of military zones
                          is original one.       # See RFC 1123 and RFC
                          2822 for the error in RFC 822.       'A' =>
                          +1, 'B' => +2, 'C' => +3, 'D' => +4,  'E' =>
                          +5,  'F' => +6,        'G' => +7, 'H' => +8,
                          'I' => +9, 'K' => +10, 'L' => +11, 'M' => +12,
                                'N' => -1, 'O' => -2, 'P' => -3, 'Q' =>
                          -4,  'R' => -5,  'S' => -6,        'T' => -7,
                          'U' => -8, 'V' => -9, 'W' => -10, 'X' => -11,
                          'Y' => -12,     }
     LeapYearMonthDays:   [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30,
                          31]
     CommonYearMonthDays: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
                          31]
     MonthValue:          {       'JAN' => 1, 'FEB' => 2, 'MAR' => 3,
                          'APR' => 4, 'MAY' => 5, 'JUN' => 6,      
                          'JUL' => 7, 'AUG' => 8, 'SEP' => 9, 'OCT'
                          =>10, 'NOV' =>11, 'DEC' =>12
     RFC2822_DAY_NAME:    [     'Sun', 'Mon', 'Tue', 'Wed', 'Thu',
                          'Fri', 'Sat'
     RFC2822_MONTH_NAME:  [     'Jan', 'Feb', 'Mar', 'Apr', 'May',
                          'Jun',     'Jul', 'Aug', 'Sep', 'Oct', 'Nov',
                          'Dec'


Class methods:
--------------
     _load, apply_offset, at, gm, httpdate, local, make_time, mktime,
     month_days, new, now, parse, rfc2822, times, utc, w3cdtf,
     xmlschema, yaml_new, zone_offset, zone_utc?


Instance methods:
-----------------
     +, -, <=>, _dump, asctime, ctime, day, dst?, eql?, getgm, getlocal,
     getutc, gmt?, gmt_offset, gmtime, gmtoff, hash, hour, httpdate,
     inspect, isdst, iso8601, localtime, marshal_dump, marshal_load,
     mday, min, mon, month, rfc2822, rfc822, sec, strftime, succ, to_a,
     to_date, to_datetime, to_f, to_i, to_s, to_yaml, tv_sec, tv_usec,
     usec, utc, utc?, utc_offset, wday, xmlschema, yday, year, zone

=end
class Time < Object
  include Comparable

  # -------------------------------------------------------------- Time::now
  #      Time.new -> time
  # ------------------------------------------------------------------------
  #      Synonym for +Time.new+. Returns a +Time+ object initialized tot he
  #      current system time.
  # 
  #      Returns a +Time+ object initialized to the current system time.
  #      *Note:* The object created will be created using the resolution
  #      available on your system clock, and so may include fractional
  #      seconds.
  # 
  #         a = Time.new      #=> Wed Apr 09 08:56:03 CDT 2003
  #         b = Time.new      #=> Wed Apr 09 08:56:03 CDT 2003
  #         a == b            #=> false
  #         "%.6f" % a.to_f   #=> "1049896563.230740"
  #         "%.6f" % b.to_f   #=> "1049896563.231466"
  # 
  def self.now
  end

  # -------------------------------------------------------------- Time::utc
  #      Time.utc( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.utc( sec, min, hour, day, month, year, wday, yday, isdst, tz
  #      ) => time
  #      Time.gm( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.gm( sec, min, hour, day, month, year, wday, yday, isdst, tz
  #      ) => time
  # ------------------------------------------------------------------------
  #      Creates a time based on given values, interpreted as UTC (GMT). The
  #      year must be specified. Other values default to the minimum value
  #      for that field (and may be +nil+ or omitted). Months may be
  #      specified by numbers from 1 to 12, or by the three-letter English
  #      month names. Hours are specified on a 24-hour clock (0..23). Raises
  #      an +ArgumentError+ if any values are out of range. Will also accept
  #      ten arguments in the order output by +Time#to_a+.
  # 
  #         Time.utc(2000,"jan",1,20,15,1)  #=> Sat Jan 01 20:15:01 UTC 2000
  #         Time.gm(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  # 
  def self.utc(arg0, arg1, *rest)
  end

  # ------------------------------------------------------------ Time::_load
  #      Time._load(string)   => time
  # ------------------------------------------------------------------------
  #      Unmarshal a dumped +Time+ object.
  # 
  def self._load(arg0)
  end

  def self.yaml_tag_subclasses?
  end

  # ------------------------------------------------------------ Time::local
  #      Time.local( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.local( sec, min, hour, day, month, year, wday, yday, isdst,
  #      tz ) => time
  #      Time.mktime( year, month, day, hour, min, sec, usec )   => time
  # ------------------------------------------------------------------------
  #      Same as +Time::gm+, but interprets the values in the local time
  #      zone.
  # 
  #         Time.local(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 CST 2000
  # 
  def self.local(arg0, arg1, *rest)
  end

  # ----------------------------------------------------------- Time::mktime
  #      Time.local( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.local( sec, min, hour, day, month, year, wday, yday, isdst,
  #      tz ) => time
  #      Time.mktime( year, month, day, hour, min, sec, usec )   => time
  # ------------------------------------------------------------------------
  #      Same as +Time::gm+, but interprets the values in the local time
  #      zone.
  # 
  #         Time.local(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 CST 2000
  # 
  def self.mktime(arg0, arg1, *rest)
  end

  # ------------------------------------------------------------ Time::times
  #      Time.times => struct_tms
  # ------------------------------------------------------------------------
  #      Deprecated in favor of +Process::times+
  # 
  def self.times
  end

  # --------------------------------------------------------------- Time::at
  #      Time.at( aTime ) => time
  #      Time.at( seconds [, microseconds] ) => time
  # ------------------------------------------------------------------------
  #      Creates a new time object with the value given by _aTime_, or the
  #      given number of _seconds_ (and optional _microseconds_) from epoch.
  #      A non-portable feature allows the offset to be negative on some
  #      systems.
  # 
  #         Time.at(0)            #=> Wed Dec 31 18:00:00 CST 1969
  #         Time.at(946702800)    #=> Fri Dec 31 23:00:00 CST 1999
  #         Time.at(-284061600)   #=> Sat Dec 31 00:00:00 CST 1960
  # 
  def self.at(arg0, arg1, *rest)
  end

  # --------------------------------------------------------------- Time::gm
  #      Time.utc( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.utc( sec, min, hour, day, month, year, wday, yday, isdst, tz
  #      ) => time
  #      Time.gm( year [, month, day, hour, min, sec, usec] ) => time
  #      Time.gm( sec, min, hour, day, month, year, wday, yday, isdst, tz
  #      ) => time
  # ------------------------------------------------------------------------
  #      Creates a time based on given values, interpreted as UTC (GMT). The
  #      year must be specified. Other values default to the minimum value
  #      for that field (and may be +nil+ or omitted). Months may be
  #      specified by numbers from 1 to 12, or by the three-letter English
  #      month names. Hours are specified on a 24-hour clock (0..23). Raises
  #      an +ArgumentError+ if any values are out of range. Will also accept
  #      ten arguments in the order output by +Time#to_a+.
  # 
  #         Time.utc(2000,"jan",1,20,15,1)  #=> Sat Jan 01 20:15:01 UTC 2000
  #         Time.gm(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  # 
  def self.gm(arg0, arg1, *rest)
  end

  # --------------------------------------------------------------- Time#<=>
  #      time <=> other_time => -1, 0, +1 
  #      time <=> numeric    => -1, 0, +1
  # ------------------------------------------------------------------------
  #      Comparison---Compares _time_ with _other_time_ or with _numeric_,
  #      which is the number of seconds (possibly fractional) since epoch.
  # 
  #         t = Time.now       #=> Wed Apr 09 08:56:03 CDT 2003
  #         t2 = t + 2592000   #=> Fri May 09 08:56:03 CDT 2003
  #         t <=> t2           #=> -1
  #         t2 <=> t           #=> 1
  #         t <=> t            #=> 0
  # 
  def >(arg0)
  end

  # ------------------------------------------------------------- Time#_dump
  #      time._dump   => string
  # ------------------------------------------------------------------------
  #      Dump _time_ for marshaling.
  # 
  def _dump(arg0, arg1, *rest)
  end

  # ---------------------------------------------------------- Time#getlocal
  #      time.getlocal => new_time
  # ------------------------------------------------------------------------
  #      Returns a new +new_time+ object representing _time_ in local time
  #      (using the local time zone in effect for this process).
  # 
  #         t = Time.gm(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt?                          #=> true
  #         l = t.getlocal                  #=> Sat Jan 01 14:15:01 CST 2000
  #         l.gmt?                          #=> false
  #         t == l                          #=> true
  # 
  def getlocal
  end

  # -------------------------------------------------------- Time#gmt_offset
  #      time.gmt_offset => fixnum
  #      time.gmtoff     => fixnum
  #      time.utc_offset => fixnum
  # ------------------------------------------------------------------------
  #      Returns the offset in seconds between the timezone of _time_ and
  #      UTC.
  # 
  #         t = Time.gm(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt_offset                    #=> 0
  #         l = t.getlocal                  #=> Sat Jan 01 14:15:01 CST 2000
  #         l.gmt_offset                    #=> -21600
  # 
  def gmt_offset
  end

  def to_yaml_node(arg0)
  end

  # ----------------------------------------------------------------- Time#+
  #      time + numeric => time
  # ------------------------------------------------------------------------
  #      Addition---Adds some number of seconds (possibly fractional) to
  #      _time_ and returns that value as a new time.
  # 
  #         t = Time.now         #=> Wed Apr 09 08:56:03 CDT 2003
  #         t + (60 * 60 * 24)   #=> Thu Apr 10 08:56:03 CDT 2003
  # 
  def +(arg0)
  end

  # -------------------------------------------------------------- Time#yday
  #      time.yday => fixnum
  # ------------------------------------------------------------------------
  #      Returns an integer representing the day of the year, 1..366.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.yday         #=> 99
  # 
  def yday
  end

  # -------------------------------------------------------------- Time#to_s
  #      time.inspect => string
  #      time.to_s    => string
  # ------------------------------------------------------------------------
  #      Returns a string representing _time_. Equivalent to calling
  #      +Time#strftime+ with a format string of ``+%a+ +%b+ +%d+ +%H:%M:%S+
  #      +%Z+ +%Y+''.
  # 
  #         Time.now.to_s   #=> "Wed Apr 09 08:56:04 CDT 2003"
  # 
  def to_s
  end

  # --------------------------------------------------------------- Time#<=>
  #      time <=> other_time => -1, 0, +1 
  #      time <=> numeric    => -1, 0, +1
  # ------------------------------------------------------------------------
  #      Comparison---Compares _time_ with _other_time_ or with _numeric_,
  #      which is the number of seconds (possibly fractional) since epoch.
  # 
  #         t = Time.now       #=> Wed Apr 09 08:56:03 CDT 2003
  #         t2 = t + 2592000   #=> Fri May 09 08:56:03 CDT 2003
  #         t <=> t2           #=> -1
  #         t2 <=> t           #=> 1
  #         t <=> t            #=> 0
  # 
  def <=>(arg0)
  end

  # --------------------------------------------------------------- Time#sec
  #      time.sec => fixnum
  # ------------------------------------------------------------------------
  #      Returns the second of the minute (0..60)_[Yes, seconds really can
  #      range from zero to 60. This allows the system to inject leap
  #      seconds every now and then to correct for the fact that years are
  #      not really a convenient number of hours long.]_ for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.sec          #=> 4
  # 
  def sec
  end

  def taguri
  end

  # -------------------------------------------------------------- Time#eql?
  #      time.eql?(other_time)
  # ------------------------------------------------------------------------
  #      Return +true+ if _time_ and _other_time_ are both +Time+ objects
  #      with the same seconds and fractional seconds.
  # 
  def eql?(arg0)
  end

  # -------------------------------------------------------------- Time#to_i
  #      time.to_i   => int
  #      time.tv_sec => int
  # ------------------------------------------------------------------------
  #      Returns the value of _time_ as an integer number of seconds since
  #      epoch.
  # 
  #         t = Time.now
  #         "%10.5f" % t.to_f   #=> "1049896564.17839"
  #         t.to_i              #=> 1049896564
  # 
  def to_i
  end

  # --------------------------------------------------------------- Time#min
  #      time.min => fixnum
  # ------------------------------------------------------------------------
  #      Returns the minute of the hour (0..59) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.min          #=> 56
  # 
  def min
  end

  # --------------------------------------------------------------- Time#<=>
  #      time <=> other_time => -1, 0, +1 
  #      time <=> numeric    => -1, 0, +1
  # ------------------------------------------------------------------------
  #      Comparison---Compares _time_ with _other_time_ or with _numeric_,
  #      which is the number of seconds (possibly fractional) since epoch.
  # 
  #         t = Time.now       #=> Wed Apr 09 08:56:03 CDT 2003
  #         t2 = t + 2592000   #=> Fri May 09 08:56:03 CDT 2003
  #         t <=> t2           #=> -1
  #         t2 <=> t           #=> 1
  #         t <=> t            #=> 0
  # 
  def <(arg0)
  end

  # -------------------------------------------------------------- Time#wday
  #      time.wday => fixnum
  # ------------------------------------------------------------------------
  #      Returns an integer representing the day of the week, 0..6, with
  #      Sunday == 0.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.wday         #=> 3
  # 
  def wday
  end

  def gmtime?
  end

  # -------------------------------------------------------------- Time#succ
  #      time.succ   => new_time
  # ------------------------------------------------------------------------
  #      Return a new time object, one second later than +time+.
  # 
  def succ
  end

  # -------------------------------------------------------------- Time#dst?
  #      time.isdst => true or false
  #      time.dst?  => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if _time_ occurs during Daylight Saving Time in its
  #      time zone.
  # 
  #         Time.local(2000, 7, 1).isdst   #=> true
  #         Time.local(2000, 1, 1).isdst   #=> false
  #         Time.local(2000, 7, 1).dst?    #=> true
  #         Time.local(2000, 1, 1).dst?    #=> false
  # 
  def dst?
  end

  # ------------------------------------------------------------ Time#gmtime
  #      time.gmtime    => time
  #      time.utc       => time
  # ------------------------------------------------------------------------
  #      Converts _time_ to UTC (GMT), modifying the receiver.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.gmt?         #=> false
  #         t.gmtime       #=> Wed Apr 09 13:56:03 UTC 2003
  #         t.gmt?         #=> true
  #      
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.utc?         #=> false
  #         t.utc          #=> Wed Apr 09 13:56:04 UTC 2003
  #         t.utc?         #=> true
  # 
  def gmtime
  end

  # --------------------------------------------------------------- Time#<=>
  #      time <=> other_time => -1, 0, +1 
  #      time <=> numeric    => -1, 0, +1
  # ------------------------------------------------------------------------
  #      Comparison---Compares _time_ with _other_time_ or with _numeric_,
  #      which is the number of seconds (possibly fractional) since epoch.
  # 
  #         t = Time.now       #=> Wed Apr 09 08:56:03 CDT 2003
  #         t2 = t + 2592000   #=> Fri May 09 08:56:03 CDT 2003
  #         t <=> t2           #=> -1
  #         t2 <=> t           #=> 1
  #         t <=> t            #=> 0
  # 
  def <=(arg0)
  end

  # -------------------------------------------------------- Time#utc_offset
  #      time.gmt_offset => fixnum
  #      time.gmtoff     => fixnum
  #      time.utc_offset => fixnum
  # ------------------------------------------------------------------------
  #      Returns the offset in seconds between the timezone of _time_ and
  #      UTC.
  # 
  #         t = Time.gm(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt_offset                    #=> 0
  #         l = t.getlocal                  #=> Sat Jan 01 14:15:01 CST 2000
  #         l.gmt_offset                    #=> -21600
  # 
  def utc_offset
  end

  # ----------------------------------------------------------- Time#tv_usec
  #      time.usec    => int
  #      time.tv_usec => int
  # ------------------------------------------------------------------------
  #      Returns just the number of microseconds for _time_.
  # 
  #         t = Time.now        #=> Wed Apr 09 08:56:04 CDT 2003
  #         "%10.6f" % t.to_f   #=> "1049896564.259970"
  #         t.usec              #=> 259970
  # 
  def tv_usec
  end

  # -------------------------------------------------------------- Time#usec
  #      time.usec    => int
  #      time.tv_usec => int
  # ------------------------------------------------------------------------
  #      Returns just the number of microseconds for _time_.
  # 
  #         t = Time.now        #=> Wed Apr 09 08:56:04 CDT 2003
  #         "%10.6f" % t.to_f   #=> "1049896564.259970"
  #         t.usec              #=> 259970
  # 
  def usec
  end

  # ------------------------------------------------------------- Time#month
  #      time.mon   => fixnum
  #      time.month => fixnum
  # ------------------------------------------------------------------------
  #      Returns the month of the year (1..12) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.mon          #=> 4
  #         t.month        #=> 4
  # 
  def month
  end

  def taguri=(arg0, arg1, *rest)
  end

  # --------------------------------------------------------------- Time#day
  #      time.day  => fixnum
  #      time.mday => fixnum
  # ------------------------------------------------------------------------
  #      Returns the day of the month (1..n) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.day          #=> 9
  #         t.mday         #=> 9
  # 
  def day
  end

  # -------------------------------------------------------------- Time#zone
  #      time.zone => string
  # ------------------------------------------------------------------------
  #      Returns the name of the time zone used for _time_. As of Ruby 1.8,
  #      returns ``UTC'' rather than ``GMT'' for UTC times.
  # 
  #         t = Time.gm(2000, "jan", 1, 20, 15, 1)
  #         t.zone   #=> "UTC"
  #         t = Time.local(2000, "jan", 1, 20, 15, 1)
  #         t.zone   #=> "CST"
  # 
  def zone
  end

  # ------------------------------------------------------------- Time#ctime
  #      time.asctime => string
  #      time.ctime   => string
  # ------------------------------------------------------------------------
  #      Returns a canonical string representation of _time_.
  # 
  #         Time.now.asctime   #=> "Wed Apr  9 08:56:03 2003"
  # 
  def ctime
  end

  # ----------------------------------------------------------- Time#asctime
  #      time.asctime => string
  #      time.ctime   => string
  # ------------------------------------------------------------------------
  #      Returns a canonical string representation of _time_.
  # 
  #         Time.now.asctime   #=> "Wed Apr  9 08:56:03 2003"
  # 
  def asctime
  end

  # -------------------------------------------------------------- Time#mday
  #      time.day  => fixnum
  #      time.mday => fixnum
  # ------------------------------------------------------------------------
  #      Returns the day of the month (1..n) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.day          #=> 9
  #         t.mday         #=> 9
  # 
  def mday
  end

  # -------------------------------------------------------------- Time#to_f
  #      time.to_f => float
  # ------------------------------------------------------------------------
  #      Returns the value of _time_ as a floating point number of seconds
  #      since epoch.
  # 
  #         t = Time.now
  #         "%10.5f" % t.to_f   #=> "1049896564.13654"
  #         t.to_i              #=> 1049896564
  # 
  def to_f
  end

  # --------------------------------------------------------- Time#localtime
  #      time.localtime => time
  # ------------------------------------------------------------------------
  #      Converts _time_ to local time (using the local time zone in effect
  #      for this process) modifying the receiver.
  # 
  #         t = Time.gm(2000, "jan", 1, 20, 15, 1)
  #         t.gmt?        #=> true
  #         t.localtime   #=> Sat Jan 01 14:15:01 CST 2000
  #         t.gmt?        #=> false
  # 
  def localtime
  end

  # -------------------------------------------------------------- Time#to_a
  #      time.to_a => array
  # ------------------------------------------------------------------------
  #      Returns a ten-element _array_ of values for _time_: {+[ sec, min,
  #      hour, day, month, year, wday, yday, isdst, zone ]+}. See the
  #      individual methods for an explanation of the valid ranges of each
  #      value. The ten elements can be passed directly to +Time::utc+ or
  #      +Time::local+ to create a new +Time+.
  # 
  #         now = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t = now.to_a     #=> [4, 56, 8, 9, 4, 2003, 3, 99, true, "CDT"]
  # 
  def to_a
  end

  def ===(arg0)
  end

  # ------------------------------------------------------------- Time#isdst
  #      time.isdst => true or false
  #      time.dst?  => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if _time_ occurs during Daylight Saving Time in its
  #      time zone.
  # 
  #         Time.local(2000, 7, 1).isdst   #=> true
  #         Time.local(2000, 1, 1).isdst   #=> false
  #         Time.local(2000, 7, 1).dst?    #=> true
  #         Time.local(2000, 1, 1).dst?    #=> false
  # 
  def isdst
  end

  # ------------------------------------------------------------ Time#tv_sec
  #      time.to_i   => int
  #      time.tv_sec => int
  # ------------------------------------------------------------------------
  #      Returns the value of _time_ as an integer number of seconds since
  #      epoch.
  # 
  #         t = Time.now
  #         "%10.5f" % t.to_f   #=> "1049896564.17839"
  #         t.to_i              #=> 1049896564
  # 
  def tv_sec
  end

  # ------------------------------------------------------------- Time#getgm
  #      time.getgm  => new_time
  #      time.getutc => new_time
  # ------------------------------------------------------------------------
  #      Returns a new +new_time+ object representing _time_ in UTC.
  # 
  #         t = Time.local(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 CST 2000
  #         t.gmt?                             #=> false
  #         y = t.getgm                        #=> Sun Jan 02 02:15:01 UTC 2000
  #         y.gmt?                             #=> true
  #         t == y                             #=> true
  # 
  def getgm
  end

  # -------------------------------------------------------------- Time#hash
  #      time.hash   => fixnum
  # ------------------------------------------------------------------------
  #      Return a hash code for this time object.
  # 
  def hash
  end

  # ---------------------------------------------------------- Time#strftime
  #      time.strftime( string ) => string
  # ------------------------------------------------------------------------
  #      Formats _time_ according to the directives in the given format
  #      string. Any text not listed as a directive will be passed through
  #      to the output string.
  # 
  #      Format meaning:
  # 
  #        %a - The abbreviated weekday name (``Sun'')
  #        %A - The  full  weekday  name (``Sunday'')
  #        %b - The abbreviated month name (``Jan'')
  #        %B - The  full  month  name (``January'')
  #        %c - The preferred local date and time representation
  #        %d - Day of the month (01..31)
  #        %H - Hour of the day, 24-hour clock (00..23)
  #        %I - Hour of the day, 12-hour clock (01..12)
  #        %j - Day of the year (001..366)
  #        %m - Month of the year (01..12)
  #        %M - Minute of the hour (00..59)
  #        %p - Meridian indicator (``AM''  or  ``PM'')
  #        %S - Second of the minute (00..60)
  #        %U - Week  number  of the current year,
  #                starting with the first Sunday as the first
  #                day of the first week (00..53)
  #        %W - Week  number  of the current year,
  #                starting with the first Monday as the first
  #                day of the first week (00..53)
  #        %w - Day of the week (Sunday is 0, 0..6)
  #        %x - Preferred representation for the date alone, no time
  #        %X - Preferred representation for the time alone, no date
  #        %y - Year without a century (00..99)
  #        %Y - Year with century
  #        %Z - Time zone name
  #        %% - Literal ``%'' character
  #      
  #         t = Time.now
  #         t.strftime("Printed on %m/%d/%Y")   #=> "Printed on 04/09/2003"
  #         t.strftime("at %I:%M%p")            #=> "at 08:56AM"
  # 
  def strftime(arg0)
  end

  # ----------------------------------------------------------------- Time#-
  #      time - other_time => float
  #      time - numeric    => time
  # ------------------------------------------------------------------------
  #      Difference---Returns a new time that represents the difference
  #      between two times, or subtracts the given number of seconds in
  #      _numeric_ from _time_.
  # 
  #         t = Time.now       #=> Wed Apr 09 08:56:03 CDT 2003
  #         t2 = t + 2592000   #=> Fri May 09 08:56:03 CDT 2003
  #         t2 - t             #=> 2592000.0
  #         t2 - 2592000       #=> Wed Apr 09 08:56:03 CDT 2003
  # 
  def -(arg0)
  end

  # --------------------------------------------------------------- Time#utc
  #      time.gmtime    => time
  #      time.utc       => time
  # ------------------------------------------------------------------------
  #      Converts _time_ to UTC (GMT), modifying the receiver.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.gmt?         #=> false
  #         t.gmtime       #=> Wed Apr 09 13:56:03 UTC 2003
  #         t.gmt?         #=> true
  #      
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.utc?         #=> false
  #         t.utc          #=> Wed Apr 09 13:56:04 UTC 2003
  #         t.utc?         #=> true
  # 
  def utc
  end

  # ----------------------------------------------------------- Time#inspect
  #      time.inspect => string
  #      time.to_s    => string
  # ------------------------------------------------------------------------
  #      Returns a string representing _time_. Equivalent to calling
  #      +Time#strftime+ with a format string of ``+%a+ +%b+ +%d+ +%H:%M:%S+
  #      +%Z+ +%Y+''.
  # 
  #         Time.now.to_s   #=> "Wed Apr 09 08:56:04 CDT 2003"
  # 
  def inspect
  end

  # -------------------------------------------------------------- Time#hour
  #      time.hour => fixnum
  # ------------------------------------------------------------------------
  #      Returns the hour of the day (0..23) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.hour         #=> 8
  # 
  def hour
  end

  def >=(arg0)
  end

  # -------------------------------------------------------------- Time#year
  #      time.year => fixnum
  # ------------------------------------------------------------------------
  #      Returns the year for _time_ (including the century).
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.year         #=> 2003
  # 
  def year
  end

  def initialize_copy(arg0)
  end

  # -------------------------------------------------------------- Time#utc?
  #      time.utc? => true or false
  #      time.gmt? => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if _time_ represents a time in UTC (GMT).
  # 
  #         t = Time.now                        #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.utc?                              #=> false
  #         t = Time.gm(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.utc?                              #=> true
  #      
  #         t = Time.now                        #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.gmt?                              #=> false
  #         t = Time.gm(2000,1,1,20,15,1)       #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt?                              #=> true
  # 
  def utc?
  end

  # ------------------------------------------------------------ Time#getutc
  #      time.getgm  => new_time
  #      time.getutc => new_time
  # ------------------------------------------------------------------------
  #      Returns a new +new_time+ object representing _time_ in UTC.
  # 
  #         t = Time.local(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 CST 2000
  #         t.gmt?                             #=> false
  #         y = t.getgm                        #=> Sun Jan 02 02:15:01 UTC 2000
  #         y.gmt?                             #=> true
  #         t == y                             #=> true
  # 
  def getutc
  end

  # -------------------------------------------------------------- Time#gmt?
  #      time.utc? => true or false
  #      time.gmt? => true or false
  # ------------------------------------------------------------------------
  #      Returns +true+ if _time_ represents a time in UTC (GMT).
  # 
  #         t = Time.now                        #=> Wed Apr 09 08:56:04 CDT 2003
  #         t.utc?                              #=> false
  #         t = Time.gm(2000,"jan",1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.utc?                              #=> true
  #      
  #         t = Time.now                        #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.gmt?                              #=> false
  #         t = Time.gm(2000,1,1,20,15,1)       #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt?                              #=> true
  # 
  def gmt?
  end

  # --------------------------------------------------------------- Time#mon
  #      time.mon   => fixnum
  #      time.month => fixnum
  # ------------------------------------------------------------------------
  #      Returns the month of the year (1..12) for _time_.
  # 
  #         t = Time.now   #=> Wed Apr 09 08:56:03 CDT 2003
  #         t.mon          #=> 4
  #         t.month        #=> 4
  # 
  def mon
  end

  # ------------------------------------------------------------ Time#gmtoff
  #      time.gmt_offset => fixnum
  #      time.gmtoff     => fixnum
  #      time.utc_offset => fixnum
  # ------------------------------------------------------------------------
  #      Returns the offset in seconds between the timezone of _time_ and
  #      UTC.
  # 
  #         t = Time.gm(2000,1,1,20,15,1)   #=> Sat Jan 01 20:15:01 UTC 2000
  #         t.gmt_offset                    #=> 0
  #         l = t.getlocal                  #=> Sat Jan 01 14:15:01 CST 2000
  #         l.gmt_offset                    #=> -21600
  # 
  def gmtoff
  end

end
