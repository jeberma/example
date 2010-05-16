class Image < ActiveRecord::Base
  has_one :catalog_entry, :as=>:resource
end
