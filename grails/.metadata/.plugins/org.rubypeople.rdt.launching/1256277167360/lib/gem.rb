=begin
------------------------------------------------------------- Class: Gem
     RubyGems is the Ruby standard for publishing and managing third
     party libraries.

     For user documentation, see:

     *   +gem help+ and +gem help [command]+

     *   {RubyGems User Guide}[http://docs.rubygems.org/read/book/1]

     *   {Frequently Asked
         Questions}[http://docs.rubygems.org/read/book/3]

     For gem developer documentation see:

     *   {Creating Gems}[http://docs.rubygems.org/read/chapter/5]

     *   Gem::Specification

     Further RubyGems documentation can be found at:

     *   {RubyGems API}[http://rubygems.rubyforge.org/rdoc] (also
         available from +gem server+)

     *   {RubyGems Bookshelf}[http://rubygem.org]


RubyGems Plugins
----------------
     As of RubyGems 1.3.2, RubyGems will load plugins installed in gems
     or $LOAD_PATH. Plugins must be named 'rubygems_plugin' are
     discovered via Gem::find_files then loaded. Take care when
     implementing a plugin as your plugin file may be loaded multiple
     times if multiple versions of your gem are installed.

     For an example plugin, see the graph gem which adds a `gem graph`
     command.


RubyGems Defaults, Packaging
----------------------------
     RubyGems defaults are stored in rubygems/defaults.rb. If you're
     packaging RubyGems or implementing Ruby you can change RubyGems'
     defaults.

     For RubyGems packagers, provide lib/rubygems/operating_system.rb
     and override any defaults from lib/rubygems/defaults.rb.

     For Ruby implementers, provide lib/rubygems/#{RUBY_ENGINE}.rb and
     override any defaults from lib/rubygems/defaults.rb.

     If you need RubyGems to perform extra work on install or uninstall,
     your defaults override file can set pre and post install and
     uninstall hooks. See Gem::pre_install, Gem::pre_uninstall,
     Gem::post_install, Gem::post_uninstall.


Bugs
----
     You can submit bugs to the {RubyGems bug
     tracker}[http://rubyforge.org/tracker/?atid=575&group_id=126&func=b
     rowse] on RubyForge


Credits
-------
     RubyGems is currently maintained by Eric Hodel.

     RubyGems was originally developed at RubyConf 2003 by:

     *   Rich Kilmer -- rich(at)infoether.com

     *   Chad Fowler -- chad(at)chadfowler.com

     *   David Black -- dblack(at)wobblini.net

     *   Paul Brannan -- paul(at)atdesk.com

     *   Jim Weirch --
         {jim(at)weirichhouse.org}[mailto:jim@weirichhouse.org]

     Contributors:

     *   Gavin Sinclair -- gsinclair(at)soyabean.com.au

     *   George Marrows -- george.marrows(at)ntlworld.com

     *   Dick Davies -- rasputnik(at)hellooperator.net

     *   Mauricio Fernandez -- batsman.geo(at)yahoo.com

     *   Simon Strandgaard -- neoneye(at)adslhome.dk

     *   Dave Glasser -- glasser(at)mit.edu

     *   Paul Duncan -- pabs(at)pablotron.org

     *   Ville Aine -- vaine(at)cs.helsinki.fi

     *   Eric Hodel -- drbrain(at)segment7.net

     *   Daniel Berger -- djberg96(at)gmail.com

     *   Phil Hagelberg -- technomancy(at)gmail.com

     *   Ryan Davis

     (If your name is missing, PLEASE let us know!)

     Thanks!

     -The RubyGems Team

------------------------------------------------------------------------
     RubyGems is the Ruby standard for publishing and managing third
     party libraries.

     For user documentation, see:

     *   +gem help+ and +gem help [command]+

     *   {RubyGems User Guide}[http://docs.rubygems.org/read/book/1]

     *   {Frequently Asked
         Questions}[http://docs.rubygems.org/read/book/3]

     For gem developer documentation see:

     *   {Creating Gems}[http://docs.rubygems.org/read/chapter/5]

     *   Gem::Specification

     Further RubyGems documentation can be found at:

     *   {RubyGems API}[http://rubygems.rubyforge.org/rdoc] (also
         available from +gem server+)

     *   {RubyGems Bookshelf}[http://rubygem.org]


RubyGems Plugins
----------------
     As of RubyGems 1.3.2, RubyGems will load plugins installed in gems
     or $LOAD_PATH. Plugins must be named 'rubygems_plugin' are
     discovered via Gem::find_files then loaded. Take care when
     implementing a plugin as your plugin file may be loaded multiple
     times if multiple versions of your gem are installed.

     For an example plugin, see the graph gem which adds a `gem graph`
     command.


RubyGems Defaults, Packaging
----------------------------
     RubyGems defaults are stored in rubygems/defaults.rb. If you're
     packaging RubyGems or implementing Ruby you can change RubyGems'
     defaults.

     For RubyGems packagers, provide lib/rubygems/operating_system.rb
     and override any defaults from lib/rubygems/defaults.rb.

     For Ruby implementers, provide lib/rubygems/#{RUBY_ENGINE}.rb and
     override any defaults from lib/rubygems/defaults.rb.

     If you need RubyGems to perform extra work on install or uninstall,
     your defaults override file can set pre and post install and
     uninstall hooks. See Gem::pre_install, Gem::pre_uninstall,
     Gem::post_install, Gem::post_uninstall.


Bugs
----
     You can submit bugs to the {RubyGems bug
     tracker}[http://rubyforge.org/tracker/?atid=575&group_id=126&func=b
     rowse] on RubyForge


Credits
-------
     RubyGems is currently maintained by Eric Hodel.

     RubyGems was originally developed at RubyConf 2003 by:

     *   Rich Kilmer -- rich(at)infoether.com

     *   Chad Fowler -- chad(at)chadfowler.com

     *   David Black -- dblack(at)wobblini.net

     *   Paul Brannan -- paul(at)atdesk.com

     *   Jim Weirch --
         {jim(at)weirichhouse.org}[mailto:jim@weirichhouse.org]

     Contributors:

     *   Gavin Sinclair -- gsinclair(at)soyabean.com.au

     *   George Marrows -- george.marrows(at)ntlworld.com

     *   Dick Davies -- rasputnik(at)hellooperator.net

     *   Mauricio Fernandez -- batsman.geo(at)yahoo.com

     *   Simon Strandgaard -- neoneye(at)adslhome.dk

     *   Dave Glasser -- glasser(at)mit.edu

     *   Paul Duncan -- pabs(at)pablotron.org

     *   Ville Aine -- vaine(at)cs.helsinki.fi

     *   Eric Hodel -- drbrain(at)segment7.net

     *   Daniel Berger -- djberg96(at)gmail.com

     *   Phil Hagelberg -- technomancy(at)gmail.com

     *   Ryan Davis

     (If your name is missing, PLEASE let us know!)

     Thanks!

     -The RubyGems Team

------------------------------------------------------------------------
     Main module to hold all RubyGem classes/modules.

------------------------------------------------------------------------
     Main module to hold all RubyGem classes/modules.

------------------------------------------------------------------------


Constants:
----------
     Cache:                  SourceIndex
     ConfigMap:              {} unless defined?(ConfigMap)
     DIRECTORIES:            %w[cache doc gems specifications] unless
                             defined?(DIRECTORIES)
     EXAMPLES:               %{     Some examples of 'gem' usage.      *
                             Install 'rake', either from local directory
                             or remote server:              gem install
                             rake      * Install 'rake', only from
                             remote server:          gem install rake
                             --remote      * Install 'rake' from remote
                             server, and run unit tests,       and
                             generate RDocs:          gem install
                             --remote rake --test --rdoc --ri      *
                             Install 'rake', but only version 0.3.1,
                             even if dependencies       are not met, and
                             into a specific directory:          gem
                             install rake --version 0.3.1 --force
                             --install-dir $HOME/.gems      * List local
                             gems whose name begins with 'D':         
                             gem list D      * List local and remote
                             gems whose name contains 'log':         
                             gem search log --both      * List only
                             remote gems whose name contains 'log':     
                                 gem search log --remote      *
                             Uninstall 'rake':          gem uninstall
                             rake          * Create a gem:          See
                             http://rubygems.rubyforge.org/wiki/wiki.pl?
                             CreateAGemInTenMinutes      * See
                             information about RubyGems:             
                             gem environment      }.gsub(/^    /, "")
     HELP:                   %{     RubyGems is a sophisticated package
                             manager for Ruby.  This is a     basic help
                             message containing pointers to more
                             information.        Usage:         gem
                             -h/--help         gem -v/--version        
                             gem command [arguments...] [options...]    
                                Examples:         gem install rake      
                               gem list --local         gem build
                             package.gemspec         gem help install   
                                 Further help:         gem help commands
                                        list all 'gem' commands        
                             gem help examples            show some
                             examples of usage         gem help
                             <COMMAND>           show help on COMMAND   
                                                                 (e.g.
                             'gem help install')       Further
                             information:        
                             http://rubygems.rubyforge.org     }.gsub(/^
                                /, "")
     MARSHAL_SPEC_DIR:       "quick/Marshal.#{Gem.marshal_version}/"
     MD5:                    DigestAdapter.new(Digest::MD5)
     MUTEX:                  Mutex.new
     RbConfig:               Config unless defined? ::RbConfig
     Requirement:            ::Gem::Version::Requirement
     RubyGemsPackageVersion: RubyGemsVersion
     RubyGemsVersion:        '1.0.1'
     SHA1:                   DigestAdapter.new(Digest::SHA1)
     SHA256:                 DigestAdapter.new(Digest::SHA256)
     WIN_PATTERNS:           [     /bccwin/i,     /cygwin/i,    
                             /djgpp/i,     /mingw/i,     /mswin/i,    
                             /wince/i,   ]
     YAML_SPEC_DIR:          'quick/'


Class methods:
--------------
     activate, all_load_paths, available?, bin_path, binary_mode,
     bindir, clear_paths, config_file, configuration, configuration=,
     configure, datadir, default_bindir, default_dir,
     default_exec_format, default_path, default_sources,
     default_system_source_cache_dir, default_user_source_cache_dir,
     deflate, dir, ensure_gem_subdirectories, ensure_ssl_available,
     find_files, gunzip, gzip, inflate, latest_load_paths,
     load_commands, load_path_insert_index, location_of_caller,
     manage_gems, marshal_version, path, platforms, platforms=,
     post_install, post_uninstall, pre_install, pre_uninstall, prefix,
     promote_load_path, read_binary, refresh, required_location, ruby,
     ruby_engine, ruby_version, searcher, source_index, sources,
     sources=, ssl_available?, suffix_pattern, suffixes, time, ui,
     use_paths, user_dir, user_home, win_platform?

Attributes:
     loaded_specs, post_install_hooks, post_uninstall_hooks,
     pre_install_hooks, pre_uninstall_hooks, ssl_available

=end
module Gem

  def self.sources
  end

  def self.all_load_paths
  end

  def self.pre_install_hooks
  end

  def self.clear_paths
  end

  def self.dir
  end

  def self.bindir(arg0, arg1, *rest)
  end

  def self.read_binary(arg0)
  end

  def self.path
  end

  def self.default_dir
  end

  def self.gzip(arg0)
  end

  def self.ruby
  end

  def self.sources=(arg0)
  end

  def self.post_install_hooks
  end

  def self.time(arg0, arg1, arg2, *rest)
  end

  def self.user_dir
  end

  def self.deflate(arg0)
  end

  def self.location_of_caller
  end

  def self.refresh
  end

  def self.find_files(arg0)
  end

  def self.pre_uninstall
  end

  def self.platforms
  end

  def self.configuration
  end

  def self.ensure_gem_subdirectories(arg0)
  end

  def self.post_uninstall
  end

  def self.pre_uninstall_hooks
  end

  def self.default_system_source_cache_dir
  end

  def self.datadir(arg0)
  end

  def self.default_path
  end

  def self.platforms=(arg0)
  end

  def self.ui
  end

  def self.config_file
  end

  def self.suffix_pattern
  end

  def self.configuration=(arg0)
  end

  def self.loaded_specs
  end

  def self.default_sources
  end

  def self.load_path_insert_index
  end

  def self.post_uninstall_hooks
  end

  def self.default_bindir
  end

  def self.win_platform?
  end

  def self.post_install
  end

  def self.default_exec_format
  end

  def self.marshal_version
  end

  def self.latest_load_paths
  end

  def self.promote_load_path(arg0, arg1)
  end

  def self.source_index
  end

  def self.ruby_version
  end

  def self.use_paths(arg0, arg1, arg2, *rest)
  end

  def self.searcher
  end

  def self.suffixes
  end

  def self.bin_path(arg0, arg1, arg2, *rest)
  end

  def self.ruby_engine
  end

  def self.cache
  end

  def self.available?(arg0, arg1, arg2, *rest)
  end

  def self.inflate(arg0)
  end

  def self.pre_install
  end

  def self.binary_mode
  end

  def self.default_user_source_cache_dir
  end

  def self.gunzip(arg0)
  end

  def self.required_location(arg0, arg1, arg2, arg3, *rest)
  end

  def self.activate(arg0, arg1, arg2, *rest)
  end

  def self.user_home
  end

  def self.prefix
  end

end
