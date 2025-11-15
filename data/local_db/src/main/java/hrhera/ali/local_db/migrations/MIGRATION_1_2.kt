package hrhera.ali.local_db.migrations

val MIGRATION_1_2 = object : androidx.room.migration.Migration(startVersion = 1, endVersion =  2) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL(
          "ALTER TABLE weather_data " +
          "ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0"
        )
    }
}
