# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_order-website_session',
  :secret      => '5ba39e0ad6d8e04c732afc9f033ea3b0a5a18723f014ef585486bf13a62399acf9824fd28f406d97d8289ce8afbff7f525be3a87e5a4b4aa47d8986cfdb65f87'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
