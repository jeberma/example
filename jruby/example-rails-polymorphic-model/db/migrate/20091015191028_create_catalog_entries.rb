class CreateCatalogEntries < ActiveRecord::Migration
  def self.up
    create_table :catalog_entries do |t|
      t.string :name
      t.datetime :acquired_at
      t.integer :resource_id
      t.string :resource_type
      t.timestamps
    end
  end

  def self.down
    drop_table :catalog_entries
  end
end
