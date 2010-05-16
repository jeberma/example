# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_example-rails-quartz_session',
  :secret      => '9eafee671dc54aca557bce16193d767f2ac476468bfb1b2527eeaad784e69044d08a5b181f3bb4b48e6527839b059d78a3468d85363ebdfdd97c9f4518e44ac6'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
