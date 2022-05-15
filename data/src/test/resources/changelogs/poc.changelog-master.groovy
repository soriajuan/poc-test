package changelogs

databaseChangeLog {

    changeSet(id: 'create-poc-schema', author: 'Juan Soria') {
        sql('CREATE SCHEMA IF NOT EXISTS poc')
    }

    changeSet(id: 'create-table-person', author: 'Juan Soria') {
        createTable(schemaName: 'poc', tableName: 'person', ) {
            column(name: 'id', type: 'UUID', defaultValueComputed: 'gen_random_uuid()') {
                constraints(nullable: false, primaryKey: true, primaryKeyName: 'person_pkey')
            }
            column(name: 'first_name', type: 'TEXT') {
                constraints(nullable: false)
            }
            column(name: 'last_name', type: 'TEXT') {
                constraints(nullable: false)
            }
        }
    }

}
