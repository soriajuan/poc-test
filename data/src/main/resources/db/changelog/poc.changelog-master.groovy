package db.changelog

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

    changeSet(id: 'create-table-customer', author: 'Juan Soria') {
        createTable(schemaName: 'poc', tableName: 'customer', ) {
            column(name: 'id', type: 'UUID', defaultValueComputed: 'gen_random_uuid()') {
                constraints(nullable: false, primaryKey: true, primaryKeyName: 'customer_pkey')
            }
            column(name: 'name', type: 'TEXT') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: 'create-table-order', author: 'Juan Soria') {
        createTable(schemaName: 'poc', tableName: 'order', ) {
            column(name: 'id', type: 'UUID', defaultValueComputed: 'gen_random_uuid()') {
                constraints(nullable: false, primaryKey: true, primaryKeyName: 'order_pkey')
            }
            column(name: 'customer_id', type: 'UUID') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'TEXT') {
                constraints(nullable: false)
            }
            column(name: 'total', type: 'NUMERIC(12, 2)') {
                constraints(nullable: false)
            }
        }
        addForeignKeyConstraint(constraintName: 'customer_id_fk',
                baseTableSchemaName: 'poc', baseTableName: 'order', baseColumnNames: 'customer_id',
                referencedTableSchemaName: 'poc', referencedTableName: 'customer', referencedColumnNames: 'id',
                validate: true)
    }

}
