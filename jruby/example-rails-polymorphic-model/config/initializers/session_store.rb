# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_example-rails-polymorphic-model_session',
  :secret      => '18f521e84fed6c24533bfeb4616133a435fbff3768f44b343dd0f0fb1c85e98e9492a1a41d5b373c42c8c556588bfa9bce369002627f5d5e60eb4d3e72f6ba97'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
